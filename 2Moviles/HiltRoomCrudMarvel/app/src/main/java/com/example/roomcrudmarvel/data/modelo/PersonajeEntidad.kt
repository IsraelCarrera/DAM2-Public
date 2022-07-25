package com.example.roomcrudmarvel.data.modelo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "Heroes")
data class PersonajeEntidad(

    @ColumnInfo(name = "ID")
    @PrimaryKey
    var id: Int,
    @ColumnInfo(name = "Nombre")
    val nombre: String,
    @ColumnInfo(name = "Descripcion")
    val descripcion: String,
    @ColumnInfo(name = "Modificado")
    val fechaModificado: LocalDate,
    @ColumnInfo(name = "LinkFoto")
    val linkFoto: String
)