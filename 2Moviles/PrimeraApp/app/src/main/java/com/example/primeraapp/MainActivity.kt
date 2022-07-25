package com.example.primeraapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val user = this.findViewById<EditText>(R.id.EditTextUser)
        val pass = this.findViewById<EditText>(R.id.EditTextPass)
        val botonAcc = this.findViewById<Button>(R.id.botonAcceder)
        botonAcc.setOnClickListener {
            if (user.text.toString() == "root" && pass.text.toString() == "root"){
                Toast.makeText(this, "Has logeado con éxito", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, "Error. Usuario y/o contraseña equivocada.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}