package com.example.roomcrudmarvel.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.roomcrudmarvel.R
import com.example.roomcrudmarvel.databinding.ActivityMainBinding
import com.example.roomcrudmarvel.domain.Personaje
import com.example.roomcrudmarvel.ui.crearPersonaje.CrearPersonaje
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

//En comparación con la práctica anterior. He modificado un par de cosas.
// Meter try/catch para lanzar errores por si pasa algo. Añadir VM en cada pantalla, y hacer el Adapter del comics para este.

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var personajeAdapter: PersonajeAdapter
    private val viewModel: MainViewModel by viewModels()

    //Para añadir menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menuItemCrearPersonaje -> {
                irCrearPersonaje()
                true
            }
            R.id.menuItemOrdenarNombre -> {
                ordenarNombre()
                Toast.makeText(this, getString(R.string.ordenando_nombre), Toast.LENGTH_SHORT)
                    .show()
                true
            }
            R.id.menuItemOrdenarId -> {
                ordenarId()
                Toast.makeText(this, getString(R.string.ordenando_id), Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        personajeAdapter = PersonajeAdapter(::deletePersonaje)
        binding.rvPersonajes.adapter = personajeAdapter
        viewModel.personajes.observe(this, { personaje ->
            personajeAdapter.submitList(personaje)
        })
        viewModel.error.observe(this, {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
        viewModel.getAllPersonajes()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllPersonajes()
    }

    private fun irCrearPersonaje() {
        val intent = Intent(this, CrearPersonaje::class.java)
        startActivity(intent)
    }

    private fun deletePersonaje(pers: Personaje) {
        val avisoDelete = MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.DeleteTitle))
            .setMessage(getString(R.string.deleteBody))
            .setNegativeButton(getString(R.string.no)) { view, _ -> view.dismiss() }
            .setPositiveButton(getString(R.string.si)) { view, _ ->
                viewModel.deletePersonaje(pers)
                view.dismiss()
            }
            .setCancelable(false)
            .create()
        avisoDelete.show()

    }

    private fun ordenarNombre() {
        viewModel.ordenarPorNombre()
    }

    private fun ordenarId() {
        viewModel.ordenarPorId()
    }
}
