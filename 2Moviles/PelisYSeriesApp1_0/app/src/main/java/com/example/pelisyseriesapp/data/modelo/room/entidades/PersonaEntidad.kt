package com.example.pelisyseriesapp.data.modelo.room.entidades

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "PERSONA")
data class PersonaEntidad(
    @ColumnInfo(name = "idPersona")
    @PrimaryKey
    val idPersona: Int,
    @ColumnInfo(name = "nombrePersonaje")
    val nombrePersonaje: String?,
    @ColumnInfo(name = "nombreReal")
    val nombreReal: String?,
    @ColumnInfo(name = "genero")
    val genero: String?,
    @ColumnInfo(name = "foto")
    val foto: String?,
)