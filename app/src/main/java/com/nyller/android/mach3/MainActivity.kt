package com.nyller.android.mach3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.*
import com.nyller.android.mach3.activities.HabitosActivity
import com.nyller.android.mach3.adapters.AdapterHabitos
import com.nyller.android.mach3.databinding.FragmentHomeBinding
import com.nyller.android.mach3.models.Habito

class MainActivity : AppCompatActivity() {
    private lateinit var binding: FragmentHomeBinding

    private lateinit var adapterHabitos : AdapterHabitos
    private lateinit var habitosArrayList : ArrayList<Habito>
    private lateinit var db : FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar Recycler
        binding.recyclerHabitos
        binding.recyclerHabitos.layoutManager = LinearLayoutManager(this)
        binding.recyclerHabitos.setHasFixedSize(true)

        habitosArrayList = arrayListOf()

        adapterHabitos = AdapterHabitos(habitosArrayList)

        binding.recyclerHabitos.adapter = adapterHabitos

        eventChangeListener()

    }

    private fun eventChangeListener() {
        db = FirebaseFirestore.getInstance()
        db.collection("habitos")
            .addSnapshotListener(object : EventListener<QuerySnapshot> {
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                    if (error != null){
                        Log.e("Firestore erro", error.message.toString())
                        return
                    }
                    for (dc : DocumentChange in value?.documentChanges!!){
                        if (dc.type == DocumentChange.Type.ADDED){
                            habitosArrayList.add(dc.document.toObject(Habito::class.java))
                        }
                    }
                    adapterHabitos.notifyDataSetChanged()
                }
            })
    }

    override fun onStart() {
        super.onStart()

        binding.imageButton.setOnClickListener {
            startActivity(Intent(this, HabitosActivity::class.java))
        }

    }

}