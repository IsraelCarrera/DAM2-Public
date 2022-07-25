package com.example.appcompose.data.modelo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "gatitos")
data class GatitosEntidad(

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    @ColumnInfo(name = "nombre")
    val nombre: String,
    @ColumnInfo(name = "apellidos")
    val apellidos: String,
    @ColumnInfo(name = "dueno")
    val dueno: String,
    @ColumnInfo(name = "edad")
    val edad: Int,
    @ColumnInfo(name = "foto")
    val foto: String?
)
