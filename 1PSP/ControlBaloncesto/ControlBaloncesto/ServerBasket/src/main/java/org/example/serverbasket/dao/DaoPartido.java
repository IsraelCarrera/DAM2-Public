package org.example.serverbasket.dao;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import org.example.common.error.ApiError;
import org.example.common.modelo.Partido;
import org.example.serverbasket.dao.modelo.PartidoEntity;
import org.example.serverbasket.dao.utils.Constantes;
import org.example.serverbasket.utils.Querys;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public class DaoPartido {
    private final DBConnPool dbConnection;

    @Inject
    public DaoPartido(DBConnPool dbConnection) {
        this.dbConnection = dbConnection;
    }

    //Pillar todos los partidos.
    public Either<ApiError, List<Partido>> getAllPartidos() {
        Either<ApiError, List<Partido>> resultado;
        TransactionDefinition txDef = new DefaultTransactionDefinition();
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dbConnection.getDataSource());
        TransactionStatus txStatus = transactionManager.getTransaction(txDef);
        try {
            JdbcTemplate bd = new JdbcTemplate(transactionManager.getDataSource());
            List<PartidoEntity> partidoEntities = bd.query(Querys.SELECT_ALL_PARTIDOS, BeanPropertyRowMapper.newInstance(PartidoEntity.class));
            resultado = Either.right(partidoEntities.stream()
                    .map(partidoEntity -> {
                        String equipoLocal = bd.queryForObject(Querys.SELECT_NOMBRE_EQUIPO, String.class, partidoEntity.getIdEquipoLocal());
                        String equipoVisitante = bd.queryForObject(Querys.SELECT_NOMBRE_EQUIPO, String.class, partidoEntity.getIdEquipoVisitante());
                        return this.pasarAPartido(partidoEntity, equipoLocal, equipoVisitante);
                    })
                    .collect(Collectors.toList()));
            transactionManager.commit(txStatus);
        } catch (NumberFormatException e) {
            transactionManager.rollback(txStatus);
            log.error(e.getMessage(), e);
            resultado = Either.left(ApiError.builder()
                    .mensaje(Constantes.FALLO_AL_PASAR_LOS_RESULTADOS_CONTACTE_CON_EL_GESTOR_DE_LA_BBDD)
                    .fecha(LocalDate.now())
                    .build());
        } catch (Exception e) {
            transactionManager.rollback(txStatus);
            log.error(e.getMessage(), e);
            resultado = Either.left(apiErrorGeneral());
        }
        return resultado;
    }

    //Pillar partido por jornada
    public Either<ApiError, List<Partido>> getPartidosPorJornada(int idJornada) {
        Either<ApiError, List<Partido>> resultado;
        TransactionDefinition txDef = new DefaultTransactionDefinition();
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dbConnection.getDataSource());
        TransactionStatus txStatus = transactionManager.getTransaction(txDef);
        try {
            JdbcTemplate bd = new JdbcTemplate(transactionManager.getDataSource());
            transactionManager.commit(txStatus);
            List<PartidoEntity> partidoEntities = bd.query(Querys.SELECT_PARTIDO_JORNADA, BeanPropertyRowMapper.newInstance(PartidoEntity.class), idJornada);
            resultado = Either.right(partidoEntities.stream()
                    .map(partidoEntity -> {
                        String equipoLocal = bd.queryForObject(Querys.SELECT_NOMBRE_EQUIPO, String.class, partidoEntity.getIdEquipoLocal());
                        String equipoVisitante = bd.queryForObject(Querys.SELECT_NOMBRE_EQUIPO, String.class, partidoEntity.getIdEquipoVisitante());
                        return this.pasarAPartido(partidoEntity, equipoLocal, equipoVisitante);
                    })
                    .collect(Collectors.toList()));
        } catch (NumberFormatException e) {
            transactionManager.rollback(txStatus);
            log.error(e.getMessage(), e);
            resultado = Either.left(ApiError.builder()
                    .mensaje(Constantes.FALLO_AL_PASAR_LOS_RESULTADOS_CONTACTE_CON_EL_GESTOR_DE_LA_BBDD)
                    .fecha(LocalDate.now())
                    .build());
        } catch (Exception e) {
            transactionManager.rollback(txStatus);
            log.error(e.getMessage(), e);
            resultado = Either.left(apiErrorGeneral());
        }
        return resultado;
    }

    //pillar partido por equipo
    public Either<ApiError, List<Partido>> getPartidosPorEquipo(String nombreEquipo) {
        Either<ApiError, List<Partido>> resultado;
        TransactionDefinition txDef = new DefaultTransactionDefinition();
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dbConnection.getDataSource());
        TransactionStatus txStatus = transactionManager.getTransaction(txDef);
        try {
            JdbcTemplate bd = new JdbcTemplate(transactionManager.getDataSource());
            transactionManager.commit(txStatus);
            List<PartidoEntity> partidoEntities = bd.query(Querys.SELECT_PARTIDOS_EQUIPO, BeanPropertyRowMapper.newInstance(PartidoEntity.class), nombreEquipo, nombreEquipo);
            resultado = Either.right(partidoEntities.stream()
                    .map(partidoEntity -> {
                        String equipoLocal = bd.queryForObject(Querys.SELECT_NOMBRE_EQUIPO, String.class, partidoEntity.getIdEquipoLocal());
                        String equipoVisitante = bd.queryForObject(Querys.SELECT_NOMBRE_EQUIPO, String.class, partidoEntity.getIdEquipoVisitante());
                        return this.pasarAPartido(partidoEntity, equipoLocal, equipoVisitante);
                    })
                    .collect(Collectors.toList()));
        } catch (NumberFormatException e) {
            transactionManager.rollback(txStatus);
            log.error(e.getMessage(), e);
            resultado = Either.left(ApiError.builder()
                    .mensaje(Constantes.FALLO_AL_PASAR_LOS_RESULTADOS_CONTACTE_CON_EL_GESTOR_DE_LA_BBDD)
                    .fecha(LocalDate.now())
                    .build());
        } catch (Exception e) {
            transactionManager.rollback(txStatus);
            log.error(e.getMessage(), e);
            resultado = Either.left(apiErrorGeneral());
        }
        return resultado;
    }

    //Añadir un partido
    public Either<ApiError, Partido> addPartido(Partido partido) {
        Either<ApiError, Partido> resultado;
        TransactionDefinition txDef = new DefaultTransactionDefinition();
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dbConnection.getDataSource());
        TransactionStatus txStatus = transactionManager.getTransaction(txDef);
        try {
            JdbcTemplate bd = new JdbcTemplate(transactionManager.getDataSource());
            int idEquipoLocal = bd.queryForObject(Querys.SELECT_ID_EQUIPO, Integer.class, partido.getNombreEquipoLocal());
            int idEquipoVisitante = bd.queryForObject(Querys.SELECT_ID_EQUIPO, Integer.class, partido.getNombreEquipoVisitante());
            PartidoEntity partidoEntity = PartidoEntity.builder()
                    .idJornada(partido.getIdJornada())
                    .idEquipoLocal(idEquipoLocal)
                    .idEquipoVisitante(idEquipoVisitante)
                    .resultado(partido.getPuntosEquipoLocal() + Constantes.GUION + partido.getPuntosEquipoVisitante())
                    .build();
            bd.update(Querys.INSERT_PARTIDO_QUERY, partidoEntity.getIdJornada(), partidoEntity.getIdEquipoLocal(), partidoEntity.getIdEquipoVisitante(), partidoEntity.getResultado());
            transactionManager.commit(txStatus);
            resultado = Either.right(partido);
        } catch (EmptyResultDataAccessException e) {
            transactionManager.rollback(txStatus);
            log.error(e.getMessage(), e);
            //Modificar el mensaje del ApiError
            ApiError apiError = new ApiError(Constantes.NO_HAY_EQUIPOS_CON_ESOS_NOMBRES_REGISTRADOS_REGISTRA_PRIMERO_DICHOS_EQUIPOS, LocalDate.now());
            resultado = Either.left(apiError);
        } catch (DataIntegrityViolationException e) {
            transactionManager.rollback(txStatus);
            log.error(e.getMessage(), e);
            //Modificar el mensaje del ApiError
            ApiError apiError = new ApiError(Constantes.LA_JORNADA_NO_EXISTE_COMPRUEBELO_CORRECTAMENTE, LocalDate.now());
            resultado = Either.left(apiError);
        } catch (Exception e) {
            transactionManager.rollback(txStatus);
            log.error(e.getMessage(), e);
            resultado = Either.left(apiErrorGeneral());
        }

        return resultado;
    }

    //Eliminar un partido
    public Either<ApiError, Partido> deletePartido(Partido partido) {
        Either<ApiError, Partido> resultado;
        TransactionDefinition txDef = new DefaultTransactionDefinition();
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dbConnection.getDataSource());
        TransactionStatus txStatus = transactionManager.getTransaction(txDef);
        try {
            JdbcTemplate bd = new JdbcTemplate(transactionManager.getDataSource());
            int idEquipoLocal = bd.queryForObject(Querys.SELECT_ID_EQUIPO, Integer.class, partido.getNombreEquipoLocal());
            int idEquipoVisitante = bd.queryForObject(Querys.SELECT_ID_EQUIPO, Integer.class, partido.getNombreEquipoVisitante());
            bd.update(Querys.DELETE_PARTIDO_QUERY, partido.getIdJornada(), idEquipoLocal, idEquipoVisitante);
            transactionManager.commit(txStatus);
            resultado = Either.right(partido);
        } catch (EmptyResultDataAccessException e) {
            transactionManager.rollback(txStatus);
            log.error(e.getMessage(), e);
            //Modificar el mensaje del ApiError
            ApiError apiError = new ApiError(Constantes.NO_HAY_EQUIPOS_CON_ESOS_NOMBRES_REGISTRADOS_REGISTRA_PRIMERO_DICHOS_EQUIPOS, LocalDate.now());
            resultado = Either.left(apiError);
        } catch (DataIntegrityViolationException e) {
            transactionManager.rollback(txStatus);
            log.error(e.getMessage(), e);
            //Modificar el mensaje del ApiError
            ApiError apiError = new ApiError(Constantes.LA_JORNADA_NO_EXISTE_COMPRUEBELO_CORRECTAMENTE, LocalDate.now());
            resultado = Either.left(apiError);
        } catch (Exception e) {
            transactionManager.rollback(txStatus);
            log.error(e.getMessage(), e);
            resultado = Either.left(apiErrorGeneral());
        }
        return resultado;
    }

    //Update partido
    public Either<ApiError, Partido> updatePartido(Partido partido, String puntuacion) {
        Either<ApiError, Partido> resultado;
        TransactionDefinition txDef = new DefaultTransactionDefinition();
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dbConnection.getDataSource());
        TransactionStatus txStatus = transactionManager.getTransaction(txDef);
        try {
            JdbcTemplate bd = new JdbcTemplate(transactionManager.getDataSource());
            int idEquipoLocal = bd.queryForObject(Querys.SELECT_ID_EQUIPO, Integer.class, partido.getNombreEquipoLocal());
            int idEquipoVisitante = bd.queryForObject(Querys.SELECT_ID_EQUIPO, Integer.class, partido.getNombreEquipoVisitante());
            //Creo el partidoEntity para que SIEMPRE SIEMPRE tengan el mismo formato el resultado.
            bd.update(Querys.UPDATE_PARTIDO_QUERY, puntuacion, partido.getIdJornada(), idEquipoLocal, idEquipoVisitante);
            transactionManager.commit(txStatus);
            resultado = Either.right(partido);
        } catch (EmptyResultDataAccessException e) {
            transactionManager.rollback(txStatus);
            log.error(e.getMessage(), e);
            //Modificar el mensaje del ApiError
            ApiError apiError = new ApiError(Constantes.NO_HAY_EQUIPOS_CON_ESOS_NOMBRES_REGISTRADOS_REGISTRA_PRIMERO_DICHOS_EQUIPOS, LocalDate.now());
            resultado = Either.left(apiError);
        } catch (DataIntegrityViolationException e) {
            transactionManager.rollback(txStatus);
            log.error(e.getMessage(), e);
            //Modificar el mensaje del ApiError
            ApiError apiError = new ApiError(Constantes.LA_JORNADA_NO_EXISTE_COMPRUEBELO_CORRECTAMENTE, LocalDate.now());
            resultado = Either.left(apiError);
        } catch (Exception e) {
            transactionManager.rollback(txStatus);
            log.error(e.getMessage(), e);
            resultado = Either.left(apiErrorGeneral());
        }
        return resultado;
    }

    //Para pasarlos a partido común:
    private Partido pasarAPartido(PartidoEntity partidoEntity, String nombreEquipoLocal, String nombreEquipoVisitante) throws NumberFormatException {
        List<String> resultado = List.of(partidoEntity.getResultado().split(Constantes.GUION));
        //Por si falla, Lanzo el error y que lo recoja en el principal
        return Partido.builder()
                .idJornada(partidoEntity.getIdJornada())
                .nombreEquipoLocal(nombreEquipoLocal)
                .nombreEquipoVisitante(nombreEquipoVisitante)
                .puntosEquipoLocal(Integer.parseInt(resultado.get(0)))
                .puntosEquipoVisitante(Integer.parseInt(resultado.get(1)))
                .build();
    }

    private ApiError apiErrorGeneral() {
        return ApiError.builder()
                .mensaje(Constantes.FALLO_GENERAL)
                .fecha(LocalDate.now())
                .build();
    }
}
