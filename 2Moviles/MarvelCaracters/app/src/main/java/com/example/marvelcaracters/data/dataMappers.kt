package com.example.marvelcaracters.data

import com.example.marvelcaracters.data.marvel.PersonajesItem
import com.example.marvelcaracters.domain.Personaje

fun PersonajesItem.toPersonaje() : Personaje = Personaje(name,description,id,modified,thumbnail.path,thumbnail.extension,  comics.items.map { i-> i.name }.toList())