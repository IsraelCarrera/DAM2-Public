package com.example.pelisyseriesapp.usescases.pelis

import com.example.pelisyseriesapp.data.repositorio.PelisRepositorio
import javax.inject.Inject

class GetAllPelisRoomUC @Inject constructor(private val pelis: PelisRepositorio) {
    fun invoke() = pelis.getAllPelisRoom()
}