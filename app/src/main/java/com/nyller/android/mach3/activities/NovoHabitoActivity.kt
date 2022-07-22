package com.nyller.android.mach3.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.nyller.android.mach3.databinding.ActivityNovoHabitoBinding
import com.nyller.android.mach3.utils.toast

class NovoHabitoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNovoHabitoBinding
    private var categoria = ""

    private val db = FirebaseFirestore.getInstance()

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
            saveData()
            finish()
        }

    }

    private fun setCategory() {

        val categorias = arrayOf("Corpo e mente", "Foco", "Finanças")

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

        var turno = ""
        val rb = binding.rgTurnos.checkedRadioButtonId

        if (rb == binding.rbQualquerHora.id) turno = "Qualquer hora"
        if (rb == binding.rbManha.id) turno = "Manhã"
        if (rb == binding.rbTarde.id) turno = "Tarde"
        if (rb == binding.rbNoite.id) turno = "Noite"

        val habitosMap = hashMapOf(
            "nome" to binding.editNomeHabito.text.toString(),
            "turno" to turno,
            "categoria" to categoria
        )

        db.collection("habitos").add(habitosMap)
            .addOnCompleteListener {
                Log.i("Db Firestore", "Sucesso ao salvar dados!")
            }.addOnFailureListener {
                Log.i("Erro Firestore", "Erro ao salvar dados!")
            }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return false
    }
}

