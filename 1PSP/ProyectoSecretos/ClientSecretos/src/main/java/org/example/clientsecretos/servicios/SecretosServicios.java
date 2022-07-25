package org.example.clientsecretos.servicios;

import com.nimbusds.jose.util.X509CertUtils;
import io.vavr.control.Either;
import org.example.clientsecretos.dao.DaoSecretos;
import org.example.clientsecretos.dao.utils.UserCacheado;
import org.example.clientsecretos.domain.Secretos;
import org.example.clientsecretos.security.EncriptadoAsimetrico;
import org.example.clientsecretos.security.EncriptarSimetrico;
import org.example.clientsecretos.security.GenClavesAndStore;
import org.example.clientsecretos.servicios.utils.Constantes;
import org.example.common.modelo.Info;
import org.example.common.modelo.SecretosCommon;
import org.example.common.modelo.User;

import javax.inject.Inject;
import java.security.*;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class SecretosServicios {
    private final DaoSecretos dao;
    private final UserCacheado userCacheado;
    private final EncriptarSimetrico encriptarSimetrico;
    private final GenClavesAndStore genConsClaves;
    private final EncriptadoAsimetrico encriptadoAsimetrico;


    @Inject
    public SecretosServicios(DaoSecretos dao, UserCacheado userCacheado, EncriptarSimetrico encriptarSimetrico, GenClavesAndStore genConsClaves, EncriptadoAsimetrico encriptadoAsimetrico) {
        this.dao = dao;
        this.userCacheado = userCacheado;
        this.encriptarSimetrico = encriptarSimetrico;
        this.genConsClaves = genConsClaves;
        this.encriptadoAsimetrico = encriptadoAsimetrico;
    }

    public Either<String, Info> addSecreto(String secreto) {
        Either<String, Info> resultado;
        try {
            //Primero generamos una contraseña aleatoria
            String pass = generarPassRandom();
            //Ahora encriptamos la pass de forma SIMETRICA.
            Either<String, String> eitherEncriptarSecreto = encriptarSimetrico.encriptando(secreto, pass);
            if (eitherEncriptarSecreto.isRight()) {
                String secretoCifrado = eitherEncriptarSecreto.get();
                //Ahora para firmar, pillamos la privada del usuario.
                Either<String, PrivateKey> eitherGetPrivateKey = genConsClaves.getPrivateKey(userCacheado.getNombre(), userCacheado.getPass());
                if (eitherGetPrivateKey.isRight()) {
                    Signature sign = Signature.getInstance(Constantes.SHA_256_WITH_RSA);
                    MessageDigest hash = MessageDigest.getInstance(Constantes.SHA_512);
                    sign.initSign(eitherGetPrivateKey.get());
//                    Firmo el secreto encriptado simetricamente
                    sign.update(hash.digest(secretoCifrado.getBytes()));
                    byte[] firmaEnBytes = sign.sign();
                    String firmaEnBase64 = Base64.getUrlEncoder().encodeToString(firmaEnBytes);
                    //Hasta aquí ya tenemos la firma y lo que iria en la tabla de Secretos.
                    //Ahora vamos a cifrar asimetricamente para mandarlo too de una.
                    //Primero cogemos la public Key
                    Either<String, PublicKey> eitherGetPublicKey = genConsClaves.getPublicKey(userCacheado.getNombre(), userCacheado.getPass());
                    if (eitherGetPublicKey.isRight()) {
                        //Cogemos la publica y encriptamos asimetricamente.
                        PublicKey publicKey = eitherGetPublicKey.get();
                        Either<String, String> eitherCifrarPass = encriptadoAsimetrico.cifrarPassParaguardar(pass, publicKey);
                        if (eitherCifrarPass.isRight()) {
                            //Ahora ya tenemos too bien y creamos la clase Secretos para mandarla al servidor.
                            SecretosCommon secretosCommon = SecretosCommon.builder()
                                    .nombre(userCacheado.getNombre())
                                    .claveCifradaPublica(eitherCifrarPass.get())
                                    .firma(firmaEnBase64)
                                    .secretoCifrado(secretoCifrado)
                                    .id(0)
                                    .build();
                            //Y por ultimo, llamamos al dao
                            resultado = dao.addSecreto(secretosCommon);
                        } else {
                            resultado = Either.left(eitherCifrarPass.getLeft());
                        }
                    } else {
                        resultado = Either.left(eitherGetPrivateKey.getLeft());
                    }
                } else {
                    resultado = Either.left(eitherGetPrivateKey.getLeft());
                }
            } else {
                resultado = Either.left(eitherEncriptarSecreto.getLeft());
            }
        } catch (Exception e) {
            resultado = Either.left(Constantes.FALLO_EN_ALGUN_TRY_FALLO_EN_SERVIDOR);
        }
        return resultado;
    }

    public Either<String, List<Secretos>> getAllSecretos(List<User> usuarios) {
        Either<String, List<Secretos>> resultado;
        //Lo primerisimo, cogemos la private key del usuario.
        try {
            Either<String, PrivateKey> eitherPrivateKey = genConsClaves.getPrivateKey(userCacheado.getNombre(), userCacheado.getPass());
            if (eitherPrivateKey.isRight()) {
                PrivateKey privateKey = eitherPrivateKey.get();
                //Ahora cogemos la lista entera.
                Either<String, List<SecretosCommon>> eitherSecretosServer = dao.getAllSecretos(userCacheado.getNombre());
                if (eitherSecretosServer.isRight()) {
                    List<SecretosCommon> listaSecretosEncriptados = eitherSecretosServer.get();
                    //Ahora que ya tenemos todos los secretos, vamos a proceder, uno a uno a hacer todas las cosas, con un for.
                    //Primero inicializamos una Lista de Secretos vacia.
                    List<Secretos> secretosList = new ArrayList<>();
                    for (SecretosCommon secretosCommon : listaSecretosEncriptados) {
                        //Primero desencriptamos asimetricamente la pass.
                        Either<String, String> eitherDesencrintarAsimetricamente = encriptadoAsimetrico.descifrarParaGetPass(secretosCommon.getClaveCifradaPublica(), privateKey);
                        if (eitherDesencrintarAsimetricamente.isRight()) {
                            //Una vez tenemos el secreto descifrado, procedemos a comprobar la firma, para ello, necesitamos al usuario que ha cifrado el secreto, junto con su certificado (que ahí está su publica).
                            //Primero buscamos al usuario.
                            User usuarioQueFirmo = usuarios.stream().filter(user -> user.getNombre().equals(secretosCommon.getNombreDelDuenoSecreto())).findFirst().orElse(null);
                            if (usuarioQueFirmo != null) {
                                //Ahora que tenemos al usuario y su certificado, cogemos el certificado y de él, su primary key.
                                X509Certificate cert = X509CertUtils.parse(Base64.getUrlDecoder().decode(usuarioQueFirmo.getCertificadoBase64()));
                                PublicKey publicKeyUsuarioQueFirmo = cert.getPublicKey();
                                //Y ya con la publica, comprobamos:
                                Signature sign = Signature.getInstance(Constantes.SHA_256_WITH_RSA);
                                MessageDigest hash = MessageDigest.getInstance(Constantes.SHA_512);
                                //Lo ponemos en verificar
                                sign.initVerify(publicKeyUsuarioQueFirmo);
                                //Aqui va la pass ya encriptada
                                sign.update(hash.digest(secretosCommon.getSecretoCifrado().getBytes()));
                                byte[] firma = Base64.getUrlDecoder().decode(secretosCommon.getFirma());
                                if (sign.verify(firma)) {
                                    //Y aqui implica que es correcto que lo firmo él, ya solo nos faltaría desencriptar asimetricamente el secreto.
                                    Either<String, String> eitherDesencriptar = encriptarSimetrico.desencriptando(secretosCommon.getSecretoCifrado(), eitherDesencrintarAsimetricamente.get());
                                    if (eitherDesencriptar.isRight()) {
                                        secretosList.add(Secretos.builder().secreto(eitherDesencriptar.get()).passSecretoCifradaAsimetricamente(secretosCommon.getClaveCifradaPublica()).id(secretosCommon.getId()).build());
                                    } else {
                                        secretosList.add(Secretos.builder().secreto(Constantes.SECRETO_DESENCRIPTADO_INCORRECTAMENTE_SE_HA_PERDIDO_EL_SECRETO_PARA_SIEMPRE).id(secretosCommon.getId()).build());
                                    }
                                } else {
                                    secretosList.add(Secretos.builder().secreto(Constantes.EL_USUARIO_NO_FIRMO_ESTE_SECRETO_NO_LO_VERAS).id(secretosCommon.getId()).build());
                                }
                            } else {
                                secretosList.add(Secretos.builder().secreto(Constantes.NO_HAY_USUARIO_CON_FIRMA_NO_LO_VERAS).id(secretosCommon.getId()).build());
                            }
                        } else {
                            //Si falla añadiremos un "secreto" que indique que dicho secreto no ha podido ser descifrado.
                            secretosList.add(Secretos.builder().secreto(Constantes.NO_SE_HA_PODIDO_DESCIFRAR_ESTE_SECRETO).id(secretosCommon.getId()).build());
                        }
                    }
                    resultado = Either.right(secretosList);
                } else {
                    resultado = Either.left(eitherSecretosServer.getLeft());
                }
            } else {
                resultado = Either.left(eitherPrivateKey.getLeft());
            }
        } catch (Exception e) {
            resultado = Either.left(Constantes.FALLO_AL_HACER_CUALQUIER_GESTION);
        }
        return resultado;
    }

    public Either<String, Info> compartirSecretos(Secretos secretos, User user) {
        Either<String, Info> resultado;
        //LO MAS IMPORTANTE, NO COMPARTIR EL SECRETO CONSIGO MISMO. Lo compruebo aquí.
        if (!user.getNombre().equals(userCacheado.getNombre())) {
            //Lo primero es coger la PK del usuario.
            try {
                X509Certificate cert = X509CertUtils.parse(Base64.getUrlDecoder().decode(user.getCertificadoBase64()));
                PublicKey publicKeyUsuarioACompartir = cert.getPublicKey();
                //Ahora que ya tenemos la public key, vamos a descifrar el secreto con la privada del user y luego lo ciframos con la publica del que vamos a compartir
                Either<String, PrivateKey> eitherGetPrivate = genConsClaves.getPrivateKey(userCacheado.getNombre(), userCacheado.getPass());
                if (eitherGetPrivate.isRight()) {
                    //Desciframos
                    Either<String, String> eitherDescifrandoAsimetricamente = encriptadoAsimetrico.descifrarParaGetPass(secretos.getPassSecretoCifradaAsimetricamente(), eitherGetPrivate.get());
                    if (eitherDescifrandoAsimetricamente.isRight()) {
                        //Y aquí ya tenemos la pass, la cual, la ciframos con la publica que tenemos arriba.
                        Either<String, String> eitherCifrando = encriptadoAsimetrico.cifrarPassParaguardar(eitherDescifrandoAsimetricamente.get(), publicKeyUsuarioACompartir);
                        if (eitherCifrando.isRight()) {
                            //Aqui ya tenemos lo que vamos a mandar, así que lo mandamos.
                            resultado = dao.compartirSecreto(SecretosCommon.builder().id(secretos.getId()).nombre(user.getNombre()).claveCifradaPublica(eitherCifrando.get()).build());
                        } else {
                            resultado = Either.left(eitherCifrando.getLeft());
                        }
                    } else {
                        resultado = Either.left(eitherDescifrandoAsimetricamente.getLeft());
                    }
                } else {
                    resultado = Either.left(eitherGetPrivate.getLeft());
                }
            } catch (Exception e) {
                resultado = Either.left(Constantes.NO_SE_HA_PODIDO_COMPARTIR_EL_SECRETO);
            }
        } else {
            resultado = Either.left(Constantes.NO_PUEDES_COMPARTIR_UN_SECRETO_CONTIGO_MISMO);
        }

        return resultado;
    }

    private String generarPassRandom() {
        byte[] passByte = new byte[16];
        SecureRandom sr = new SecureRandom();
        sr.nextBytes(passByte);
        String pass = new String(Base64.getUrlEncoder().encode(passByte));
        return pass;
    }
}
