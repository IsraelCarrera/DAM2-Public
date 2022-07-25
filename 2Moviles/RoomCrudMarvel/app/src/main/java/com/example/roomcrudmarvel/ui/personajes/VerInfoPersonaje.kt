package com.example.roomcrudmarvel.ui.personajes

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.roomcrudmarvel.R
import com.example.roomcrudmarvel.data.PersonajeDatabaseRoom
import com.example.roomcrudmarvel.data.PersonajeRepository
import com.example.roomcrudmarvel.databinding.InfoPersonajeBinding
import com.example.roomcrudmarvel.domain.Personaje
import com.example.roomcrudmarvel.ui.comics.ComicAdapter
import com.example.roomcrudmarvel.ui.dialog.DatePickerFragment
import com.example.roomcrudmarvel.ui.main.MainViewModel
import com.example.roomcrudmarvel.ui.main.MainViewModelFactory
import com.example.roomcrudmarvel.usescases.personajes.AddPersonaje
import com.example.roomcrudmarvel.usescases.personajes.DeletePersonaje
import com.example.roomcrudmarvel.usescases.personajes.GetAllPersonajes
import com.example.roomcrudmarvel.usescases.personajes.UpdatePersonaje
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class VerInfoPersonaje : AppCompatActivity() {
    private lateinit var binding: InfoPersonajeBinding
    private lateinit var rvComics: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = InfoPersonajeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val personaje = intent.getParcelableExtra<Personaje>(getString(R.string.personaje))
        rvComics = binding.rvComics

        with(binding) {
            etNombre.setText(personaje?.nombre)
            etId.setText(personaje?.id.toString())
            etModificado.setText(personaje?.modificado.toString())
            etDescripcion.setText(personaje?.descripcion)
            ivImagen.load(personaje?.linkFoto)
        }
        rvCargarComics(personaje)
        binding.etModificado.setOnClickListener {
            showDatePickerDialog()
        }

        binding.brUpdate.setOnClickListener {
            val id: Int = binding.etId.text.toString().toInt()
            val nombre: String = binding.etNombre.text.toString()
            val descripcion: String = binding.etDescripcion.text.toString()
            val modificado: LocalDate = LocalDate.parse(
                binding.etModificado.text.toString(),
                DateTimeFormatter.ofPattern("dd-MM-yyyy")
            )
            val personajeUpdate = Personaje(
                id, nombre, descripcion, modificado,
                personaje!!.linkFoto, personaje.comics
            )
            viewModel.updatePersonaje(personajeUpdate)
            Toast.makeText(this, getString(R.string.UpdateExitoso), Toast.LENGTH_SHORT).show()
        }
    }

    private fun rvCargarComics(pers: Personaje?) {
        val comics = pers?.comics
        comics?.let {
            rvComics.adapter = ComicAdapter(it)
            rvComics.layoutManager = GridLayoutManager(this@VerInfoPersonaje, 1)
        }
    }

    private fun showDatePickerDialog() {
        val newFragment =
            DatePickerFragment.newInstance(DatePickerDialog.OnDateSetListener { _, year, month, day ->
                val selectedDate = day.toString() + "-" + (month + 1) + "-" + year
                binding.etModificado.setText(selectedDate)
            })
        newFragment.show(supportFragmentManager, "datePicker")
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
}