package com.example.roomcrudmarvel.ui.crearPersonaje

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.roomcrudmarvel.R
import com.example.roomcrudmarvel.databinding.CreatePersonajeBinding

import com.example.roomcrudmarvel.domain.Comic
import com.example.roomcrudmarvel.domain.Personaje
import com.example.roomcrudmarvel.ui.infoPersonaje.ComicAdapter
import com.example.roomcrudmarvel.ui.utils.DatePickerFragment
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class CrearPersonaje : AppCompatActivity() {
    private lateinit var binding: CreatePersonajeBinding
    private lateinit var comicAdapter: ComicAdapter
    private val viewModel: CrearPersViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CreatePersonajeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val comics: MutableList<Comic> = mutableListOf()
        comicAdapter = ComicAdapter()
        binding.rvComics.adapter = comicAdapter
        binding.etModificado.setOnClickListener {
            showDatePickerDialog()
        }

        viewModel.comics.observe(this, { comic ->
            comicAdapter.submitList(comic)
        })
        viewModel.error.observe(this, {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
        binding.bnAddComic.setOnClickListener {
            crearComic(comics)
        }
        binding.bnCrear.setOnClickListener {
            crearPersonaje(comics)
        }
    }

    private fun crearComic(comics: MutableList<Comic>) {
        try {
            val id = binding.etIdComic.text.toString().toInt()
            val nombre = binding.etNombreComic.text.toString()
            if (nombre.isEmpty() || binding.etIdComic.text.toString().isEmpty()) {
                Toast.makeText(this, getString(R.string.noInfoComic), Toast.LENGTH_SHORT)
                    .show()
            } else {
                val comic = Comic(id, 0, nombre)
                //Pongo el ID del personaje a 0 y luego, antes de añadirlo, los modifico al id real.
                comics.add(comic)
                viewModel.addComics(comic)
            }
        } catch (e: Exception) {
            Toast.makeText(this, getString(R.string.noInfoComic), Toast.LENGTH_SHORT).show()
        }
    }

    private fun crearPersonaje(comics: MutableList<Comic>) {
        try {
            val id: Int = binding.etId.text.toString().toInt()
            val nombre: String = binding.etNombre.text.toString()
            val descripcion: String = binding.etDescripcion.text.toString()
            val modificado: LocalDate = LocalDate.parse(
                binding.etModificado.text.toString(),
                DateTimeFormatter.ofPattern(getString(R.string.patterDate))
            )
            //Comprobamos si hay vacios.
            if (nombre.isEmpty() || descripcion.isEmpty()) {
                Toast.makeText(this, getString(R.string.noAddPersonaje), Toast.LENGTH_SHORT)
                    .show()
            } else {
                val personajeNuevo: Personaje = if (comics.isNotEmpty()) {
                    ponerIdPersonajeAComics(comics, id)
                    Personaje(
                        id,
                        nombre,
                        descripcion,
                        modificado,
                        getString(R.string.imagenAdd),
                        comics
                    )
                } else {
                    Personaje(
                        id,
                        nombre,
                        descripcion,
                        modificado,
                        getString(R.string.imagenAdd),
                        null
                    )
                }
                viewModel.addPersonaje(personajeNuevo)
                Toast.makeText(this, getString(R.string.addCorrectamente), Toast.LENGTH_SHORT)
                    .show()
                finish()
            }
        } catch (e: Exception) {
            Toast.makeText(this, getString(R.string.noAddPersonaje), Toast.LENGTH_SHORT)
                .show()

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
                val selectedDate = getString(R.string.formato_fecha)
                    .format(year,(month + 1),day)
                binding.etModificado.setText(selectedDate)
            })
        newFragment.show(supportFragmentManager, getString(R.string.datePicker))
    }
}