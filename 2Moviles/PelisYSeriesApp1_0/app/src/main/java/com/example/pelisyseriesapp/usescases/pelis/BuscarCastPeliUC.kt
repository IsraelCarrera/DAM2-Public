package com.example.pelisyseriesapp.usescases.pelis

import com.example.pelisyseriesapp.data.repositorio.PelisRepositorio
import javax.inject.Inject

class BuscarCastPeliUC @Inject constructor(private val pelis: PelisRepositorio) {
    suspend fun invoke(id: Int) = pelis.buscarCastPeli(id)
}