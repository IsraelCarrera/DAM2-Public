package com.example.roomcrudmarvel.ui.main

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.roomcrudmarvel.R
import com.example.roomcrudmarvel.data.PersonajeDatabaseRoom
import com.example.roomcrudmarvel.data.PersonajeRepository
import com.example.roomcrudmarvel.databinding.ActivityMainBinding
import com.example.roomcrudmarvel.domain.Personaje
import com.example.roomcrudmarvel.ui.personajes.CrearPersonaje
import com.example.roomcrudmarvel.ui.personajes.PersonajeAdapter
import com.example.roomcrudmarvel.usescases.personajes.AddPersonaje
import com.example.roomcrudmarvel.usescases.personajes.DeletePersonaje
import com.example.roomcrudmarvel.usescases.personajes.GetAllPersonajes
import com.example.roomcrudmarvel.usescases.personajes.UpdatePersonaje
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var personajeAdapter: PersonajeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        personajeAdapter = PersonajeAdapter(::deletePersonaje)
        binding.rvPersonajes.adapter = personajeAdapter
        viewModel.personajes.observe(this, { personaje ->
            personajeAdapter.submitList(personaje)
        })
        viewModel.getAllPersonajes()

        binding.bnAddPersonaje.setOnClickListener {
            val intent = Intent(it.context, CrearPersonaje::class.java)
            startActivity(intent)
        }


    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllPersonajes()
    }


    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(
            //Funciones que están en las otras clases.
            GetAllPersonajes(
                PersonajeRepository(
                    PersonajeDatabaseRoom.getDatabase(this).personajeDao()
                )
            ),
            UpdatePersonaje(
                PersonajeRepository(
                    PersonajeDatabaseRoom.getDatabase(this).personajeDao()
                )
            ),
            AddPersonaje(
                PersonajeRepository(
                    PersonajeDatabaseRoom.getDatabase(this).personajeDao()
                )
            ),
            DeletePersonaje(
                PersonajeRepository(
                    PersonajeDatabaseRoom.getDatabase(this).personajeDao()
                )
            )
        )
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
}
