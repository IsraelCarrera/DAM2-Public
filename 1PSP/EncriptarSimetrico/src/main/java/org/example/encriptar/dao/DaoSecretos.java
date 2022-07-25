package org.example.encriptar.dao;


import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import org.example.encriptar.dao.model.SecretosEntity;
import org.example.encriptar.modelo.Secretos;
import org.example.encriptar.utils.Constantes;
import org.example.encriptar.utils.Encriptar;
import org.example.encriptar.utils.Querys;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.inject.Inject;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Log4j2
public class DaoSecretos {
    private final DBConnPool dbConnection;
    private final Encriptar encriptar;

    @Inject
    public DaoSecretos(DBConnPool dbConnection, Encriptar encriptar) {
        this.dbConnection = dbConnection;
        this.encriptar = encriptar;
    }

    public Either<String, Boolean> addSecreto(Secretos secretos) {
        Either<String, Boolean> resultado;
        try {
            String secretoEncriptado = encriptar.encriptando(secretos.getSecreto(), secretos.getPassUser());
            JdbcTemplate bd = new JdbcTemplate(dbConnection.getDataSource());
            bd.update(Querys.INSERT_SECRETOS_QUERY, secretos.getNombreUser(), secretoEncriptado);
            resultado = Either.right(true);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(Constantes.NO_SE_HA_ANADIDO_EL_SECRETO);
        }
        return resultado;
    }

    public Either<String, List<String>> consultarSecretos(String user, String pass) {
        Either<String, List<String>> resultado;
        try {
            JdbcTemplate bd = new JdbcTemplate(dbConnection.getDataSource());
            List<String> secretosEntities = bd.query(Querys.SELECT_SECRETOS, BeanPropertyRowMapper.newInstance(SecretosEntity.class), user)
                    .stream()
                    .map(SecretosEntity::getSecreto)
                    .collect(Collectors.toList());
            //ENTENDEREMOS QUE EL USUARIO SIEMPRE ESCRIBE LA MISMA PASS... SINO LE MANDAMOS UN BONITO NULL AL CONTROLLER.
            if (secretosEntities.isEmpty()) {
                resultado = Either.left(Constantes.NO_HAY_SECRETOS_PARA_DICHO_USUARIO);
            } else {
                List<String> secretosFinal = secretosEntities.stream().map(sec -> encriptar.desencriptando(sec, pass))
                        .filter(Objects::nonNull).collect(Collectors.toList());
                if (secretosFinal.isEmpty()) {
                    resultado = Either.left(Constantes.CONTRASENA_INCORRECTA);
                } else {
                    resultado = Either.right(secretosFinal);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(Constantes.NO_SE_HA_ANADIDO_EL_SECRETO);
        }
        return resultado;
    }
}
