package org.example.encriptarasimetrico.dao;


import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import org.example.encriptarasimetrico.dao.utils.Constantes;
import org.example.encriptarasimetrico.dao.utils.Querys;
import org.example.encriptarasimetrico.modelo.Secretos;
import org.example.encriptarasimetrico.modelo.SecretosCompartidos;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;

@Log4j2
public class DaoSecretos {
    private final DBConnPool dbConnection;

    @Inject
    public DaoSecretos(DBConnPool dbConnection) {
        this.dbConnection = dbConnection;
    }

    public Either<String, Integer> addSecreto(String user, String secretoEncriptado) {
        Either<String, Integer> resultado;
        try {
            KeyHolder kh = new GeneratedKeyHolder();
            JdbcTemplate bd = new JdbcTemplate(dbConnection.getDataSource());
            bd.update(conn -> {
                PreparedStatement ps = conn
                        .prepareStatement(Querys.INSERT_SECRETOS_QUERY, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, user);
                ps.setString(2, secretoEncriptado);
                return ps;
            }, kh);
            resultado = Either.right(Objects.requireNonNull(kh.getKey()).intValue());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(Constantes.NO_SE_HA_ANADIDO_EL_SECRETO);
        }
        return resultado;
    }

    public Either<String, Boolean> addSecretoCompartido(int id, String userACompartir, String passEncriptada) {
        Either<String, Boolean> resultado;
        try {
            JdbcTemplate bd = new JdbcTemplate(dbConnection.getDataSource());
            bd.update(Querys.INSERT_SECRETOSCOMPARTIDOS_QUERY, id, userACompartir, passEncriptada);
            resultado = Either.right(true);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(Constantes.NO_SE_HA_AGREGADO_EL_SECRETO_EN_LA_TABLA_DE_SECRETOS_COMPARTIDOS);
        }
        return resultado;
    }

    //Corregir eh.
    public Either<String, Secretos> consultarSecretos(int id) {
        Either<String, Secretos> resultado;
        try {
            JdbcTemplate bd = new JdbcTemplate(dbConnection.getDataSource());
            List<Secretos> secretosList = bd.query(Querys.SELECT_SECRETOS, BeanPropertyRowMapper.newInstance(Secretos.class), id);
            //Si o sí debería darme un secreto.
            resultado = Either.right(secretosList.get(0));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(Constantes.NO_HAY_SECRETO_CON_ESA_ID);
        }
        return resultado;
    }

    public Either<String, List<SecretosCompartidos>> selectSecretosCompartidos(String user) {
        Either<String, List<SecretosCompartidos>> resultado;
        try {
            JdbcTemplate bd = new JdbcTemplate(dbConnection.getDataSource());
            List<SecretosCompartidos> secretosEntities = bd.query(Querys.SELECT_SECRETOSCOMPARTIDOS, BeanPropertyRowMapper.newInstance(SecretosCompartidos.class), user);
            resultado = Either.right(secretosEntities);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(Constantes.NO_SE_HAN_CONSEGUIDO_LOS_SECRETOS);
        }
        return resultado;
    }

    public Either<String, SecretosCompartidos> selectSecretosCompartidosPorId(int id) {
        Either<String, SecretosCompartidos> resultado;
        try {
            JdbcTemplate bd = new JdbcTemplate(dbConnection.getDataSource());
            List<SecretosCompartidos> secretosEntities = bd.query(Querys.SELECT_SECRETOSCOMPARTIDOS_PORID, BeanPropertyRowMapper.newInstance(SecretosCompartidos.class), id);
            if (!secretosEntities.isEmpty()) {
                resultado = Either.right(secretosEntities.get(0));
            } else {
                resultado = Either.left(Constantes.NO_HAY_SECRETOS_CON_DICHA_ID);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(Constantes.NO_SE_HAN_CONSEGUIDO_LOS_SECRETOS);
        }
        return resultado;
    }
}
