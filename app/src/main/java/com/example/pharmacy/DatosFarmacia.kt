package com.example.pharmacy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class DatosFarmacia : AppCompatActivity() {

        private  lateinit var  recyclerView: RecyclerView
        private lateinit var  farmaciaAdapter: FarmaciaAdapter

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_datos_farmacia)

            val farmacias = listOf(
                Farmacia("FARMACIA EL RENACIMIENTO", "Calle", "Xalapa", "Colonia", "Centro"),
                Farmacia("FARMACIA CANDELARIA", "Calle", "Xalapa", "Colonia", "Misantla Centro"),
                Farmacia("FARMACIA CONCHITA", "Calle", "Ignacio Comonfort", "Tipo Asentamiento C", "Misantla Centro"),
                Farmacia("FARMACIA DE CRISTO", "Calle", "Coronado", "Colonia", "Misantla Centro"),
                Farmacia("FARMACIA DE GENERICOS SIMILARES", "Calle", "Zaragoza", "Colonia", "Misantla Centro"),
                Farmacia("FARMACIA DE GENERICOS Y PATENTE", "Calle", "Tabachines", "Colonia", "Misantla Centro"),
                Farmacia("FARMACIA EL DE GENERICOS Y SIMILARES HERNANDEZ", "Calle", "Sebastian Lerdo de Tejada", "Colonia", "MISANTLA CENTRO"),
                Farmacia("FARMACIA DE JESUS", "Calle", "Ezequiel Alatriste", "Colonia", "Misantla Centro"),
                Farmacia("FARMACIA DEL ANGEL", "Calle", "Zaragoza", "Colonia", "Misantla Centro"),
                Farmacia("FARMACIA DEL CENTRO", "Calle", "Diaz Miron", "Colonia", "Misantla CentroO"),
                Farmacia("FARMACIA LA ASUNCION", "Calle", "Xalapa", "Colonia", "Misantla Centro "),
                Farmacia("FARMACIA SAN JOSE", "Calle", "Constitución", "Colonia", "Centro"),
                Farmacia("FARMACIAS DE SIMILARES", "Avenida", "Manuel Ávila Camacho", "Colonia", "Aviacion"),
                Farmacia("FARMACIAS DE SIMILARES", "Calle", "5 de Mayo", "Colonia", "Misantla"),
                Farmacia("FARMACIAS DE SIMILARES", "Calle", "Ignacio Comonfort", "Colonia", "Centro"),
                Farmacia("FARMACIAS DE SIMILARES", "Avenida", "Alfonso Arroyo", "Colonia", "Centro"),
                Farmacia("FARMACIAS G I", "Calle", "Jose Maria Morelos y Pavon", "Colonia", "Misantla Centro"),
                Farmacia("SUPER FARMACIA DEL PUEBLO", "Calle", "Constitución", "Colonia", "Centro"),
                Farmacia("FARMACIA GUADALAJARA", "Avenida", "Xalapa", "Colonia", "Centro"),
            )

            recyclerView = findViewById(R.id.recyclerview)
            recyclerView.layoutManager = LinearLayoutManager(this)
            farmaciaAdapter = FarmaciaAdapter(farmacias)
            recyclerView.adapter = farmaciaAdapter

        }
    }
