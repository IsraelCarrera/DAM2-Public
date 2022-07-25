package org.example.encriptarasimetrico.servicios;

import io.vavr.control.Either;
import org.example.encriptarasimetrico.dao.DaoSecretos;
import org.example.encriptarasimetrico.modelo.SecretoConId;
import org.example.encriptarasimetrico.modelo.Secretos;
import org.example.encriptarasimetrico.modelo.SecretosCompartidos;
import org.example.encriptarasimetrico.security.EncriptadoAsimetrico;
import org.example.encriptarasimetrico.security.EncriptarSimetrico;
import org.example.encriptarasimetrico.security.GenConsClaves;
import org.example.encriptarasimetrico.servicios.utils.Constantes;

import javax.inject.Inject;
import java.io.File;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.*;
import java.util.stream.Collectors;

public class SecretosServices {
    private final DaoSecretos dao;
    private final EncriptarSimetrico encriptarSimetrico;
    private final GenConsClaves genConsClaves;
    private final EncriptadoAsimetrico encriptadoAsimetrico;

    @Inject
    public SecretosServices(DaoSecretos dao, EncriptarSimetrico encriptarSimetrico, GenConsClaves genConsClaves, EncriptadoAsimetrico encriptadoAsimetrico) {
        this.dao = dao;
        this.encriptarSimetrico = encriptarSimetrico;
        this.genConsClaves = genConsClaves;
        this.encriptadoAsimetrico = encriptadoAsimetrico;
    }

    public Either<String, Boolean> addSecreto(String user, String password, String secreto) {
        Either<String, Boolean> resultado;
        //Comprobamos que el usuario existe.
        if (!checkSiExisteUser(user)) {
            //Como no existe, hacemos los ficheros.
            genConsClaves.crearClaves(user, password);
        }
        //Una vez si o si están los ficheros de verdad, entonces generamos una clave aleatoria.
        String pass = generarPassRandom();
        //Ahora, vamos a encriptar simetricamente el secreto para que nos genere el código.
        Either<String, String> eitherEncriptarSecreto = encriptarSimetrico.encriptando(secreto, pass);
        if (eitherEncriptarSecreto.isRight()) {
            String secretoEncriptado = eitherEncriptarSecreto.get();
            //Añadimos el secreto, junto con el usuario a la Tabla Secretos. Necesito la ID, así que, que me la retorne.
            Either<String, Integer> eitherAddSecretro = dao.addSecreto(user, secretoEncriptado);
            if (eitherAddSecretro.isRight()) {
                //Ahora vamos a encriptar asimetricamente la pass, con la pública de nuestro usuario.
                //1º Cogemos la Public Key
                Either<String, PublicKey> eitherGetPublicKey = genConsClaves.getPublicKey(user);
                if (eitherGetPublicKey.isRight()) {
                    //Cogemos la publicKey y, encriptamos con ella.
                    PublicKey publicKey = eitherGetPublicKey.get();
                    Either<String, String> eitherCifrarPass = encriptadoAsimetrico.cifrarPassParaguardar(pass, publicKey);
                    if (eitherCifrarPass.isRight()) {
                        //Con el troncho de la pass cifrada de forma asimétrica, lo guardamos en la tabla de secretos compartidos,
                        //Ya que aun así, el secreto hay que si o si compartirlo con uno mismo primero.
                        resultado = dao.addSecretoCompartido(eitherAddSecretro.get(), user, eitherCifrarPass.get());
                    } else
                        resultado = Either.left(eitherCifrarPass.getLeft());
                } else {
                    resultado = Either.left(eitherGetPublicKey.getLeft());
                }
            } else {
                resultado = Either.left(eitherAddSecretro.getLeft());
            }
        } else {
            resultado = Either.left(eitherEncriptarSecreto.getLeft());
        }
        return resultado;
    }

