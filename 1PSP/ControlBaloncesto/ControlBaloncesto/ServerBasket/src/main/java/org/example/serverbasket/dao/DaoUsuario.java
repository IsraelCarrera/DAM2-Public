package org.example.serverbasket.dao;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import org.example.common.error.ApiError;
import org.example.common.modelo.Usuario;
import org.example.common.modelo.UsuarioLogging;
import org.example.serverbasket.dao.modelo.UsuarioEntity;
import org.example.serverbasket.dao.utils.Constantes;
import org.example.serverbasket.utils.GenerarCodigoRandom;
import org.example.serverbasket.utils.HashPass;
import org.example.serverbasket.utils.Querys;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;


@Log4j2
public class DaoUsuario {

    private final DBConnPool dbConnection;
    private final HashPass hash;


    @Inject
    public DaoUsuario(DBConnPool dbConnection, HashPass hash) {
        this.dbConnection = dbConnection;
        this.hash = hash;
    }

    public Either<ApiError, Usuario> addUser(Usuario user) {
        Either<ApiError, Usuario> resultado;
        //Aquí le iré metiendo tanto el hash de la contraseña, como el cod de activación, que no está activo, y el cod de cambio de pass.
        //Usuario siempre por defecto normal. Para ser admin, tiene que hacerle el update un admin si o si.
        UsuarioEntity u = UsuarioEntity.builder()
                .idUser(user.getIdUser())
                .nombreUser(user.getNombreUser().toLowerCase())
                .idTipoUsuario(2)
                .correoElectronico(user.getCorreoElectronico().toLowerCase())
                .passHash(hash.hashearPass(user.getPass()))
                .fechaLimite(LocalDateTime.now().plusHours(3))
                .codActivacion(GenerarCodigoRandom.randomBytes())
                .build();
        try {
            KeyHolder kh = new GeneratedKeyHolder();
            JdbcTemplate bd = new JdbcTemplate(dbConnection.getDataSource());
            bd.update(conn -> {
                PreparedStatement ps = conn
                        .prepareStatement(Querys.INSERT_USER_QUERY, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, u.getNombreUser());
                ps.setString(2, u.getPassHash());
                ps.setString(3, u.getCodActivacion());
                ps.setBoolean(4, u.isEstaActivo());
                ps.setTimestamp(5, Timestamp.valueOf(u.getFechaLimite()));
                ps.setInt(6, u.getIdTipoUsuario());
                ps.setString(7, u.getCodRestPass());
                ps.setString(8, u.getCorreoElectronico());
                return ps;
            }, kh);
            user.setIdUser(kh.getKey().intValue());
            user.setCodigoActivacion(u.getCodActivacion());
            resultado = Either.right(user);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(apiErrorGeneral());
        }
        return resultado;
    }

    public Either<ApiError, UsuarioEntity> getUserByCodActivacion(String codigo) {
        Either<ApiError, UsuarioEntity> resultado;
        UsuarioEntity user = null;
        try {
            JdbcTemplate bd = new JdbcTemplate(dbConnection.getDataSource());
            //Pondré el codigo a null para que no pueda volver a acceder a la activación una vez esté activo.
            List<UsuarioEntity> basura = bd.query(Querys.USUARIO_POR_CODACTIVACION_QUERY, BeanPropertyRowMapper.newInstance(UsuarioEntity.class), codigo);
            if (!basura.isEmpty()) {
                user = basura.get(0);
            }
            resultado = Either.right(user);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(apiErrorGeneral());
        }
        return resultado;
    }

    public Either<ApiError, Boolean> activarCuenta(int id) {
        Either<ApiError, Boolean> resultado;
        try {
            JdbcTemplate bd = new JdbcTemplate(dbConnection.getDataSource());
            bd.update(Querys.UPDATE_USER_ACTIVADO, id);
            resultado = Either.right(true);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(apiErrorGeneral());
        }
        return resultado;
    }


    public Either<ApiError, String> mandarCodigoParaHacerAdmin(int id) {
        Either<ApiError, String> resultado;
        if (checkearSiEstaActivo(id)) {
            try {
                //Updateamos solamente el codigo para ser admin
                String codigo = GenerarCodigoRandom.randomBytes();
                JdbcTemplate bd = new JdbcTemplate(dbConnection.getDataSource());
                //Aunque sno es la clase de UserEntity, como solo hago el update del código, cojo ese String.
                bd.update(Querys.UPDATE_CODIGO_PARA_ADMINISTRADOR, codigo, id);
                resultado = Either.right(codigo);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                resultado = Either.left(apiErrorGeneral());
            }
        } else {
            resultado = Either.left(ApiError.builder()
                    .mensaje(Constantes.EL_USUARIO_NO_ESTA_ACTIVO_TODAVIA)
                    .fecha(LocalDate.now())
                    .build());
        }
        return resultado;

    }


