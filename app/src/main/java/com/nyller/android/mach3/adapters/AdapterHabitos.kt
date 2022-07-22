package com.nyller.android.mach3.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nyller.android.mach3.databinding.AdapterHabitosBinding
import com.nyller.android.mach3.models.Habito

class AdapterHabitos(private val items : List<Habito> = ArrayList()) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder (AdapterHabitosBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MyViewHolder -> {
                val habito : Habito = items[position]
                holder.habitoTurno.text = habito.turno
                holder.habitoCategoria.text = habito.categoria
                holder.habitoNome.text = habito.nome
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class MyViewHolder(itemView: AdapterHabitosBinding) : RecyclerView.ViewHolder(itemView.root) {
        val habitoTurno = itemView.tvTurno
        val habitoCategoria = itemView.tvCategoria
        val habitoNome = itemView.tvNomeHabito

    }

}