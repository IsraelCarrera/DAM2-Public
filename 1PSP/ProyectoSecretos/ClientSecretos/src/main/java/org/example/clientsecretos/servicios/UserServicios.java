package org.example.clientsecretos.servicios;

import io.vavr.control.Either;
import org.example.clientsecretos.dao.DaoUser;
import org.example.clientsecretos.dao.utils.UserCacheado;
import org.example.common.modelo.User;
import org.example.clientsecretos.security.GenClavesAndStore;
import org.example.clientsecretos.servicios.Modelo.Claves;
import org.example.clientsecretos.servicios.utils.Constantes;
import org.example.common.modelo.UsuarioCrear;
import org.example.common.modelo.UsuarioLoggear;

import javax.inject.Inject;
import java.io.File;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserServicios {
    private final DaoUser dao;
    private final GenClavesAndStore genClavesAndStore;
    private final UserCacheado userCacheado;

    @Inject
    public UserServicios(DaoUser dao, GenClavesAndStore genClavesAndStore, UserCacheado userCacheado) {
        this.dao = dao;
        this.genClavesAndStore = genClavesAndStore;
        this.userCacheado = userCacheado;
    }

    public Either<String, Boolean> crearUser(User user) {
        Either<String, Boolean> resultado;
        //Primero generamos las publicas y las privadas.
        Either<String, Claves> eitherConClaves = genClavesAndStore.generarClaves();
        if (eitherConClaves.isRight()) {
            //Aquí le mandamos al servidor el nombre y la publica.
            String publicaEnBase64 = new String(Base64.getUrlEncoder().encode(eitherConClaves.get().getPublicKey().getEncoded()));
            Either<String, UsuarioLoggear> eitherAddUser = dao.addUser(UsuarioCrear.builder().nombre(user.getNombre()).publicKeyBase64(publicaEnBase64).build());
            if (eitherAddUser.isRight()) {
                resultado = genClavesAndStore.generarKeyStore(user, eitherAddUser.get().getCertificadoBase64(), eitherConClaves.get().getPrivateKey());
            } else {
                resultado = Either.left(eitherAddUser.getLeft());
            }
        } else {
            resultado = Either.left(eitherConClaves.getLeft());
        }
        return resultado;
    }

    public Either<String, User> hacerLoggin(User user) {
        Either<String, User> resultado;
        //Primero comprobamos que exista el usuario en este ordenador, para ello leemos y comprobamos que existe.
        if (checkSiExisteUser(user.getNombre())) {
            //Ahora cogemos el certificado
            Either<String, String> eitherGetCertificado = genClavesAndStore.getCertificadoLoggin(user.getNombre(), user.getPass());
            if (eitherGetCertificado.isRight()) {
                //Cacheamos al usuario, ya que tenemos todos los datos que queremos.
                userCacheado.setNombre(user.getNombre());
                userCacheado.setPass(user.getPass());
                userCacheado.setCertificadoBase64(eitherGetCertificado.get());
                //Con el certificado, voy al servidor a comprobar que está firmado por el servidor.
                Either<String, User> eitherLoggin = dao.hacerLoggin(UsuarioLoggear.builder().nombre(user.getNombre()).certificadoBase64(eitherGetCertificado.get()).build());
                if (eitherLoggin.isRight()) {
                    resultado = Either.right(User.builder().nombre(userCacheado.getNombre()).pass(userCacheado.getPass()).token(userCacheado.getToken()).build());
                } else {
                    resultado = Either.left(eitherLoggin.getLeft());
                }

            } else {
                resultado = Either.left(eitherGetCertificado.getLeft());
            }
        } else {
            //No existe el usuario
            resultado = Either.left(Constantes.NO_EXISTE_DICHO_USUARIO_Y_O_CONTRASENA);
        }
        return resultado;
    }

    public Either<String, List<User>> getAllUsers() {
        return dao.getAllUsers();
    }


    private Either<String, List<String>> comprobarUsuarios() {
        Either<String, List<String>> resultado;
        try {
            File folder = new File(Constantes.KEYSTORE);
            List<String> listaUser = Arrays.stream(Objects.requireNonNull(folder.listFiles()))
                    .sequential()
                    .filter(f -> !f.isDirectory())
                    .map(File::getName)
                    .map(p -> {
                        String[] s = p.split(Constantes.REGEX);
                        return s[0];
                    })
                    .collect(Collectors.toList());
            resultado = Either.right(listaUser);
        } catch (Exception e) {
            resultado = Either.left(Constantes.FALLO_AL_ACCEDER_A_LA_LISTA_DE_USUARIOS);
        }
        return resultado;
    }


    private boolean checkSiExisteUser(String nombre) {
        boolean exist = false;
        Either<String, List<String>> eitherListUser = comprobarUsuarios();
        if (eitherListUser.isRight()) {
            exist = eitherListUser.get().stream().anyMatch(u -> u.equals(nombre));
        }
        return exist;
    }
}
