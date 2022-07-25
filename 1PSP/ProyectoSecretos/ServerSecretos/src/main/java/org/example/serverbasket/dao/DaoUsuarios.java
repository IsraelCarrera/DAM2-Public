package org.example.serverbasket.dao;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import org.example.common.modelo.ApiError;
import org.example.common.modelo.User;
import org.example.common.modelo.UsuarioLoggear;
import org.example.serverbasket.dao.modelo.UserEntity;
import org.example.serverbasket.dao.utils.Constantes;
import org.example.serverbasket.dao.utils.Querys;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public class DaoUsuarios {
    private final DBConnPool dbConnection;

    @Inject
    public DaoUsuarios(DBConnPool dbConnection) {
        this.dbConnection = dbConnection;
    }

    //Añadiendo user
    public Either<ApiError, UsuarioLoggear> addUser(UsuarioLoggear usuarioLoggear) {
        Either<ApiError, UsuarioLoggear> resultado;
        try {
            JdbcTemplate bd = new JdbcTemplate(dbConnection.getDataSource());
            bd.update(Querys.INSERT_USER, usuarioLoggear.getNombre(), usuarioLoggear.getCertificadoBase64());
            resultado = Either.right(usuarioLoggear);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(apiErrorGeneral());
        }
        return resultado;
    }

    public Either<ApiError, List<User>> getAllUsers() {
        Either<ApiError, List<User>> resultado;
        try {
            JdbcTemplate bd = new JdbcTemplate(dbConnection.getDataSource());
            resultado = Either.right(bd.query(Querys.SELECT_ALL_USERS, BeanPropertyRowMapper.newInstance(UserEntity.class))
                    .stream()
                    .map(this::pasarAUser)
                    .collect(Collectors.toList()));
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

    private User pasarAUser(UserEntity user) {
        return User.builder().nombre(user.getNombre()).certificadoBase64(user.getCertificado()).build();
    }
}
