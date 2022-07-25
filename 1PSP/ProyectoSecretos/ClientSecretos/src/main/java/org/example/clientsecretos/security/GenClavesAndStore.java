package org.example.clientsecretos.security;

import com.nimbusds.jose.util.X509CertUtils;
import io.vavr.control.Either;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.example.common.modelo.User;
import org.example.clientsecretos.security.utils.Constantes;
import org.example.clientsecretos.servicios.Modelo.Claves;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Base64;


public class GenClavesAndStore {
    public Either<String, Claves> generarClaves() {
        Either<String, Claves> resultado;
        try {
            Security.addProvider(new BouncyCastleProvider());
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance(Constantes.RSA);
            keyGen.initialize(Constantes.KEYSIZE);
            KeyPair clavesRSA = keyGen.generateKeyPair();
            resultado = Either.right(Claves.builder().privateKey(clavesRSA.getPrivate()).publicKey(clavesRSA.getPublic()).build());
        } catch (NoSuchAlgorithmException e) {
            resultado = Either.left(Constantes.NO_HA_SIDO_POSIBLE_GENERAR_LAS_CLAVES_INTENTELO_MAS_TARDE);
        } catch (Exception e) {
            resultado = Either.left(Constantes.FALLO_INTERNO);
        }
        return resultado;
    }

    public Either<String, Boolean> generarKeyStore(User user, String certBase64, PrivateKey privateKey) {
        Either<String, Boolean> resultado;
        try {
            X509Certificate cert = X509CertUtils.parse(Base64.getUrlDecoder().decode(certBase64));
            KeyStore ks = KeyStore.getInstance(Constantes.PKCS_12);
            char[] password = user.getPass().toCharArray();
            ks.load(null, null);
            ks.setCertificateEntry(Constantes.PUBLICA, cert);
            ks.setKeyEntry(Constantes.PRIVADA, privateKey, password, new Certificate[]{cert}); //Aqui la contraseña del usuario.
            FileOutputStream fos = new FileOutputStream(Constantes.KEYSTORE + user.getNombre() + Constantes.KEYSTORE_PFX); //Aqui la ruta bien.
            ks.store(fos, password);
            fos.close();
            //Too correcto
            resultado = Either.right(true);
        } catch (Exception e) {
            resultado = Either.left(Constantes.FALLO_INTERNO);
        }
        return resultado;
    }

    public Either<String, String> getCertificadoLoggin(String nombreUser, String pass) {
        Either<String, String> resultado;
        try {
            KeyStore ksLoad = KeyStore.getInstance(Constantes.PKCS_12);
            char[] password = pass.toCharArray();
            ksLoad.load(new FileInputStream(Constantes.KEYSTORE + nombreUser + Constantes.KEYSTORE_PFX), password);
            X509Certificate cert = (X509Certificate) ksLoad.getCertificate(Constantes.PUBLICA);
            byte[] certEnBytes = cert.getEncoded();
            resultado = Either.right(Base64.getUrlEncoder().encodeToString(certEnBytes));
        } catch (NoSuchAlgorithmException | IOException e) {
            resultado = Either.left(Constantes.USUARIO_PASS_NOVALIDO);
        } catch (Exception e) {
            resultado = Either.left(Constantes.FALLO_INTERNO);
        }

        return resultado;
    }

    public Either<String, PublicKey> getPublicKey(String nombre, String pass) {
        Either<String, PublicKey> resultado;
        try {
            KeyStore ksLoad = KeyStore.getInstance(Constantes.PKCS_12);
            char[] password = pass.toCharArray();
            ksLoad.load(new FileInputStream(Constantes.KEYSTORE + nombre + Constantes.KEYSTORE_PFX), password);
            X509Certificate certLoad = (X509Certificate) ksLoad.getCertificate(Constantes.PUBLICA);
            PublicKey publicKey = certLoad.getPublicKey();
            resultado = Either.right(publicKey);
        } catch (NoSuchAlgorithmException | IOException e) {
            resultado = Either.left(Constantes.NO_HA_SIDO_POSIBLE_RECOGER_LA_CLAVE_PUBLICA_INTENTELO_MAS_TARDE);
        } catch (Exception e) {
            resultado = Either.left(Constantes.FALLO_INTERNO);
        }

        return resultado;
    }

    public Either<String, PrivateKey> getPrivateKey(String nombre, String pass) {
        Either<String, PrivateKey> resultado;
        //Para leer la privada:
        try {
            KeyStore ksLoad = KeyStore.getInstance(Constantes.PKCS_12);
            char[] password = pass.toCharArray();
            ksLoad.load(new FileInputStream(Constantes.KEYSTORE + nombre + Constantes.KEYSTORE_PFX), password);
            KeyStore.PasswordProtection pt = new KeyStore.PasswordProtection(password);
            KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) ksLoad.getEntry(Constantes.PRIVADA, pt);
            PrivateKey privateKey = privateKeyEntry.getPrivateKey();
            resultado = Either.right(privateKey);
        } catch (NoSuchAlgorithmException | IOException e) {
            resultado = Either.left(Constantes.NO_HA_SIDO_POSIBLE_RECOGER_LA_CLAVE_PRIVADA_INTENTELO_MAS_TARDE);
        } catch (UnrecoverableEntryException e) {
            //Es porque la pass es incorrecta.
            resultado = Either.left(Constantes.USUARIO_Y_O_CONTRASENA_NO_VALIDO);
        } catch (Exception e) {
            resultado = Either.left(Constantes.FALLO_INTERNO);
        }
        return resultado;
    }
}
