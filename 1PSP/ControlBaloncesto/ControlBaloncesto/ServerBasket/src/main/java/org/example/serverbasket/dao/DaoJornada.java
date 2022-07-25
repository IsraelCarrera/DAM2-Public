package org.example.serverbasket.dao;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import org.example.common.error.ApiError;
import org.example.common.modelo.Jornada;
import org.example.serverbasket.dao.modelo.JornadaEntity;
import org.example.serverbasket.dao.utils.Constantes;
import org.example.serverbasket.utils.Querys;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public class DaoJornada {
    private final DBConnPool dbConnection;

    @Inject
    public DaoJornada(DBConnPool dbConnection) {
        this.dbConnection = dbConnection;
    }

    //Pillar todas las jornadas. Para que el admin pueda verlos y poner los partidos correspondientes.
    public Either<ApiError, List<Jornada>> getAllJornadas() {
        Either<ApiError, List<Jornada>> resultado;
        try {
            JdbcTemplate bd = new JdbcTemplate(dbConnection.getDataSource());
            resultado = Either.right(bd.query(Querys.SELECT_ALL_JORNADAS, BeanPropertyRowMapper.newInstance(JornadaEntity.class))
                    .stream()
                    .map(this::pasarAJornada)
                    .collect(Collectors.toList()));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(apiErrorGeneral());
        }
        return resultado;
    }

    //Añadir una jornada
    public Either<ApiError, Jornada> addJornada(Jornada jornada) {
        Either<ApiError, Jornada> resultado;
        try {
            KeyHolder kh = new GeneratedKeyHolder();
            JdbcTemplate bd = new JdbcTemplate(dbConnection.getDataSource());

            bd.update(conn -> {
                PreparedStatement ps = conn
                        .prepareStatement(Querys.INSERT_JORNADA_QUERY, Statement.RETURN_GENERATED_KEYS);
                ps.setDate(1, Date.valueOf(jornada.getFechaJornada()));
                return ps;
            }, kh);
            jornada.setIdJornada(kh.getKey().intValue());
            resultado = Either.right(jornada);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(apiErrorGeneral());
        }

        return resultado;
    }

    //Eliminar una jornada
    public Either<ApiError, Integer> deleteJornada(int idJornada) {
        Either<ApiError, Integer> resultado;
        try {
            JdbcTemplate bd = new JdbcTemplate(dbConnection.getDataSource());
            bd.update(Querys.DELETE_JORNADA_QUERY, idJornada);
            resultado = Either.right(idJornada);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(apiErrorGeneral());
        }
        return resultado;
    }

    //Update jornada
    public Either<ApiError, Jornada> updateJornada(Jornada jornada, LocalDate nuevaFecha) {
        Either<ApiError, Jornada> resultado;
        try {
            JdbcTemplate bd = new JdbcTemplate(dbConnection.getDataSource());
            bd.update(Querys.UPDATE_JORNADA_QUERY, Date.valueOf(nuevaFecha), jornada.getIdJornada());
            jornada.setFechaJornada(nuevaFecha);
            resultado = Either.right(jornada);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(apiErrorGeneral());
        }
        return resultado;
    }

    //Para pasarlos a jornada común:
    private Jornada pasarAJornada(JornadaEntity jornadaEntity) {
        return Jornada.builder()
                .idJornada(jornadaEntity.getIdJornada())
                .fechaJornada(jornadaEntity.getFechaJornada()
                        .toLocalDate())
                .build();
    }

    private ApiError apiErrorGeneral() {
        return ApiError.builder()
                .mensaje(Constantes.FALLO_GENERAL)
                .fecha(LocalDate.now())
                .build();
    }
}
