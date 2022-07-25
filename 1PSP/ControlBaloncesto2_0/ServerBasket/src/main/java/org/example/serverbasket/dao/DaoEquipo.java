package org.example.serverbasket.dao;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import org.example.common.error.ApiError;
import org.example.common.modelo.Equipo;
import org.example.serverbasket.dao.modelo.EquipoEntity;
import org.example.serverbasket.dao.utils.Constantes;
import org.example.serverbasket.utils.Querys;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Log4j2
public class DaoEquipo {
    private final DBConnPool dbConnection;

    @Inject
    public DaoEquipo(DBConnPool dbConnection) {
        this.dbConnection = dbConnection;
    }

    //Pillar todos los equipos. Para que el admin pueda verlos y poner los partidos.
    public Either<ApiError, List<Equipo>> getAllEquipos() {
        Either<ApiError, List<Equipo>> resultado;
        try {
            JdbcTemplate bd = new JdbcTemplate(dbConnection.getDataSource());
            resultado = Either.right(bd.query(Querys.SELECT_ALL_EQUIPOS, BeanPropertyRowMapper.newInstance(EquipoEntity.class))
                    .stream()
                    .map(this::pasarAEquipo)
                    .collect(Collectors.toList()));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(apiErrorGeneral());
        }

        return resultado;
    }

    //Añadir un equipo
    public Either<ApiError, Equipo> addEquipo(Equipo equipo) {
        Either<ApiError, Equipo> resultado;
        try {
            JdbcTemplate bd = new JdbcTemplate(dbConnection.getDataSource());
            bd.update(Querys.INSERT_EQUIPO_QUERY, equipo.getNombreEquipo().toLowerCase());
            resultado = Either.right(equipo);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(apiErrorGeneral());
        }

        return resultado;
    }

    //Eliminar un equipo
    public Either<ApiError, Equipo> deleteEquipo(Equipo equipo) {
        Either<ApiError, Equipo> resultado;
        try {
            JdbcTemplate bd = new JdbcTemplate(dbConnection.getDataSource());
            bd.update(Querys.DELETE_EQUIPO_QUERY, equipo.getNombreEquipo());
            resultado = Either.right(equipo);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(apiErrorGeneral());
        }
        return resultado;
    }

    //Update equipo
    public Either<ApiError, Equipo> updateEquipo(Equipo equipo, String nombreEquipoNuevo) {
        Either<ApiError, Equipo> resultado;
        try {
            JdbcTemplate bd = new JdbcTemplate(dbConnection.getDataSource());
            bd.update(Querys.UPDATE_EQUIPO_QUERY, nombreEquipoNuevo.toLowerCase(), equipo.getNombreEquipo());
            equipo.setNombreEquipo(nombreEquipoNuevo);
            resultado = Either.right(equipo);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(apiErrorGeneral());
        }
        return resultado;
    }

    //En este caso creo que getUnEquipo no tiene sentido, ya que nunca buscaremos UN solo equipo, de momento.
    //Para pasarlos a equipos normales:
    private Equipo pasarAEquipo(EquipoEntity equipoEntity) {
        return Equipo.builder()
                .nombreEquipo(equipoEntity.getNombreEquipo())
                .build();
    }

    private ApiError apiErrorGeneral() {
        return ApiError.builder()
                .mensaje(Constantes.FALLO_GENERAL)
                .fecha(LocalDate.now())
                .build();
    }
}
