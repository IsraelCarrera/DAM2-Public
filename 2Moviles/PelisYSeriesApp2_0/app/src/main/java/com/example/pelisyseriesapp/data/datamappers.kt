package com.example.pelisyseriesapp.data

import com.example.pelisyseriesapp.data.modelo.api.*
import com.example.pelisyseriesapp.data.modelo.room.PeliculaRoom
import com.example.pelisyseriesapp.data.modelo.room.SerieRoom
import com.example.pelisyseriesapp.data.modelo.room.SerieWithTemporadas
import com.example.pelisyseriesapp.data.modelo.room.TemporadaWithCapitulos
import com.example.pelisyseriesapp.data.modelo.room.entidades.*
import com.example.pelisyseriesapp.domain.*
import com.example.pelisyseriesapp.utils.Constantes


//Para los RV de buscar peli y buscar serie.
fun ResultadoBuscarPeliculas.toGenerico(): GenericoSeriesPelis {
    return GenericoSeriesPelis(
        this.id,
        this.title,
        poster = Constantes.URL_IMG + this.posterPath,
        esFavorito = false
    )
}

fun ResultadoBuscarSerie.toGenerico(): GenericoSeriesPelis {
    return GenericoSeriesPelis(
        this.id,
        this.name,
        poster = Constantes.URL_IMG + this.posterPath,
        esFavorito = false
    )
}


//Dos mapeos del buscar peli específica del retrofit.
fun PeliPorId.toPelicula(): Pelicula {
    return Pelicula(
        this.id,
        this.title,
        this.releaseDate,
        poster = Constantes.URL_IMG + this.posterPath,
        emptyList(),
        this.originalTitle,
        this.overview,
        this.originalLanguage,
        haSidoVista = false,
        esFavorita = false
    )
}

fun CastPeli.toActoresActuan(): ActoresActuan {
    val genero = when (gender) {
        1 -> Constantes.MUJER
        2 -> Constantes.HOMBRE
        else -> Constantes.NO_INDICADO
    }
    return ActoresActuan(
        this.id,
        this.character,
        genero,
        this.name,
        foto = Constantes.URL_IMG + this.profilePath
    )
}

//Funciones de buscar serie específica del retrofit
fun Season.toTemporada(id: Int): Temporada {
    return Temporada(
        id,
        this.name,
        this.overview,
        poster = Constantes.URL_IMG + this.posterPath,
        this.seasonNumber,
        this.episodeCount,
        emptyList(),
        this.airDate,
        false
    )
}

fun SeriePorId.toSerie(): Serie {
    return Serie(
        this.id,
        this.name,
        this.firstAirDate,
        this.lastAirDate,
        poster = Constantes.URL_IMG + this.posterPath,
        emptyList(),
        this.originalName,
        this.overview,
        this.originalLanguage,
        this.seasons.map { it.toTemporada(this.id) },
        haSidoVista = false,
        esFavorita = false
    )
}

fun CastSerie.toActoresActuan(): ActoresActuan {
    val genero = when (gender) {
        1 -> Constantes.MUJER
        2 -> Constantes.HOMBRE
        else -> Constantes.NO_INDICADO
    }
    return ActoresActuan(
        this.id,
        this.character,
        genero,
        this.name,
        foto = Constantes.URL_IMG + this.profilePath
    )
}

fun Episode.toEpisodio(id: Int): Episodio {
    return Episodio(
        id,
        this.seasonNumber,
        this.episodeNumber,
        this.name,
        this.overview,
        this.airDate,
        false
    )
}

//Funciones del room

//Para pasar pelicula a peliculaEntidad
fun Pelicula.toPeliculaEntidad(): PeliculaEntidad {
    val vista: Int = if (haSidoVista) {
        1
    } else {
        0
    }
    return PeliculaEntidad(
        this.id,
        this.titulo,
        this.tituloOriginal,
        this.poster,
        this.fechaEstreno,
        this.sipnosis,
        this.lenguajeOriginal,
        vista
    )
}

fun ActoresActuan.toPersonasEntidad(): PersonaEntidad {
    return PersonaEntidad(this.id, this.nombrePersonaje, this.nombreReal, this.genero, this.foto)
}


fun Pelicula.toPeliculaRoom(): PeliculaRoom {
    return PeliculaRoom(this.toPeliculaEntidad(), this.cast?.map { it.toPersonasEntidad() })
}

//Para pasar pelicula de entidad a domain
fun PersonaEntidad.toCast(): ActoresActuan {
    return ActoresActuan(
        this.idPersona,
        this.nombrePersonaje,
        this.genero,
        this.nombreReal,
        this.foto
    )
}

fun PeliculaRoom.toPelicula(): Pelicula {
    val vista: Boolean = this.pelicula.haSidoVista == 1
    return Pelicula(
        this.pelicula.idPelicula,
        this.pelicula.titulo,
        this.pelicula.fechaEstreno.toString(),
        this.pelicula.poster,
        this.casting!!.map { it.toCast() },
        this.pelicula.tituloOriginal,
        this.pelicula.sipnosis,
        this.pelicula.lenguajeOriginal,
        haSidoVista = vista,
        esFavorita = true
    )
}


