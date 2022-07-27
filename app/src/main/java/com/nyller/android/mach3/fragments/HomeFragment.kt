package com.nyller.android.mach3.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.google.firebase.firestore.EventListener
import com.nyller.android.mach3.activities.HabitosActivity
import com.nyller.android.mach3.adapters.AdapterHabitos
import com.nyller.android.mach3.databinding.FragmentHomeBinding
import com.nyller.android.mach3.models.Habito
import com.nyller.android.mach3.utils.toast
import java.util.*

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

        binding.btnHabito.setOnClickListener { startActivity(Intent(activity, HabitosActivity::class.java)) }

        // Configurar Recycler
        binding.recyclerHabitos
        binding.recyclerHabitos.layoutManager = LinearLayoutManager(activity)
        binding.recyclerHabitos.setHasFixedSize(true)

        habitosArrayList = arrayListOf()

        adapterHabitos = AdapterHabitos(habitosArrayList)

        binding.recyclerHabitos.adapter = adapterHabitos

        eventChangeListener()
        swipe()

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

    private fun swipe() {

        val swipeHandler = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.START or ItemTouchHelper.END
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val startPosition = viewHolder.adapterPosition
                val endPosition = target.adapterPosition

                Collections.swap(habitosArrayList, startPosition, endPosition)
                adapterHabitos.notifyItemMoved(startPosition, endPosition)

                return true

            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                when (direction) {
                    ItemTouchHelper.END ->

                        activity?.let { "Done!".toast(it) }

                    ItemTouchHelper.START ->
                        activity?.let { "Not Done!".toast(it) }
                }
            }
        }
        ItemTouchHelper(swipeHandler).attachToRecyclerView(binding.recyclerHabitos)
    }
}