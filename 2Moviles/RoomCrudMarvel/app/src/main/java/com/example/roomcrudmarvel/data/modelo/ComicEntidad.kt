package com.example.roomcrudmarvel.data.modelo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "comics")
data class ComicEntidad(

    @ColumnInfo(name = "IDComic")
    @PrimaryKey(autoGenerate = true)
    val idComic: Int = 0,
    @ColumnInfo(name = "IDPersonaje")
    var idPersonaje: Int,
    @ColumnInfo(name = "Nombre")
    val nombre: String
)