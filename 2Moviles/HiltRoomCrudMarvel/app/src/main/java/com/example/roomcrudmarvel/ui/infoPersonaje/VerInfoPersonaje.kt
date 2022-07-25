package com.example.roomcrudmarvel.ui.infoPersonaje

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.example.roomcrudmarvel.R
import com.example.roomcrudmarvel.databinding.InfoPersonajeBinding
import com.example.roomcrudmarvel.domain.Personaje
import com.example.roomcrudmarvel.ui.utils.DatePickerFragment
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@AndroidEntryPoint
class VerInfoPersonaje : AppCompatActivity() {
    private lateinit var binding: InfoPersonajeBinding
    private lateinit var comicsAdapter: ComicAdapter
    private val viewModel: InfoPersViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = InfoPersonajeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val personaje = intent.getParcelableExtra<Personaje>(getString(R.string.personaje))

        comicsAdapter = ComicAdapter()
        showPersonaje(personaje)
        viewModel.comics.observe(this, { comic -> comicsAdapter.submitList(comic) })
        personaje?.let { viewModel.getAllComics(it.id) }
        viewModel.error.observe(this, {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })

        binding.brUpdate.setOnClickListener {
            updatePersonaje(personaje)
        }
    }

    private fun updatePersonaje(personaje: Personaje?) {
        val id: Int = binding.etId.text.toString().toInt()
        val nombre: String = binding.etNombre.text.toString()
        val descripcion: String = binding.etDescripcion.text.toString()
        val modificado: LocalDate = LocalDate.parse(
            binding.etModificado.text.toString(),
            DateTimeFormatter.ofPattern(getString(R.string.patterDate))
        )
        val personajeUpdate = Personaje(
            id, nombre, descripcion, modificado,
            personaje!!.linkFoto, personaje.comics
        )
        viewModel.updatePersonaje(personajeUpdate)
        Toast.makeText(this, getString(R.string.UpdateExitoso), Toast.LENGTH_SHORT).show()

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

    private fun showPersonaje(personaje: Personaje?) {
        with(binding) {
            etNombre.setText(personaje?.nombre)
            etId.setText(personaje?.id.toString())
            etModificado.setText(personaje?.modificado.toString())
            etDescripcion.setText(personaje?.descripcion)
            ivImagen.load(personaje?.linkFoto)
            rvComics.adapter = comicsAdapter
        }

        binding.etModificado.setOnClickListener {
            showDatePickerDialog()
        }
    }
}