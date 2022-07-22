package com.nyller.android.mach3.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.nyller.android.mach3.activities.HabitosActivity
import com.nyller.android.mach3.adapters.AdapterHabitos
import com.nyller.android.mach3.databinding.FragmentHomeBinding
import com.nyller.android.mach3.models.Habito

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    private lateinit var adapterHabitos : AdapterHabitos
    private lateinit var habitosArrayList : ArrayList<Habito>
    private lateinit var db : FirebaseFirestore
    private val userAtual = FirebaseAuth.getInstance().currentUser?.uid

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)

        return binding.root

    }

    override fun onStart() {
        super.onStart()

        binding.imageButton.setOnClickListener {
            startActivity(Intent(activity, HabitosActivity::class.java))
        }

        // Configurar Recycler
        binding.recyclerHabitos
        binding.recyclerHabitos.layoutManager = LinearLayoutManager(activity)
        binding.recyclerHabitos.setHasFixedSize(true)

        habitosArrayList = arrayListOf()

        adapterHabitos = AdapterHabitos(habitosArrayList)

        binding.recyclerHabitos.adapter = adapterHabitos

        eventChangeListener()

    }

    private fun eventChangeListener() {
        db = FirebaseFirestore.getInstance()
        val habitosRef = userAtual?.let { idUser ->
            db.collection("usuarios")
                .document(idUser) }
        habitosRef?.collection("meus_habitos")
            ?.addSnapshotListener(object : EventListener<QuerySnapshot> {
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                    habitosArrayList.clear()
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

}