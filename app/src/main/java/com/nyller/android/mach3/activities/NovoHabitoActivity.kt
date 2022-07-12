package com.nyller.android.mach3.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.nyller.android.mach3.R
import com.nyller.android.mach3.databinding.ActivityNovoHabitoBinding

class NovoHabitoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNovoHabitoBinding

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

        fun onCheckBoxClicked(view: View){
            if (view is CheckBox) {
                val checked: Boolean = view.isChecked
                when (view.id) {
                    R.id.buttonSegunda -> {
                        if (checked) {
                            Toast.makeText(this, "Segunda!", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this, "Not Segunda! :(", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                binding.btnCriar.setOnClickListener {

                    val habitosMap = hashMapOf(
                        "nome" to binding.editNomeHabito.text.toString(),
                        "turno" to "Manhã",
                        "categoria" to "Alguma aí"
                    )

                    db.collection("habitos").add(habitosMap)
                        .addOnCompleteListener {
                            Log.i("Db Firestore", "Sucesso ao salvar dados!")
                        }.addOnFailureListener {
                            Log.i("Erro Firestore", "Erro ao salvar dados!")
                        }
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return false
    }

}