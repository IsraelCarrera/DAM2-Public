package com.example.marvelcaracters.ui


import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.marvelcaracters.R
import com.example.marvelcaracters.data.PersonajesRepository
import com.example.marvelcaracters.databinding.PersonajesItemBinding
import com.example.marvelcaracters.domain.Personaje
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

class PersonajeAdapter(
    private var personajes: List<Personaje>,
    private val clickDelete: (Personaje) -> Unit,
    private val volverAdd: (Int,Personaje) -> Unit
) : RecyclerView.Adapter<PersonajesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonajesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PersonajesViewHolder(layoutInflater.inflate(R.layout.personajes_item, parent, false))
    }

    override fun onBindViewHolder(holder: PersonajesViewHolder, position: Int) {
        holder.render(personajes[position], clickDelete, volverAdd)
    }

    override fun getItemCount(): Int {
        return personajes.size
    }
}

class PersonajesViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    val binding = PersonajesItemBinding.bind(view)

    fun render(personaje: Personaje, clickDelete: (Personaje) -> Unit, volverAdd: (Int,Personaje) -> Unit) {
        with(binding) {
            tvNombrePersonajeRV.text = personaje.nombre
            ivImagenDelPrincipal.load(
                view.context.getString(R.string.imagen)
                    .format(personaje?.linkFoto, personaje?.extensionFoto)
            )
        }

        binding.brDeleteRV.setOnClickListener {
            val avisoDelete = MaterialAlertDialogBuilder(it.context)
                .setTitle(it.context.getString(R.string.DeleteTitle))
                .setMessage(it.context.getString(R.string.deleteBody))
                .setNegativeButton(it.context.getString(R.string.no)) { view, _ -> view.dismiss() }
                .setPositiveButton(it.context.getString(R.string.si)) { view, _ ->
                    PersonajesRepository().deletePersonaje(personaje)
                    clickDelete(personaje)
                    view.dismiss()
                    Snackbar.make(it, it.context.getString(R.string.personajeEliminado), Snackbar.LENGTH_LONG)
                        .setAction(it.context.getString(R.string.deshacer)) {
                            PersonajesRepository().addPersonaje(position, personaje)
                            volverAdd(position,personaje)
                        }.show()
                }
                .setCancelable(false)
                .create()
            avisoDelete.show()
        }
        binding.btIrInfoRV.setOnClickListener {
            val intent = Intent(it.context, VerInfoPersonas::class.java)
            intent.putExtra(it.context.getString(R.string.personaje), personaje)
            it.context.startActivity(intent)
        }
    }
}

