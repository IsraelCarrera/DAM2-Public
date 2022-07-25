package com.example.pantallacud.ui


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pantallacud.R
import com.example.pantallacud.domain.Persona

class PersonasAdapter(private val personas: List<Persona>) :
    RecyclerView.Adapter<PersonasViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonasViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PersonasViewHolder(layoutInflater.inflate(R.layout.personas_item, parent, false))
    }

    override fun onBindViewHolder(holder: PersonasViewHolder, position: Int) {
        holder.render(personas[position])
    }

    override fun getItemCount(): Int {
        return personas.size
    }

}

class PersonasViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    fun render(persona: Persona) {
        view.findViewById<TextView>(R.id.tvNombrePersonasItem).text = persona.nombre
        view.findViewById<TextView>(R.id.tvCorreoPersonasItem).text = persona.email
        if (persona.sexo) {
            view.findViewById<TextView>(R.id.tvSexoPersonasItem).text =
                view.context.getString(R.string.hm)
        } else {
            view.findViewById<TextView>(R.id.tvSexoPersonasItem).text =
                view.context.getString(R.string.mj)
        }
        view.findViewById<TextView>(R.id.tvTelefonoPersonasItem).text = persona.tel
        val juega: String = if (persona.juegaEnConsola && persona.juegaEnPc) {
            view.context.getString(R.string.juegaPcConsolas)
        } else if (persona.juegaEnConsola) {
            view.context.getString(R.string.juegaConsola)
        } else if (persona.juegaEnPc) {
            view.context.getString(R.string.juegaPc)
        } else {
            view.context.getString(R.string.noJuega)
        }

        view.findViewById<TextView>(R.id.tvJuegaEnPersonasItem).text = juega

    }
}