package com.nyller.android.mach3.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.nyller.android.mach3.databinding.ActivityLoginBinding
import com.nyller.android.mach3.models.User
import com.nyller.android.mach3.utils.toast

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private var user = User()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

    }

    override fun onStart() {
        super.onStart()

        binding.btnLogin.setOnClickListener {
            validarCampos()
        }

        binding.tvCadastrar.setOnClickListener {
            startActivity(Intent(this, CadastroActivity::class.java))
            finish()
        }

        val currentUser = auth.currentUser

        if (currentUser != null) {
            reload()
        }

    }

    private fun validarCampos() {
        val campoEmail = binding.editLoginEmail.text.toString()
        val campoSenha = binding.editLoginSenha.text.toString()

        if (campoEmail.isNotEmpty()) {
            if (campoSenha.isNotEmpty()) {
                user.email = campoEmail
                user.senha = campoSenha
                loginUser()
            }else { "Insira sua senha!".toast(this) }
        }else { "Insira seu e-mail!".toast(this) }

    }

    private fun loginUser() {
        user.email?.let { email -> user.senha?.let { password ->
            auth.signInWithEmailAndPassword(email, password) } }
            ?.addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    openMainActivity()
                }
            }?.addOnFailureListener { exception ->
                val msg = when (exception) {
                    is FirebaseAuthInvalidUserException -> "Usuário não cadastrado!"
                    is FirebaseAuthInvalidCredentialsException -> "E-mail e senha não correspondem!"
                    else -> "Erro ao fazer login!"
                }
                msg.toast(this)
            }
    }

    private fun openMainActivity() {
        startActivity(Intent(this, NavActivity::class.java))
        "Sucesso ao logar!".toast(this)
        finish()
    }

    private fun reload() {
        startActivity(Intent(this, NavActivity::class.java))
        finish()
    }

}