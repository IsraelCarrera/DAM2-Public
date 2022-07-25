package org.example.encriptar.utils;

//Coges un secreto y la encriptas de forma simetrica con una contraseña aleatoria.
//Esa contraseña aleatoria se cifra de forma asimetrica con la clavePública en otro BBDD.
//Si quieres compartir. Esta la compartes con otro, haciendo que se firme esa contraseña aleatoria con su clavePública
public class Constantes {
    public static final int DOCE = 12;
    public static final int DIECISEIS = 16;
    public static final int CIENTOVEINTIOCHO = 128;
    public static final String PBKDF_2_WITH_HMAC_SHA_256 = "PBKDF2WithHmacSHA256";
    public static final int ITERATION_COUNT = 65536;
    public static final int KEY_LENGTH = 256;
    public static final String AES = "AES";
    public static final String UTF_8 = "UTF-8";
    public static final String AES_GCM_NO_PADDING = "AES/GCM/noPadding";
    public static final int VENTIOCHO = 28;
    public static final String NO_SE_HA_ANADIDO_EL_SECRETO = "No se ha añadido el secreto";
    public static final String NO_HAY_SECRETOS_PARA_DICHO_USUARIO = "No hay secretos para dicho usuario";
    public static final String CONTRASENA_INCORRECTA = "Contraseña incorrecta";
    public static final String FXML_PRINCIPAL_FXML = "/fxml/principal.fxml";
    public static final String ENCRIPTACIÓN = "Encriptación";
    public static final String SECRETO_ANADIDO = "Secreto añadido.";
}