fun PeliculaRoom.toFavoritos(): Favoritos {
    val vista: Boolean = this.pelicula.haSidoVista == 1
    return Favoritos(
        this.pelicula.idPelicula,
        this.pelicula.titulo,
        this.pelicula.poster,
        Constantes.PELICULA,
        vista
    )
}

//Para pasar series (y too lo que tiene) a entidad.

fun Serie.toSerieEntidad(): SerieEntidad {
    val vista: Int = if (haSidoVista) {
        1
    } else {
        0
    }
    return SerieEntidad(
        this.id,
        this.titulo,
        this.tituloOriginal,
        this.fechaEstreno,
        this.fechaFinal,
        this.poster,
        this.sipnosis,
        this.lenguajeOriginal,
        vista
    )
}

fun Temporada.toTemporadaEntidad(): TemporadaEntidad {
    val vista: Int = if (haSidoVista) {
        1
    } else {
        0
    }
    return TemporadaEntidad(
        this.id,
        this.numeroTemporada,
        this.numEpisodios,
        this.fechaEstreno,
        this.nombre,
        this.sipnopsis,
        this.poster,
        vista
    )
}

fun Episodio.toCapituloEntidad(): CapituloEntidad {
    val vista: Int = if (haSidoVisto) {
        1
    } else {
        0
    }
    return CapituloEntidad(
        this.id,
        this.numTemporada,
        this.numCapitulo,
        this.fechaEmision,
        this.nombre,
        this.sipnosis,
        vista
    )
}

fun Temporada.toTemporadaWithCapitulos(): TemporadaWithCapitulos {
    return TemporadaWithCapitulos(
        toTemporadaEntidad(),
        this.episodios?.map {
            it.toCapituloEntidad()
        })
}


fun Serie.toSerieWithTemporadas(): SerieWithTemporadas {
    return SerieWithTemporadas(
        toSerieEntidad(),
        this.temporadas?.map {
            it.toTemporadaWithCapitulos()
        })
}

fun Serie.toSerieRoom(): SerieRoom {
    return SerieRoom(
        toSerieWithTemporadas(),
        this.cast?.map {
            it.toPersonasEntidad()
        }
    )
}

//Y ahora del serieEntidad al del domain

fun CapituloEntidad.toCapitulos(): Episodio {
    val vista: Boolean = this.haSidoVisto == 1
    return Episodio(
        this.idSerie,
        this.numeroTemporada,
        this.numeroCapitulo,
        this.nombre,
        this.sipnosis,
        this.fechaEmision.toString(),
        vista
    )
}

fun TemporadaWithCapitulos.toTemporada(): Temporada {
    val vista: Boolean = this.temporada.haSidoVista == 1
    return Temporada(
        this.temporada.idSerie,
        this.temporada.nombre,
        this.temporada.sipnosis,
        this.temporada.poster,
        this.temporada.numTemporada,
        this.temporada.numeroEpisodios,
        this.capitulos!!.map { it.toCapitulos() },
        this.temporada.fechaEmision.toString(),
        vista
    )
}


fun SerieRoom.toSerie(): Serie {
    val vista: Boolean = this.serie.serie.haSidoVista == 1
    return Serie(
        this.serie.serie.idSerie,
        this.serie.serie.titulo,
        this.serie.serie.fechaEstreno.toString(),
        this.serie.serie.fechaFinal.toString(),
        this.serie.serie.poster,
        this.casting!!.map { it.toCast() },
        this.serie.serie.tituloOriginal,
        this.serie.serie.sipnosis,
        this.serie.serie.lenguajeOriginal,
        this.serie.temporadas!!.map { it.toTemporada() },
        vista,
        esFavorita = true
    )
}

fun SerieRoom.toFavoritos(): Favoritos {
    val vista: Boolean = this.serie.serie.haSidoVista == 1
    return Favoritos(
        this.serie.serie.idSerie,
        this.serie.serie.titulo,
        this.serie.serie.poster,
        Constantes.SERIE,
        vista
    )
}

//Caches

fun GenericoSeriesPelis.toPeliculaCache(): PeliculaCacheEntidad {
    return PeliculaCacheEntidad(
        this.id,
        this.titulo ?: Constantes.VACIO,
        this.poster ?: Constantes.VACIO
    )
}

fun GenericoSeriesPelis.toSerieCache(): SerieCacheEntidad {
    return SerieCacheEntidad(
        this.id,
        this.titulo ?: Constantes.VACIO,
        this.poster ?: Constantes.VACIO
    )
}

fun SerieCacheEntidad.toGenerico(): GenericoSeriesPelis {
    return GenericoSeriesPelis(this.idSerie, this.titulo, this.poster, false)
}

fun PeliculaCacheEntidad.toGenerico(): GenericoSeriesPelis {
    return GenericoSeriesPelis(this.idPelicula, this.titulo, this.poster, false)
}
