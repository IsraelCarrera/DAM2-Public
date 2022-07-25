package com.example.pelisyseriesapp.usescases.pelis

import com.example.pelisyseriesapp.data.repositorio.PelisRepositorio
import javax.inject.Inject

class GetOnePeliculaRoomUC @Inject constructor(
    private val pelis: PelisRepositorio) {
     fun invoke(id: Int) = pelis.getOnePeliculaRoom(id)
}