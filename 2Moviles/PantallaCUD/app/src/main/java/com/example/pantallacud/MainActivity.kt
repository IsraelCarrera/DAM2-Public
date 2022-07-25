package com.example.pantallacud



import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {

    //Los botones:
    private lateinit var botonAdd : Button
    private lateinit var botonDelete : Button
    private lateinit var botonUpdate : Button
    private lateinit var botonAtras : Button
    private lateinit var botonAlante : Button


    //Variables a pillar:
    private lateinit var etNombre : TextInputEditText
    private lateinit var etPass : TextInputEditText
    private lateinit var etEmail : TextInputEditText
    private lateinit var etTel : TextInputEditText
    private lateinit var smSuscr : SwitchMaterial
    private lateinit var rbHombre : RadioButton
    private lateinit var rbMujer : RadioButton
    private lateinit var cbPc : CheckBox
    private lateinit var cbConsolas : CheckBox
    private lateinit var tvContador : TextView
    private lateinit var tvListaTotal : TextView
    //Para ayudarme, haré un contador
    private var contador : Int = 0

    private var listPersonas = mutableListOf<Persona>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Los botones:
        botonAdd = this.findViewById(R.id.btAdd)
        botonDelete = this.findViewById(R.id.btDelete)
        botonUpdate = this.findViewById(R.id.btUpdate)
        botonAtras = this.findViewById(R.id.btAtras)
        botonAlante = this.findViewById(R.id.btDelante)


        //Variables a pillar:
        etNombre = this.findViewById(R.id.tfNombre)
        etPass = this.findViewById(R.id.tfPass)
        etEmail = this.findViewById(R.id.tfEmail)
        etTel = this.findViewById(R.id.tfTelefono)
        smSuscr = this.findViewById(R.id.smSuscribe)
        rbHombre = this.findViewById(R.id.rbSexoHombre)
        rbMujer = this.findViewById(R.id.rbSexoMujer)
        cbPc = this.findViewById(R.id.cbPc)
        cbConsolas = this.findViewById(R.id.cbConsolas)
        tvContador = this.findViewById(R.id.tvContador)
        tvListaTotal= this.findViewById(R.id.tvLista)

        tvContador.text = contador.toString()
        listPersonas = mutableListOf()
        tvListaTotal.text = listPersonas.size.toString()

        //Acciones
        accionesDelPrograma()
    }

    private fun accionesDelPrograma(){
        //Añadir persona
        botonAdd.setOnClickListener {
            annadirPersona()
        }

        //Delete
        botonDelete.setOnClickListener {
            deletePersona()
        }

        //Update
        botonUpdate.setOnClickListener {
            updatePersona()
        }

        //Ir a la derecha
        botonAlante.setOnClickListener {
            irDerecha()
        }

        //Ir a la izquierda
        botonAtras.setOnClickListener {
            irIzquierda()
        }
    }

    private fun annadirPersona(){
        if(!estaEnLista(contador)){
            val nombre: String = etNombre.text.toString()
            val pass: String = etPass.text.toString()
            val mail: String = etEmail.text.toString()
            val tel: String = etTel.text.toString()
            val suscr: Boolean = smSuscr.isChecked
            val juegaPc: Boolean = cbPc.isChecked
            val juegaConsola: Boolean = cbConsolas.isChecked
            val sexo: Boolean = rbHombre.isChecked
            if(nombre.isEmpty() || pass.isEmpty() || mail.isEmpty() || tel.isEmpty()) {
                Toast.makeText(this, "No se ha agregado a la persona indicada.", Toast.LENGTH_SHORT).show()
            } else{
                val persona = Persona(contador,nombre, pass, tel, mail, sexo, suscr, juegaPc, juegaConsola)
                (listPersonas.add(contador,persona))
                Toast.makeText(this, "la persona llamada ${nombre} se ha registrado con éxito", Toast.LENGTH_SHORT).show()
                tvListaTotal.text = listPersonas.size.toString()
            }
        }else{
            Toast.makeText(this, "Ya hay una persona grabada aquí", Toast.LENGTH_SHORT).show()
        }
    }

    private fun deletePersona(){
        if(estaEnLista(contador)){
            val persona = cogerPersona()
            //Borramos a la persona. No quiero hacerlo por index por si da error.
            listPersonas.remove(persona)
            //Como ahora el array decrece y to-do se va hacia atrás, llevamos al contador hacia atrás, salvo si es 0, que no lo llevamos hacia atras. Además, corremos 1 el ID de todos.
            //Sé que es una guarrada, pero no encontré otra forma, estoy espeso.
            var aux = contador
            for (personas in listPersonas){
                if(aux == personas.id)
                    personas.id = aux-1
                aux++
            }
            //Comprobamos si hay alguien en la dicha parte del ID, si lo hay, se imprime, sino, se limpia.
            if(estaEnLista(contador)){
                ponerPersona(contador)
            }else{
                limpiarLista()
            }
            Toast.makeText(this, "Persona borrada con éxito", Toast.LENGTH_SHORT).show()
            tvListaTotal.text = listPersonas.size.toString()
            tvContador.text = contador.toString()
        }else{
            Toast.makeText(this, "No hay persona aquí. No puedes borrar a nadie que no existe.", Toast.LENGTH_SHORT).show()
        }

    }

    private fun updatePersona(){
        if(estaEnLista(contador)){
            val nombre: String = etNombre.text.toString()
            val pass: String = etPass.text.toString()
            val mail: String = etEmail.text.toString()
            val tel: String = etTel.text.toString()
            val suscr: Boolean = smSuscr.isChecked
            val juegaPc: Boolean = cbPc.isChecked
            val juegaConsola: Boolean = cbConsolas.isChecked
            val sexo: Boolean = rbHombre.isChecked
            if(nombre.isEmpty() || pass.isEmpty() || mail.isEmpty() || tel.isEmpty()) {
                Toast.makeText(this, "No se ha modificado a la persona indicada.", Toast.LENGTH_SHORT).show()
            } else{
                val persona = Persona(contador,nombre, pass, tel, mail, sexo, suscr, juegaPc, juegaConsola)
                listPersonas.set(contador,persona)
                Toast.makeText(this, "la persona llamada ${nombre} ha recibido la modificación que ha indicado", Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(this, "No hay persona guardada aquí. Primero, guarda una persona.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun irDerecha(){
        if(listPersonas.size-1 < contador){
            Toast.makeText(this, "No se puede ir a la derecha.", Toast.LENGTH_SHORT).show()
        }else {
            //A nuestro contador le sumamos uno más
            contador++
            tvContador.text = contador.toString()
            tvListaTotal.text = listPersonas.size.toString()
            //Comprobamos que tenemos a alguien con ese id
            if (estaEnLista(contador)) {
                ponerPersona(contador)
            } else {
                //Y si no tenemos a nadie, lo limpiamos
                limpiarLista()
            }
        }
    }

    private fun irIzquierda(){
        if(contador!=0){
            contador--
            tvListaTotal.text = listPersonas.size.toString()
            tvContador.text = contador.toString()
            //Comprobamos si tenemos a alguien grabado aquí.
            if(estaEnLista(contador)){
                ponerPersona(contador)
            }
        }else{
            Toast.makeText(this, "No se puede ir a la izquierda.", Toast.LENGTH_SHORT).show()
        }
    }

    //Coger datos de una persona
    private fun cogerPersona():Persona{
        val nombre: String = etNombre.text.toString()
        val pass: String = etPass.text.toString()
        val mail: String = etEmail.text.toString()
        val tel: String = etTel.text.toString()
        val suscr: Boolean = smSuscr.isChecked
        val juegaPc: Boolean = cbPc.isChecked
        val juegaConsola: Boolean = cbConsolas.isChecked
        val sexo: Boolean = rbHombre.isChecked
        val persona = Persona(contador,nombre, pass, tel, mail, sexo, suscr, juegaPc, juegaConsola)
        return persona
    }

    //Poner a la persona
    private fun ponerPersona(contador: Int){
        etNombre.setText(listPersonas[contador].nombre)
        etPass.setText(listPersonas[contador].pass)
        etEmail.setText(listPersonas[contador].email)
        etTel.setText(listPersonas[contador].tel)
        smSuscr.isChecked = listPersonas[contador].suscribirse
        cbPc.isChecked = listPersonas[contador].juegaEnPc
        cbConsolas.isChecked = listPersonas[contador].juegaEnConsola
        if (listPersonas[contador].sexo) {
            rbHombre.isChecked = true
            rbMujer.isChecked = false
        } else {
            rbMujer.isChecked = true
            rbHombre.isChecked = false
        }
    }

    //Limpiar lista
    private fun limpiarLista(){
        etNombre.text?.clear()
        etPass.text?.clear()
        etEmail.text?.clear()
        etTel.text?.clear()
        smSuscr.isChecked = false
        cbPc.isChecked = false
        cbConsolas.isChecked = false
        rbHombre.isChecked = false
        rbMujer.isChecked = false
    }

    private fun estaEnLista(id: Int): Boolean {
        var esta = false
        for (personas in listPersonas){
            if(id == personas.id) run {
                esta = true
            }
        }
        return esta
    }

}