    public Either<String, List<SecretoConId>> getSecretos(String user, String password) {
        Either<String, List<SecretoConId>> resultado;
        List<SecretoConId> secretos = new ArrayList<>();
        //Lo primero, comprobar que el usuario existe. Si no existe, pues adios.
        if (checkSiExisteUser(user)) {
            //Al saber que existe el usuario, nos traemos todos los datos de la tabla secretos_compartidos que tengan su userACompartir.
            Either<String, List<SecretosCompartidos>> eitherSecretosCompartidos = dao.selectSecretosCompartidos(user);
            if (eitherSecretosCompartidos.isRight()) {
                List<SecretosCompartidos> secretosCompartidosList = eitherSecretosCompartidos.get();
                //Como tenemos una lista de secretos que desencriptar, Tenemos que ir uno a uno desencriptando con la clave privada, entonces:
                //Compruebo que la lista no sea vacia, claro. Posteriormente recorro el array, desencriptando para conseguir la pass y llamando a la BBDD
                //para conseguir los secretos encriptados., tantas veces como secretos compartidos tenga. una vez tenga los secretos aquí,
                //los desencripto con dicha clave.
                //Como la clave privada siempre es la misma, vamos a cogerla ahora.
                if (!secretosCompartidosList.isEmpty()) {
                    Either<String, PrivateKey> eitherGetPrivateKey = genConsClaves.getPrivateKey(user, password);
                    if (eitherGetPrivateKey.isRight()) {
                        //Como la clave privada siempre es la misma, vamos a cogerla ahora.
                        PrivateKey privateKey = eitherGetPrivateKey.get();
                        for (SecretosCompartidos sc : secretosCompartidosList) {
                            //Vamos a conseguir la pass descifrando con la privada
                            Either<String, String> eitherDescrifrando = encriptadoAsimetrico.descifrarParaGetPass(sc.getClaveCifradaPublic(), privateKey);
                            if (eitherDescrifrando.isRight()) {
                                String pass = eitherDescrifrando.get();
                                //Aquí ya tendría la pass, entonces, hago la llamada por la ID del secretos compartidos a la tabla secretos.
                                Either<String, Secretos> eitherSecreto = dao.consultarSecretos(sc.getId());
                                if (eitherSecreto.isRight()) {
                                    //Aquí tenemos el secreto encriptado, así que lo desencriptamos y lo añadimos a nuestra lista.
                                    Either<String, String> eitherGetSecretoDesencriptado = encriptarSimetrico.desencriptando(eitherSecreto.get().getSecreto(), pass);
                                    if (eitherGetSecretoDesencriptado.isRight()) {
                                        secretos.add(SecretoConId.builder()
                                                .id(sc.getId())
                                                .secreto(eitherGetSecretoDesencriptado.get())
                                                .build());
                                    } else {
                                        secretos.add(SecretoConId.builder()
                                                .id(-1)
                                                .secreto(eitherGetSecretoDesencriptado.getLeft())
                                                .build());
                                    }
                                } else {
                                    secretos.add(SecretoConId.builder()
                                            .id(-1)
                                            .secreto(eitherSecreto.getLeft())
                                            .build());
                                }
                            } else {
                                //Como estoy en un bucle, voy a añadir a la lista de resultado el porque, de momento.
                                secretos.add(SecretoConId.builder()
                                        .id(-1)
                                        .secreto(eitherDescrifrando.getLeft())
                                        .build());
                            }
                        }
                        resultado = Either.right(secretos);
                    } else {
                        resultado = Either.left(eitherGetPrivateKey.getLeft());
                    }
                } else {
                    resultado = Either.left(Constantes.NO_HAY_SECRETOS_COMPARTIDOS_CONTIGO);
                }
            } else {
                resultado = Either.left(eitherSecretosCompartidos.getLeft());
            }
        } else {
            resultado = Either.left(Constantes.NO_EXISTE_DICHO_USUARIO);
        }
        return resultado;
    }

    public Either<String, Boolean> compartirSecreto(String userPrincipal, String password, String userACompartir, SecretoConId secreto) {
        Either<String, Boolean> resultado;
        //Confio en que siempre existirá el usuario, porque el comboBox solo muestra usuarios existentes.
        //Primero vamos otra vez a por los datos de secretos compartidos. Para ello busco ahora por ID y me traigo el cifrado asimetrico del userPrincipal
        Either<String, SecretosCompartidos> eitherSecretosCompartidos = dao.selectSecretosCompartidosPorId(secreto.getId());
        if (eitherSecretosCompartidos.isRight()) {
            //Una vez tengo el secreto, desencripto igual que arriba con la private del userPrincipal.
            //Cojo la Private del userPrincipal
            Either<String, PrivateKey> eitherPrivateKey = genConsClaves.getPrivateKey(userPrincipal, password);
            if (eitherPrivateKey.isRight()) {
                //Ahora desencripto para tener la pass.
                Either<String, String> eitherCogerPass = encriptadoAsimetrico.descifrarParaGetPass(eitherSecretosCompartidos.get().getClaveCifradaPublic(), eitherPrivateKey.get());
                if (eitherCogerPass.isRight()) {
                    String pass = eitherCogerPass.get();
                    //A este punto, ya tengo la pass. Ahora cojo la publica del usuario a Compartir.
                    Either<String, PublicKey> eitherCogerPublicKey = genConsClaves.getPublicKey(userACompartir);
                    if (eitherCogerPass.isRight()) {
                        //Encripto y cojo el troncho de la pass cifrada asimetricamente con la public key.
                        Either<String, String> eitherCifrandoUserCompartir = encriptadoAsimetrico.cifrarPassParaguardar(pass, eitherCogerPublicKey.get());
                        if (eitherCifrandoUserCompartir.isRight()) {
                            //Aquí ya tendría ese troncho, solo faltaría añadirlo a la BBDD.
                            resultado = dao.addSecretoCompartido(secreto.getId(), userACompartir, eitherCifrandoUserCompartir.get());
                        } else {
                            resultado = Either.left(eitherCifrandoUserCompartir.getLeft());
                        }
                    } else {
                        resultado = Either.left(eitherCogerPass.getLeft());
                    }
                } else {
                    resultado = Either.left(eitherCogerPass.getLeft());
                }
            } else {
                resultado = Either.left(eitherPrivateKey.getLeft());
            }
        } else {
            resultado = Either.left(eitherSecretosCompartidos.getLeft());
        }
        return resultado;
    }

    public Either<String, List<String>> comprobarUsuarios() {
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

    private String generarPassRandom() {
        byte[] passByte = new byte[50];
        SecureRandom sr = new SecureRandom();
        sr.nextBytes(passByte);
        String pass = new String(Base64.getUrlEncoder().encode(passByte));
        return pass;
    }
}
