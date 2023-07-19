package com.example.pharmacy

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FarmaciaAdapter(private val farmacias: List<Farmacia>) : RecyclerView.Adapter<FarmaciaAdapter.ViewHolder>() {


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewHotelName: TextView = itemView.findViewById(R.id.textViewHotelName)
        val textViewVialityType: TextView = itemView.findViewById(R.id.textViewVialityType)
        val textViewVialityName: TextView = itemView.findViewById(R.id.textViewVialityName)
        val textViewSettlementType: TextView = itemView.findViewById(R.id.textViewSettlementType)
        val textViewSettlementName: TextView = itemView.findViewById(R.id.textViewSettlementName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_farmacia, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val hotel = farmacias[position]
        holder.textViewHotelName.text = hotel.nombre
        holder.textViewVialityType.text = hotel.vialidadTipo
        holder.textViewVialityName.text = hotel.vialidadNombre
        holder.textViewSettlementType.text = hotel.tipoAcentamiento
        holder.textViewSettlementName.text = hotel.nombreAcentamiento
    }

    override fun getItemCount(): Int {
        return farmacias.size
    }
}