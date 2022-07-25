package org.example.encriptar.utils;

import com.google.common.primitives.Bytes;
import lombok.extern.log4j.Log4j2;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;

@Log4j2
public class Encriptar {

    public String encriptando(String cosaAEncriptar, String passEncriptado) {
        String mensaje;
        try {
            byte[] iv = new byte[Constantes.DOCE];
            byte[] salt = new byte[Constantes.DIECISEIS];
            SecureRandom sr = new SecureRandom();
            sr.nextBytes(iv);
            sr.nextBytes(salt);
            GCMParameterSpec parameterSpec = new GCMParameterSpec(Constantes.CIENTOVEINTIOCHO, iv);


            SecretKeyFactory factory = SecretKeyFactory.getInstance(Constantes.PBKDF_2_WITH_HMAC_SHA_256);
            KeySpec spec = new PBEKeySpec(passEncriptado.toCharArray(), salt, Constantes.ITERATION_COUNT, Constantes.KEY_LENGTH);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), Constantes.AES);

            Cipher cipher = Cipher.getInstance(Constantes.AES_GCM_NO_PADDING);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, parameterSpec);
            mensaje = Base64.getUrlEncoder().encodeToString(Bytes.concat(iv, salt,
                    cipher.doFinal(cosaAEncriptar.getBytes(StandardCharsets.UTF_8))));
        } catch (Exception e) {
            mensaje = null;
        }
        return mensaje;
    }

    public String desencriptando(String cosaADesencriptar, String passDesencriptar) {
        String mensaje;
        try {
            byte[] decoded = Base64.getUrlDecoder().decode(cosaADesencriptar);
            byte[] iv = Arrays.copyOf(decoded, Constantes.DOCE);
            byte[] salt = Arrays.copyOfRange(decoded, Constantes.DOCE, Constantes.VENTIOCHO);
            GCMParameterSpec parameterSpec = new GCMParameterSpec(Constantes.CIENTOVEINTIOCHO, iv);
            SecretKeyFactory factory = SecretKeyFactory.getInstance(Constantes.PBKDF_2_WITH_HMAC_SHA_256);
            KeySpec spec = new PBEKeySpec(passDesencriptar.toCharArray(), salt, Constantes.ITERATION_COUNT, Constantes.KEY_LENGTH);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), Constantes.AES);
            Cipher cipher = Cipher.getInstance(Constantes.AES_GCM_NO_PADDING);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, parameterSpec);
            mensaje = new String(cipher.doFinal(Arrays.copyOfRange(decoded, Constantes.VENTIOCHO, decoded.length)));
        } catch (Exception e) {
            mensaje = null;
        }
        return mensaje;
    }
}
