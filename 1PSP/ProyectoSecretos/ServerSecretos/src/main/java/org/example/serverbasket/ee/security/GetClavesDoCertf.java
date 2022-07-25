package org.example.serverbasket.ee.security;

import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import org.bouncycastle.jce.X509Principal;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.x509.X509V3CertificateGenerator;
import org.example.serverbasket.config.Configuration;
import org.example.serverbasket.ee.utils.Constantes;

import jakarta.inject.Inject;

import java.io.IOException;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.X509Certificate;
import java.security.spec.X509EncodedKeySpec;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;

@Log4j2
public class GetClavesDoCertf {
    private final Configuration config;

    @Inject
    public GetClavesDoCertf(Configuration config) {
        this.config = config;
    }

    public Either<String, PublicKey> cogerPKDeString(String publicaBase64) {
        Either<String, PublicKey> resultado;
        try {
            byte[] bufferPub = Base64.getUrlDecoder().decode(publicaBase64);
            X509EncodedKeySpec clavePublicaSpec = new X509EncodedKeySpec(bufferPub);
            KeyFactory keyFactoryRSA = KeyFactory.getInstance(Constantes.RSA);
            PublicKey clavePublica = keyFactoryRSA.generatePublic(clavePublicaSpec);
            resultado = Either.right(clavePublica);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(Constantes.NO_SE_HA_PODIDO_DECODIFICAR_LA_CLAVE_PUBLICA);
        }
        return resultado;
    }

    public Either<String, PublicKey> getPublicKey() {
        Either<String, PublicKey> resultado;
        //Para leer la privada:
        try {
            KeyStore ksLoad = KeyStore.getInstance(Constantes.PKCS_12);
            char[] password = config.getPassKeyStore().toCharArray();
            ksLoad.load(this.getClass().getClassLoader().getResourceAsStream(config.getNombreKeyStore() + Constantes.KEYSTORE_PFX), password);
            X509Certificate certLoad = (X509Certificate) ksLoad.getCertificate(Constantes.PUBLICA);
            PublicKey publicKey = certLoad.getPublicKey();
            resultado = Either.right(publicKey);
        } catch (NoSuchAlgorithmException | IOException e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(Constantes.NO_HA_SIDO_POSIBLE_RECOGER_LA_CLAVE_PRIVADA_INTENTELO_MAS_TARDE);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(Constantes.FALLO_INTERNO);
        }
        return resultado;
    }

    public Either<String, PrivateKey> getPrivateKey() {
        Either<String, PrivateKey> resultado;
        //Para leer la privada:
        try {
            KeyStore ksLoad = KeyStore.getInstance(Constantes.PKCS_12);
            char[] password = config.getPassKeyStore().toCharArray();
            ksLoad.load(this.getClass().getClassLoader().getResourceAsStream(config.getNombreKeyStore() + Constantes.KEYSTORE_PFX), password);
            KeyStore.PasswordProtection pt = new KeyStore.PasswordProtection(password);
            KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) ksLoad.getEntry(Constantes.PRIVADA, pt);
            PrivateKey privateKey = privateKeyEntry.getPrivateKey();
            resultado = Either.right(privateKey);
        } catch (NoSuchAlgorithmException | IOException e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(Constantes.NO_HA_SIDO_POSIBLE_RECOGER_LA_CLAVE_PRIVADA_INTENTELO_MAS_TARDE);
        } catch (UnrecoverableEntryException e) {
            log.error(e.getMessage(), e);
            //Es porque la pass es incorrecta.
            resultado = Either.left(Constantes.USUARIO_Y_O_CONTRASENA_NO_VALIDO);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(Constantes.FALLO_INTERNO);
        }

        return resultado;
    }

    public Either<String, X509Certificate> hacerCertificado(String nombreUser, PublicKey publicKey) {
        Either<String, X509Certificate> resultado;
        Either<String, PrivateKey> eitherPrivateKey = getPrivateKey();
        if (eitherPrivateKey.isRight()) {
            try {
                Security.addProvider(new BouncyCastleProvider());
                X509V3CertificateGenerator cert1 = new X509V3CertificateGenerator();
                cert1.setSerialNumber(BigInteger.valueOf(Constantes.UNO));   //or generate a random number
                cert1.setSubjectDN(new X509Principal(Constantes.CN + nombreUser));  //Aqui se pone el nombre del usuario.
                cert1.setIssuerDN(new X509Principal(Constantes.CN + Constantes.PROYECTO_SECRETOS)); //El emisor que es el server.
                cert1.setPublicKey(publicKey);
                cert1.setNotBefore(
                        Date.from(LocalDate.now().plus(Constantes.AMOUNT_TO_ADD, ChronoUnit.DAYS).atStartOfDay().toInstant(ZoneOffset.UTC)));
                cert1.setNotAfter(new Date());
                cert1.setSignatureAlgorithm(Constantes.SHA_1_WITH_RSA_ENCRYPTION);
                //Aquí se firma el certificado con la privada del servidor.
                PrivateKey signingKey = eitherPrivateKey.get();
                X509Certificate cert = cert1.generateX509Certificate(signingKey);
                resultado = Either.right(cert);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                resultado = Either.left(Constantes.FALLO_INTERNO);
            }
        } else {
            resultado = Either.left(Constantes.NO_SE_HA_PODIDO_ACCEDER_A_LA_PRIVATE_KEY_DEL_SERVIDOR);
        }

        return resultado;
    }

}
