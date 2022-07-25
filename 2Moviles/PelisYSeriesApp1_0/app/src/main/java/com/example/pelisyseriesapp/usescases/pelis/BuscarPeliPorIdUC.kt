package com.example.pelisyseriesapp.usescases.pelis

import com.example.pelisyseriesapp.data.repositorio.PelisRepositorio
import javax.inject.Inject

class BuscarPeliPorIdUC @Inject constructor(private val pelis: PelisRepositorio) {
    suspend fun invoke(idPeli: Int) = pelis.getUnaPeli(idPeli)
}