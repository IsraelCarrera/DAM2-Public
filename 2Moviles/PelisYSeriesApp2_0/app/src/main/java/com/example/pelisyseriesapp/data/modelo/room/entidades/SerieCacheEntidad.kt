package com.example.pelisyseriesapp.data.modelo.room.entidades

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "SERIECACHE")
data class SerieCacheEntidad (
    @ColumnInfo(name = "idSerie")
    @PrimaryKey
    val idSerie: Int,
    @ColumnInfo(name = "titulo")
    val titulo: String,
    @ColumnInfo(name = "poster")
    val poster: String,

        )