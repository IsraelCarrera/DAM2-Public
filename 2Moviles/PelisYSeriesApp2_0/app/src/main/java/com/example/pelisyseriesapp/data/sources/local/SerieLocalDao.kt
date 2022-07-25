package com.example.pelisyseriesapp.data.sources.local

import androidx.room.*
import com.example.pelisyseriesapp.data.modelo.room.SerieRoom
import com.example.pelisyseriesapp.data.modelo.room.entidades.*
import kotlinx.coroutines.flow.Flow


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
    fun getAllSeries(): Flow<List<SerieRoom>>

    //getOne
    @Transaction
    @Query("SELECT * from SERIE where idSerie=:idSerie")
    fun getOneSerie(idSerie: Int): Flow<List<SerieRoom>>

    //Get episodios de una temporada
    @Query("SELECT * from CAPITULO where idSerie=:idSerie and numeroTemporada=:numTemporada")
    fun getEpisodiosTemporada(idSerie: Int, numTemporada: Int): Flow<List<CapituloEntidad>>

    //Update. Para modificar que la serie ha sido vista.
    @Update
    suspend fun updateSerie(serie: SerieEntidad)

    //Los deletes
    @Query("DELETE FROM SERIE WHERE idSerie = :idSerie")
    suspend fun deleteSerie(idSerie: Int)

    @Query("DELETE FROM TEMPORADA WHERE idSerie = :idSerie")
    suspend fun deleteTemporada(idSerie: Int)

    @Query("DELETE FROM CAPITULO WHERE idSerie = :idSerie")
    suspend fun deleteCapitulos(idSerie: Int)


    @Transaction
    suspend fun deleteAllSerie(idSerie: Int) {
        deleteCapitulos(idSerie)
        deleteTemporada(idSerie)
        deleteSerie(idSerie)
    }

    //Update episodio
    @Update
    suspend fun updateEpisodio(episodio: CapituloEntidad)

    //Update temporada
    @Update
    suspend fun updateTemporada(temporada: TemporadaEntidad)

    //Caché
    //Idem que en pelis.
    @Query("select * from SERIECACHE")
    suspend fun getAllSeriesCacheadas(): List<SerieCacheEntidad>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSerieCacheada(series: List<SerieCacheEntidad>)

    @Query("DELETE FROM SERIECACHE")
    suspend fun deleteSerieCacheadas()

    @Transaction
    suspend fun todoSerieCache(series: List<SerieCacheEntidad>) {
        deleteSerieCacheadas()
        insertSerieCacheada(series)
    }
}