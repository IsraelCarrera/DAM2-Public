package com.example.roomcrudmarvel.ui.personajes

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.roomcrudmarvel.R
import com.example.roomcrudmarvel.data.PersonajeDatabaseRoom
import com.example.roomcrudmarvel.data.PersonajeRepository
import com.example.roomcrudmarvel.databinding.CreatePersonajeBinding
import com.example.roomcrudmarvel.domain.Comic
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

class CrearPersonaje : AppCompatActivity() {
    private lateinit var binding: CreatePersonajeBinding
    private lateinit var rvComics: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CreatePersonajeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        rvComics = binding.rvComics

        binding.etModificado.setOnClickListener {
            showDatePickerDialog()
        }

        val comics: MutableList<Comic> = mutableListOf()
        rvCargarComics(comics)
        binding.bnAddComic.setOnClickListener {
            try {
                val id = binding.etIdComic.text.toString().toInt()
                val nombre = binding.etNombreComic.text.toString()
                if (nombre.isEmpty() || binding.etIdComic.text.toString().isEmpty()) {
                    Toast.makeText(this, getString(R.string.no_info_comic), Toast.LENGTH_SHORT)
                        .show()
                } else {
                    val comic = Comic(id, 0, nombre)
                    //Pongo el ID del personaje a 0 y luego, antes de añadirlo, los modifico al id real.
                    comics.add(comic)
                    //Lo recargamos para ver los que hay
                    rvComics.adapter?.notifyDataSetChanged()
                }
            } catch (e: Exception) {
                Toast.makeText(this, getString(R.string.no_info_comic), Toast.LENGTH_SHORT).show()
            }
        }
        binding.bnCrear.setOnClickListener {
            try {
                val id: Int = binding.etId.text.toString().toInt()
                val nombre: String = binding.etNombre.text.toString()
                val descripcion: String = binding.etDescripcion.text.toString()
                val modificado: LocalDate = LocalDate.parse(
                    binding.etModificado.text.toString(),
                    DateTimeFormatter.ofPattern("dd-MM-yyyy")
                )
                //Comprobamos si hay vacios.
                if (nombre.isEmpty() || descripcion.isEmpty()) {
                    Toast.makeText(this,getString(R.string.no_add_personaje), Toast.LENGTH_SHORT).show()
                } else {
                    val personajeNuevo: Personaje = if (comics.isNotEmpty()) {
                        ponerIdPersonajeAComics(comics, id)
                        Personaje(
                            id,
                            nombre,
                            descripcion,
                            modificado,
                            getString(R.string.imagen_add),
                            comics
                        )
                    } else {
                        Personaje(
                            id,
                            nombre,
                            descripcion,
                            modificado,
                            getString(R.string.imagen_add),
                            null
                        )
                    }
                    viewModel.addPersonaje(personajeNuevo)
                    finish()
                }
            } catch (e: Exception) {
                Toast.makeText(this, getString(R.string.no_add_personaje), Toast.LENGTH_SHORT)
                    .show()

            }
        }
    }

    private fun rvCargarComics(comics: MutableList<Comic>) {
        comics.let {
            rvComics.adapter = ComicAdapter(it)
            rvComics.layoutManager = GridLayoutManager(this@CrearPersonaje, 1)
        }
    }

    private fun ponerIdPersonajeAComics(comics: MutableList<Comic>, id: Int) {
        for (c in comics) {
            c.idPersonaje = id
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