package org.example.serverbasket.dao;

import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;

import jakarta.inject.Inject;
import org.example.common.modelo.ApiError;
import org.example.common.modelo.SecretosCommon;
import org.example.serverbasket.dao.utils.Constantes;
import org.example.serverbasket.dao.utils.Querys;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Log4j2
public class DaoSecretos {
    private final DBConnPool dbConnection;

    @Inject
    public DaoSecretos(DBConnPool dbConnection) {
        this.dbConnection = dbConnection;
    }

    public Either<ApiError, Integer> addSecreto(SecretosCommon secretosCommon) {
        Either<ApiError, Integer> resultado;
        try {
            KeyHolder kh = new GeneratedKeyHolder();
            JdbcTemplate bd = new JdbcTemplate(dbConnection.getDataSource());
            bd.update(conn -> {
                PreparedStatement ps = conn
                        .prepareStatement(Querys.INSERT_SECRETOS_QUERY, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, secretosCommon.getNombre());
                ps.setString(2, secretosCommon.getSecretoCifrado());
                ps.setString(3, secretosCommon.getFirma());
                return ps;
            }, kh);
            resultado = Either.right(Objects.requireNonNull(kh.getKey()).intValue());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(apiErrorGeneral());
        }
        return resultado;
    }

    public Either<ApiError, Boolean> addSecretoCompartido(SecretosCommon secretosCommon) {
        Either<ApiError, Boolean> resultado;
        try {
            JdbcTemplate bd = new JdbcTemplate(dbConnection.getDataSource());
            bd.update(Querys.INSERT_SECRETOSCOMPARTIDOS_QUERY, secretosCommon.getId(), secretosCommon.getNombre(), secretosCommon.getClaveCifradaPublica());
            resultado = Either.right(true);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(apiErrorGeneral());
        }
        return resultado;
    }

    public Either<ApiError, List<SecretosCommon>> getAllSecretosCompartidos(String userName) {
        Either<ApiError, List<SecretosCommon>> resultado;
        try {
            JdbcTemplate bd = new JdbcTemplate(dbConnection.getDataSource());
            List<SecretosCommon> secretos = bd.query(Querys.SELECT_SECRETOSALL,
                    new RowMapper<SecretosCommon>() {
                        @Override
                        public SecretosCommon mapRow(ResultSet rs, int i) throws SQLException {
                            return SecretosCommon.builder()
                                    .id(rs.getInt(Constantes.ID))
                                    .nombre(rs.getString(Constantes.USER_A_COMPARTIR))
                                    .firma(rs.getString(Constantes.FIRMA))
                                    .claveCifradaPublica(rs.getString(Constantes.CLAVE_CIFRADA_PUBLICA))
                                    .secretoCifrado(rs.getString(Constantes.SECRETO))
                                    .nombreDelDuenoSecreto(rs.getString(Constantes.S_NOMBRE_USER))
                                    .build();
                        }
                    }, userName);
            resultado = Either.right(secretos);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(apiErrorGeneral());
        }
        return resultado;
    }


    private ApiError apiErrorGeneral() {
        return ApiError.builder()
                .mensaje(Constantes.FALLO_GENERAL)
                .fecha(LocalDate.now())
                .build();
    }
}
