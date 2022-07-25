package com.example.marvelcaracters.ui


import android.app.DatePickerDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.marvelcaracters.R
import com.example.marvelcaracters.databinding.InfoPersonajeBinding
import com.example.marvelcaracters.domain.Personaje
import com.example.marvelcaracters.ui.dialog.DatePickerFragment


class VerInfoPersonas : AppCompatActivity() {
    private lateinit var binding: InfoPersonajeBinding
    private lateinit var rvComics: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = InfoPersonajeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val personaje = intent.getParcelableExtra<Personaje>(getString(R.string.personaje))
        rvComics = binding.rvComics

        with(binding) {
            tvNombre.text = personaje?.nombre
            tvId.text = personaje?.id.toString()
            tvModificado.setText(personaje?.modificado.toString())
            tvDescripcion.text = personaje?.descripcion
            ivImagen.load(
                getString(R.string.imagen).format(
                    personaje?.linkFoto,
                    personaje?.extensionFoto
                )
            )
        }
        rvCargarComics(personaje)
        binding.tvModificado.setOnClickListener {
            showDatePickerDialog()
        }
    }

    private fun rvCargarComics(pers: Personaje?) {
        val comics = pers?.comics
        comics?.let {
            rvComics.adapter = ComicAdapter(it)
            rvComics.layoutManager = GridLayoutManager(this@VerInfoPersonas, 1)
        }
    }

    private fun showDatePickerDialog() {
        val newFragment =
            DatePickerFragment.newInstance(DatePickerDialog.OnDateSetListener { _, year, month, day ->
                val selectedDate = day.toString() + " / " + (month + 1) + " / " + year
                binding.tvModificado.setText(selectedDate)
            })
        newFragment.show(supportFragmentManager, "datePicker")
    }
}