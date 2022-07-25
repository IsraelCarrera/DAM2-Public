package org.example.serverbasket.servicios;

import com.nimbusds.jose.util.X509CertUtils;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import org.example.common.modelo.ApiError;
import org.example.common.modelo.User;
import org.example.common.modelo.UsuarioCrear;
import org.example.common.modelo.UsuarioLoggear;
import org.example.serverbasket.config.Configuration;
import org.example.serverbasket.dao.DaoUsuarios;
import org.example.serverbasket.ee.security.GetClavesDoCertf;
import org.example.serverbasket.ee.utils.Constantes;

import java.security.*;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;

@Log4j2
public class ServiciosUsuarios {
    public final DaoUsuarios dao;
    public final Configuration config;
    public final GetClavesDoCertf clavesCertf;

    @Inject
    public ServiciosUsuarios(DaoUsuarios dao, Configuration config, GetClavesDoCertf clavesCertf) {
        this.dao = dao;
        this.config = config;
        this.clavesCertf = clavesCertf;
    }

    public Either<ApiError, UsuarioLoggear> addUser(UsuarioCrear usuarioCrear) {
        Either<ApiError, UsuarioLoggear> resultado;
        //Una vez tenemos aquí al usuario, vamos a pasar la public key, que está en String, a public key.
        Either<String, PublicKey> eitherGetPublicKey = clavesCertf.cogerPKDeString(usuarioCrear.getPublicKeyBase64());
        if (eitherGetPublicKey.isRight()) {
            PublicKey publicKey = eitherGetPublicKey.get();
            //Con la publica, vamos a generar el certificado, firmado por el servidor a nombre del usuario.
            Either<String, X509Certificate> eitherCertificado = clavesCertf.hacerCertificado(usuarioCrear.getNombre(), publicKey);
            if (eitherCertificado.isRight()) {
                //Hecho el certificado, lo pasamos a Base64 y, por un lado lo guardamos, y posteriormente para el usuario.
                try {
                    String certificadoEnBase64 = new String(Base64.getUrlEncoder().encode(eitherCertificado.get().getEncoded()));
                    //Guardamos y mandamos el either de allí.
                    resultado = dao.addUser(UsuarioLoggear.builder().nombre(usuarioCrear.getNombre()).certificadoBase64(certificadoEnBase64).build());
                } catch (CertificateEncodingException e) {
                    log.error(e.getMessage(), e);
                    resultado = Either.left(ApiError.builder().mensaje(Constantes.NO_SE_HA_PODIDO_ENCODEAR_EL_CERTIFICADO).fecha(LocalDate.now()).build());
                }
            } else {
                resultado = Either.left(ApiError.builder().mensaje(eitherCertificado.getLeft()).fecha(LocalDate.now()).build());
            }
        } else {
            resultado = Either.left(ApiError.builder().mensaje(eitherGetPublicKey.getLeft()).fecha(LocalDate.now()).build());
        }
        return resultado;
    }


    public Either<ApiError, User> logginUser(UsuarioLoggear usuarioLoggear) {
        Either<ApiError, User> resultado;
        //Primero, paso el string en certificado.
        X509Certificate cert = X509CertUtils.parse(Base64.getUrlDecoder().decode(usuarioLoggear.getCertificadoBase64()));
        if (cert.getIssuerX500Principal().getName().equals(Constantes.CN + Constantes.PROYECTO_SECRETOS)) {
            resultado = Either.right(User.builder().nombre(usuarioLoggear.getNombre()).build());
        } else {
            resultado = Either.left(ApiError.builder().mensaje(Constantes.EL_CERTIFICADO_NO_ESTA_FIRMADO_POR_ESTE_SERVIDOR).fecha(LocalDate.now()).build());
        }
        return resultado;
    }

    public Either<ApiError, List<User>> getAllUsers() {
        return dao.getAllUsers();
    }
}
