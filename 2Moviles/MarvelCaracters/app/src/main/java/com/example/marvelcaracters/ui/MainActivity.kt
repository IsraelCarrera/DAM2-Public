package com.example.marvelcaracters.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelcaracters.R
import com.example.marvelcaracters.data.PersonajesRepository
import com.example.marvelcaracters.databinding.ActivityMainBinding
import com.example.marvelcaracters.domain.Personaje
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber
import java.io.IOException


class MainActivity : AppCompatActivity() {
    //Clases utilizadas
    private lateinit var binding: ActivityMainBinding
    private var listPersonajes: List<Personaje> = arrayListOf()
    private lateinit var rvPersonajes: RecyclerView
    private var matchedPersonajes: ArrayList<Personaje> = arrayListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        rvPersonajes = binding.rvPersonajes
        leerArchivo()
        listPersonajes = PersonajesRepository().getLista()
        ponerRV()
        cargarBuscar()
    }

    private fun leerArchivo() {
        try {
            PersonajesRepository(assets.open(getString(R.string.archivoLectura)))
        } catch (e: IOException) {
            Timber.e(e, getString(R.string.falloLectura))
        }
    }

    private fun ponerRV() {
        listPersonajes?.let {
            rvPersonajes.adapter = PersonajeAdapter(it, ::clickDelete, ::volverAdd)
            rvPersonajes.layoutManager = GridLayoutManager(this@MainActivity, 1)
        }
    }


    private fun clickDelete(pers: Personaje) {
        if (matchedPersonajes.contains(pers)) {
            matchedPersonajes.remove(pers)
        }
        rvPersonajes.adapter?.notifyDataSetChanged()
    }

    private fun volverAdd(pos : Int, pers: Personaje){
        if(matchedPersonajes.isEmpty()){
            matchedPersonajes.add(pers)
        }else if(!matchedPersonajes.contains(pers)){
            matchedPersonajes.add(pos,pers)
        }
        rvPersonajes.adapter?.notifyDataSetChanged()
    }

    //Estas 3 de aquí, para el buscador.
    private fun cargarBuscar() {
        binding.svBuscarPorNombre.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                search(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                search(newText)
                return true
            }
        })
    }

    private fun search(text: String?) {
        matchedPersonajes = arrayListOf()
        text?.let {
            listPersonajes.forEach { personaje ->
                if (personaje.nombre.contains(text, true)) {
                    matchedPersonajes.add(personaje)
                }
            }
        }
        recargarRV()
        if (matchedPersonajes.isEmpty()) {
            Toast.makeText(this, getString(R.string.noPersonajeLook), Toast.LENGTH_SHORT).show()
        }
        recargarRV()
    }

    private fun recargarRV() {
        //Realmente no sabía como pasarle solo la lista de match, intenté crear un constructor pero, no sé, voy muy pillado.
        binding.rvPersonajes.apply {
            rvPersonajes.adapter = PersonajeAdapter(matchedPersonajes, ::clickDelete, ::volverAdd)
            rvPersonajes.adapter?.notifyDataSetChanged()
        }
    }

}