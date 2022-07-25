package org.example.clientsecretos.security;

import io.vavr.control.Either;
import org.example.clientsecretos.security.utils.Constantes;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

public class EncriptadoAsimetrico {

    public Either<String, String> cifrarPassParaguardar(String pass, PublicKey clavePublica) {
        Either<String, String> resultado;
        try {
            //Se pasa a Bytes lo que se va a cifrar (La contraseña en este caso)
            byte[] buffer = pass.getBytes(StandardCharsets.UTF_8);
            //Cogemos el cifrador
            Cipher cifrador = Cipher.getInstance(Constantes.RSA);
            //Lo ponemos en modo cifrar con la pública de a quien queremos compartir el secreto.
            cifrador.init(Cipher.ENCRYPT_MODE, clavePublica);
            //Con esto se cifra
            byte[] bufferCifrado = cifrador.doFinal(buffer);
            //Y como queremos guardar lo cifrado, pues se pasa a String
            byte[] bufferCode64 = Base64.getUrlEncoder().encode(bufferCifrado);
            //Este es el troncho a guardar.
            String probando = new String(bufferCode64);
            resultado = Either.right(probando);
        } catch (NoSuchPaddingException | InvalidKeyException | BadPaddingException | NoSuchAlgorithmException | IllegalBlockSizeException e) {
            resultado = Either.left(Constantes.NO_HA_SIDO_POSIBLE_CIFRAR_CON_LA_CLAVE_PUBLICA);
        }
        return resultado;
    }

    public Either<String, String> descifrarParaGetPass(String troncho, PrivateKey clavePrivada) {
        Either<String, String> resultado;
        try {
            Cipher cifrador = Cipher.getInstance(Constantes.RSA);
            //El cyfer pasa a descifrando
            cifrador.init(Cipher.DECRYPT_MODE, clavePrivada);
            byte[] bufferPlano2 = cifrador.doFinal(Base64.getUrlDecoder().decode(troncho));
            String pass = new String(bufferPlano2);
            resultado = Either.right(pass);
        } catch (NoSuchPaddingException | InvalidKeyException | BadPaddingException | NoSuchAlgorithmException | IllegalBlockSizeException e) {
            resultado = Either.left(Constantes.NO_HA_SIDO_POSIBLE_DESCIFRAR_CON_LA_CLAVE_PRIVADA);
        }
        return resultado;
    }
}
