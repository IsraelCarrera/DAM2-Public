package com.example.pantallacud.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pantallacud.R
import com.example.pantallacud.domain.Persona

class ReciclerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recicler)
        val personas = intent.getParcelableArrayListExtra<Persona>(getString(R.string.persona))
        val rvPersonas = this.findViewById<RecyclerView>(R.id.rvPersonas)

        personas?.let {
            rvPersonas.adapter = PersonasAdapter(it)
            rvPersonas.layoutManager = GridLayoutManager(this@ReciclerActivity, 2)
        }
    }
}