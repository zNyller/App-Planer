package com.nyller.android.mach3.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.nyller.android.mach3.databinding.ActivityNovoHabitoBinding
import com.nyller.android.mach3.utils.toast

class NovoHabitoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNovoHabitoBinding
    private var categoria = ""
    private var turno = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNovoHabitoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Toolbar
        setSupportActionBar(binding.include.toolbar)
        supportActionBar?.title = "Criar hábito"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onStart() {
        super.onStart()

        binding.btnCategoria.setOnClickListener {

            setCategory()

        }

        binding.btnCriar.setOnClickListener {
            setTurn()
            validarCampos()
        }

    }

    private fun setTurn() {
        val rb = binding.rgTurnos.checkedRadioButtonId

        if (rb == binding.rbQualquerHora.id) turno = "Qualquer hora"
        if (rb == binding.rbManha.id) turno = "Manhã"
        if (rb == binding.rbTarde.id) turno = "Tarde"
        if (rb == binding.rbNoite.id) turno = "Noite"
    }

    private fun validarCampos() {
        if (binding.editNomeHabito.text.isNotEmpty()) {
            if (categoria.isNotEmpty()) {
                if (turno.isNotEmpty()) {
                    saveData()
                    "Habito criado!".toast(this)
                    finish()
                } else { "Defina um turno!".toast(this) }
            } else { "Defina uma categoria!".toast(this ) }
        } else { "Defina um nome!".toast(this) }
    }

    private fun setCategory() {

        val categorias = arrayOf("Corpo e mente", "Foco", "Finanças", "Rotina noturna", "Atividade fisica", "Organização")

        val dialog = AlertDialog.Builder(this)
            .setTitle("Selecione a categoria")
            .setSingleChoiceItems(categorias, -1) { dialog, selected ->
                "Selecionado: ${categorias[selected]}".toast(this)
                categoria = categorias[selected]
                dialog.dismiss()
            }.create()

        dialog.show()

    }

    private fun saveData() {

        var configs = ""

        if (binding.cbSegunda.isChecked) configs += 0
        if (binding.cbTerca.isChecked) configs += 1
        if (binding.cbQuarta.isChecked) configs += 2
        if (binding.cbQuinta.isChecked) configs += 3
        if (binding.cbSexta.isChecked) configs += 4
        if (binding.cbSabado.isChecked) configs += 5
        if (binding.cbDomingo.isChecked) configs += 6

        val habitosMap = hashMapOf(
            "nome" to binding.editNomeHabito.text.toString(),
            "turno" to turno,
            "categoria" to categoria
        )

        val db = Firebase.firestore
        val usuarioAtual = FirebaseAuth.getInstance().currentUser?.uid
        val docRef = usuarioAtual?.let { idUser ->
            db.collection("usuarios").document(idUser) }

        usuarioAtual?.let {
            docRef?.collection("meus_habitos")?.add(habitosMap)
                ?.addOnCompleteListener {
                    Log.i("Db Firestore", "Sucesso ao salvar dados!")
                }?.addOnFailureListener {
                    Log.i("Erro Firestore", "Erro ao salvar dados!")
                }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return false
    }
}

