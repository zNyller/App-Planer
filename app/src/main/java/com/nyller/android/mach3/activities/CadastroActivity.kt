package com.nyller.android.mach3.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.nyller.android.mach3.MainActivity
import com.nyller.android.mach3.databinding.ActivityCadastroBinding
import com.nyller.android.mach3.models.User
import com.nyller.android.mach3.models.saveInDb
import com.nyller.android.mach3.utils.toast

class CadastroActivity : AppCompatActivity() {
    private lateinit var binding : ActivityCadastroBinding

    private lateinit var auth : FirebaseAuth

    private val user = User()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.btnCadastrar.setOnClickListener { validarCampos() }

    }

    private fun validarCampos() {
        val nome = binding.editNomeCadastro.text.toString()
        val email = binding.editEmailCadastro.text.toString()
        val senha = binding.editSenhaCadastro.text.toString()

        if (nome.isNotEmpty()){
            if (email.isNotEmpty()){
                if (senha.isNotEmpty()) {
                    user.nome = nome
                    user.email = email
                    user.senha = senha
                    registerUser()

                }else { "Insira uma senha!".toast(this) }
            }else { "Insira um e-mail!".toast(this) }
        }else { "Insira um nome!".toast(this) }
    }

    private fun registerUser() {
        user.email?.let { email -> user.senha?.let { senha ->
            auth.createUserWithEmailAndPassword(email, senha) } }
            ?.addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // UID do Firebase
                    val idUsuario = task.result.user?.uid
                    user.id = idUsuario
                    "Sucesso ao cadastrar!".toast(this)
                    saveInDb(user)
                    startActivity(Intent(this, NavActivity::class.java))
                    finish()
                }
            }?.addOnFailureListener { exception ->
                val excecao = when (exception) {
                    is FirebaseAuthWeakPasswordException -> "Crie uma senha mais forte!"
                    is FirebaseAuthInvalidCredentialsException -> "Por favor, digite um e-mail válido!"
                    is FirebaseAuthUserCollisionException -> "Esta conta já foi cadastrada!"
                    is FirebaseNetworkException -> "Sem conexão com a internet!"
                    else -> "Erro ao cadastrar usuário!"
                }
                excecao.toast(this)
            }

    }

}