package com.example.pelisyseriesapp.data.sources.local

import androidx.room.*
import com.example.pelisyseriesapp.data.modelo.room.SerieRoom
import com.example.pelisyseriesapp.data.modelo.room.entidades.*


@Dao
interface SerieLocalDao {

    //Inser de series, con sus capitulos, temporadas, y cast.
    //Insertar episodios
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertEpisodios(episodios: List<CapituloEntidad>)

    //Insertar temporadas
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTemporada(tempo: TemporadaEntidad)

    //Insertar cast
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCast(cast: List<PersonaEntidad>)

    //Insertamos pelicula

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(serie: SerieEntidad)

    //Insertamos vínculo
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertRelation(rel: List<SeriePersonaParticipanRelacion>)

    //Procedemos a hacer too
    @Transaction
    suspend fun addSerie(serie: SerieRoom) {
        //Añadimos la serie
        insert(serie.serie.serie)
        //Ahora añadimos las temporadas
        serie.serie.temporadas?.apply {
            forEach { insertTemporada(it.temporada) }
        }
        //insertamos los episodios
        serie.serie.temporadas?.map {
            it.capitulos?.apply {
                insertEpisodios(this)
            }
        }
        //Ahora añadimos el cast
        serie.casting?.apply {
            insertCast(this)
        }
        //Y ya añadimos la relación
        serie.casting?.apply {
            val relacion = mutableListOf<SeriePersonaParticipanRelacion>()
            forEach {
                relacion.add(
                    SeriePersonaParticipanRelacion(
                        serie.serie.serie.idSerie,
                        it.idPersona
                    )
                )
            }
            insertRelation(relacion)
        }
    }
    //Fin de insertar series con too

    //GetAll
    @Transaction
    @Query("Select * from SERIE")
    suspend fun getAllSeries(): List<SerieRoom>

    //getOne

    @Transaction
    @Query("SELECT * from SERIE where SERIE.idSerie=:idSerie")
    suspend fun getOneSerie(idSerie: Int): List<SerieRoom>

    //Update. Para modificar que la serie ha sido vista.
    @Update
    suspend fun updateSerie(serie: SerieEntidad)

    //Los deletes
    @Delete
    suspend fun deleteSerie(serie: SerieEntidad)

    @Delete
    suspend fun deleteTemporada(temporadaEntidad: TemporadaEntidad)

    @Delete
    suspend fun deleteCapitulos(capitulos: List<CapituloEntidad>)


    @Transaction
    suspend fun deleteSerie(serie: SerieRoom) {
        serie.serie.temporadas?.map {
            it.capitulos?.let { it1 -> deleteCapitulos(it1) }
            deleteTemporada(it.temporada)
        }
        deleteSerie(serie.serie.serie)
    }

    //Update episodio
    @Update
    suspend fun updateEpisodio(episodio: CapituloEntidad)

    //Update temporada
    @Update
    suspend fun updateTemporada(temporada: TemporadaEntidad)
}