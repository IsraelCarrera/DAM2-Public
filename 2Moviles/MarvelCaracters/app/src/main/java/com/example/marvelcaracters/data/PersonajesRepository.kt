package com.example.marvelcaracters.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.marvelcaracters.data.marvel.PersonajesItem
import com.example.marvelcaracters.domain.Personaje
import com.squareup.moshi.FromJson
import com.squareup.moshi.Moshi
import com.squareup.moshi.ToJson
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.io.InputStream
import java.lang.reflect.Type
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class PersonajesRepository {

    class LocalDateTimeAdapter {
        @RequiresApi(Build.VERSION_CODES.O)
        @ToJson
        fun toJson(value: LocalDateTime): String {
            return FORMATTER.format(value)
        }

        @RequiresApi(Build.VERSION_CODES.O)
        @FromJson
        fun fromJson(value: String): LocalDateTime {
            return LocalDateTime.from(FORMATTER.parse(value))
        }

        companion object {
            @RequiresApi(Build.VERSION_CODES.O)
            private val FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss-SSSS")
        }
    }

    companion object {
        private val lista = mutableListOf<Personaje>()
    }

    constructor(file: InputStream? = null) {
        if (lista.size == 0) {
            val moshi = Moshi.Builder().add(LocalDateTimeAdapter())
                .addLast(KotlinJsonAdapterFactory())
                .build()
            val listOfCardsType: Type =
                Types.newParameterizedType(MutableList::class.java, PersonajesItem::class.java)
            val lectura = moshi.adapter<List<PersonajesItem>>(listOfCardsType)
                .fromJson(file?.bufferedReader()?.readText())

            lectura?.let { lista.addAll(it.map { it.toPersonaje() }) }
        }
    }

    fun getLista(): List<Personaje> {
        return lista
    }

    fun deletePersonaje(personaje: Personaje) {
        lista.remove(personaje)
    }

    fun addPersonaje(n: Int,personaje: Personaje) {
        lista.add(n, personaje)
    }
}