    public boolean hacerAdmin(String codSerAdmin) {
        boolean hecho = false;
        TransactionDefinition txDef = new DefaultTransactionDefinition();
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dbConnection.getDataSource());
        TransactionStatus txStatus = transactionManager.getTransaction(txDef);
        try {
            JdbcTemplate bd = new JdbcTemplate(transactionManager.getDataSource());
            UsuarioEntity user = bd.query(Querys.CHECK_CODIGO_ADMIN, BeanPropertyRowMapper.newInstance(UsuarioEntity.class), codSerAdmin).get(0);
            if (user != null) {
                bd.update(Querys.UPDATE_A_ADMINISTRADOR, codSerAdmin);
                hecho = true;
            }
            transactionManager.commit(txStatus);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            transactionManager.rollback(txStatus);
        }
        return hecho;
    }

    //Creo que, ya que solo necesito el email y el codigo, no voy a mandar to-do el usuario, simplemente una lista de 2 string. Donde 0 es el código, y el 1 el correo.
    public Either<ApiError, List<String>> escribirCodCambioPass(String nombreUser) {
        Either<ApiError, List<String>> resultado;
        TransactionDefinition txDef = new DefaultTransactionDefinition();
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dbConnection.getDataSource());
        TransactionStatus txStatus = transactionManager.getTransaction(txDef);
        try {
            JdbcTemplate bd = new JdbcTemplate(transactionManager.getDataSource());
            String codigo = GenerarCodigoRandom.randomBytes();
            bd.update(Querys.UPDATE_CODIGO_CAMBIO_PASS, codigo, nombreUser.toLowerCase());
            String email = bd.queryForObject(Querys.SELECT_EMAIL_USUARIO, String.class, nombreUser.toLowerCase());
            transactionManager.commit(txStatus);
            resultado = Either.right(Arrays.asList(codigo, email));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            transactionManager.rollback(txStatus);
            resultado = Either.left(apiErrorGeneral());
        }
        return resultado;
    }

    public boolean comprobarCodCambiarPass(String codCambiarPass) {
        boolean estaActivo = false;
        try {
            JdbcTemplate bd = new JdbcTemplate(dbConnection.getDataSource());
            int n = bd.queryForObject(Querys.CHECK_CODIGO_CAMBIO_PASS, Integer.class, codCambiarPass);
            if (n == 1) {
                estaActivo = true;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return estaActivo;
    }

    public boolean cambiarPass(String codCambioPass, String passVieja, String nuevaPass) {
        boolean seHaCambiado = false;
        TransactionDefinition txDef = new DefaultTransactionDefinition();
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dbConnection.getDataSource());
        TransactionStatus txStatus = transactionManager.getTransaction(txDef);
        try {
            JdbcTemplate bd = new JdbcTemplate(transactionManager.getDataSource());
            //Primero localizamos al usuario con el Cod de cambio de pass.
            UsuarioEntity usuarioEntity = bd.query(Querys.COGER_USER_POR_CODIGO_PASS, BeanPropertyRowMapper.newInstance(UsuarioEntity.class), codCambioPass).get(0);
            //Segundo comprobamos si la contraseña vieja coincide con la que tenemos guardada
            if (usuarioEntity != null) {
                if (hash.comprobarPass(passVieja, usuarioEntity.getPassHash())) {
                    //Por ultimo, cambiamos la contraseña con su hasheo, y borramos el código de cambio de pass.
                    String nuevaPassHasheada = hash.hashearPass(nuevaPass);
                    bd.update(Querys.UPDATE_CAMBIAR_PASS, nuevaPassHasheada, codCambioPass);
                    seHaCambiado = true;
                }
            }
            transactionManager.commit(txStatus);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            transactionManager.rollback(txStatus);
        }
        return seHaCambiado;
    }

    //Como en el anterior, un array donde el 0 es el código, y el 1, es el correo.
    public Either<ApiError, List<String>> solicitarVolverActivarCuenta(String codigoActivacionViejo) {
        Either<ApiError, List<String>> resultado;
        TransactionDefinition txDef = new DefaultTransactionDefinition();
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dbConnection.getDataSource());
        TransactionStatus txStatus = transactionManager.getTransaction(txDef);
        try {
            LocalDateTime fecha = LocalDateTime.now().plusHours(3);
            String nuevoCodigoActivacion = GenerarCodigoRandom.randomBytes();
            JdbcTemplate bd = new JdbcTemplate(transactionManager.getDataSource());
            String email = bd.queryForObject(Querys.SELECT_EMAIL_POR_CODIGOACTIVACION, String.class, codigoActivacionViejo);
            bd.update(Querys.UPDATE_REINICIAR_TIEMPO_ACTIVACION_CUENTA, nuevoCodigoActivacion, Timestamp.valueOf(fecha), codigoActivacionViejo);
            transactionManager.commit(txStatus);
            resultado = Either.right(Arrays.asList(nuevoCodigoActivacion, email));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            transactionManager.rollback(txStatus);
            resultado = Either.left(apiErrorGeneral());
        }
        return resultado;

    }

    public Either<ApiError, Usuario> hacerLogging(UsuarioLogging u) {
        Either<ApiError, Usuario> resultado;
        //Primero comprobemos si el usuario está activado, mirando en su nombre de usuario de usuario si está activado. Entonces me traigo to/do el usuarioentity y voy comparando.
        try {
            JdbcTemplate bd = new JdbcTemplate(dbConnection.getDataSource());
            UsuarioEntity usuarioEntity = bd.query(Querys.SELECT_USUARIO_IDUSER, BeanPropertyRowMapper.newInstance(UsuarioEntity.class), u.getNombreUser().toLowerCase()).get(0);
            if (usuarioEntity.isEstaActivo()) {
                //Una vez comprobado que el usuario está activo, comprobamos la pass. Si se valida correctamente, se manda el usuario, sino, pos un ApiError.
                if (hash.comprobarPass(u.getPass(), usuarioEntity.getPassHash())) {
                    resultado = Either.right(construirUsuarioNormal(usuarioEntity));
                } else {
                    resultado = Either.left(ApiError.builder()
                            .mensaje(Constantes.USUARIO_YO_CONTRASENIA_NO_VALIDA)
                            .fecha(LocalDate.now())
                            .build());
                }
            } else {
                resultado = Either.left(ApiError.builder()
                        .mensaje(Constantes.EL_USUARIO_NO_ESTA_ACTIVO_AUN)
                        .fecha(LocalDate.now())
                        .build());
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(ApiError.builder()
                    .mensaje(Constantes.USUARIO_YO_CONTRASENIA_NO_VALIDA)
                    .fecha(LocalDate.now())
                    .build());
        }

        return resultado;
    }

    //Pillar todos los usuarios. Para que el admin haga admin a otro, lo elija.
    public Either<ApiError, List<Usuario>> getUsers() {
        Either<ApiError, List<Usuario>> resultado;
        try {
            JdbcTemplate bd = new JdbcTemplate(dbConnection.getDataSource());
            resultado = Either.right(bd.query(Querys.SELECT_ALL_USERS, BeanPropertyRowMapper.newInstance(UsuarioEntity.class))
                    .stream()
                    .map(this::construirUsuarioNormal)
                    .collect(Collectors.toList()));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(apiErrorGeneral());
        }
        return resultado;
    }

    //Borrar usuario. Podrá borrar la cuenta uno mismo, o un admin podrá borrar también cuentas que sean normales.
    public Either<ApiError, Integer> deleteUser(int idUser) {
        Either<ApiError, Integer> resultado;
        try {
            JdbcTemplate bd = new JdbcTemplate(dbConnection.getDataSource());
            bd.update(Querys.DELETE_USER_QUERY, idUser);
            resultado = Either.right(idUser);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(apiErrorGeneral());
        }
        return resultado;
    }

    private boolean checkearSiEstaActivo(int id) {
        boolean estaActivo = false;
        try {
            JdbcTemplate bd = new JdbcTemplate(dbConnection.getDataSource());
            UsuarioEntity u = bd.query(Querys.CHECK_SI_ESTA_ACTIVO_QUERY, BeanPropertyRowMapper.newInstance(UsuarioEntity.class), id).get(0);
            estaActivo = u.isEstaActivo();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return estaActivo;
    }

    private Usuario construirUsuarioNormal(UsuarioEntity u) {
        return Usuario.builder()
                .nombreUser(u.getNombreUser())
                .idTipoUsuario(u.getIdTipoUsuario())
                .estaActivo(u.isEstaActivo())
                .pass(null)
                .correoElectronico(u.getCorreoElectronico())
                .idUser(u.getIdUser())
                .codigoActivacion(u.getCodActivacion())
                .codigoSerAdmin(u.getCodigoSerAdmin())
                .build();
    }

    private ApiError apiErrorGeneral() {
        return ApiError.builder()
                .mensaje(Constantes.FALLO_GENERAL)
                .fecha(LocalDate.now())
                .build();
    }

}
