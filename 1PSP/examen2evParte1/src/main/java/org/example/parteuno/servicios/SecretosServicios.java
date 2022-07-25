package org.example.parteuno.servicios;

import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import org.example.parteuno.dao.DaoSecretos;
import org.example.parteuno.modelo.Secreto;
import org.example.parteuno.security.EncriptarSimetrico;
import org.example.parteuno.security.GenConsClaves;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;


@Log4j2
public class SecretosServicios {
    private final DaoSecretos dao;
    private final GenConsClaves genConsClaves;
    private final EncriptarSimetrico encriptarSimetrico;

    public SecretosServicios() {
        this.dao  = new DaoSecretos();
        this.genConsClaves = new GenConsClaves();
        this.encriptarSimetrico = new EncriptarSimetrico();
    }

    public Either<String, String> hacerKeystoreJefeTribu(String nombre, String password){
        Either<String, String> resultado;
        //Vamos a hacer el keystore del jefe, firmandoselo él así mismo, porque es el más mandamas y más importante.
        Either<String, Boolean> eitherKeystore = genConsClaves.crearClaves(nombre,password);
        if(eitherKeystore.isRight()){
            resultado = Either.right("Usuario creado con éxito");
        }else{
            resultado = Either.left(eitherKeystore.getLeft());
        }

        return resultado;
    }


    public Either<String, String> addSecreto(String nombreTribu, String passDelSecreto, String fecha, String nombreJefe, String passJefe){
        Either<String, String> resultado;
        try {
            //Lo primero es encriptar ese secreto con la pass que nos dan.
            //Para ello, vamos a pasar la fecha a String
            Either<String, String> eitherEncriptar = encriptarSimetrico.encriptando(fecha, passDelSecreto);
            //Si es correcto, seguimos.
            if (eitherEncriptar.isRight()) {
                //Aquí ya procedemos a hacer la firma con la privada del jefe de la tribu (Por eso pedimos el nombre y la pass de este personaje)
                String secretoCifrado = eitherEncriptar.get();
                Either<String, PrivateKey> eitherGetPrivateKey= genConsClaves.getPrivateKey(nombreJefe,passJefe);
                if(eitherGetPrivateKey.isRight()) {
                    Signature sign = Signature.getInstance("SHA256WithRSA");
                    MessageDigest hash = MessageDigest.getInstance("SHA-512");

                    sign.initSign(eitherGetPrivateKey.get());
//                    Firmo el secreto encriptado simetricamente
                    sign.update(hash.digest(secretoCifrado.getBytes()));
                    byte[] firmaEnBytes = sign.sign();
                    String firmaEnBase64 = Base64.getUrlEncoder().encodeToString(firmaEnBytes);
                    //-------------------------------- AQUI YA TENEMOS LA FIRMA HECHO, SOLO SE PROCEDERÍA A HACER UN ADD
                    Secreto secreto = new Secreto(nombreTribu,secretoCifrado,firmaEnBase64);
                    Either<String,Boolean> eitherAdd =dao.addSecreto(secreto);
                    if(eitherAdd.isRight()){
                        resultado = Either.right("Se ha añadido el secreto correctamente");
                    }else{
                        resultado = Either.left(eitherAdd.getLeft());
                    }
                }else{
                    resultado = Either.left(eitherGetPrivateKey.getLeft());
                }
            } else {
                resultado = Either.left(eitherEncriptar.getLeft());
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
            resultado = Either.left("No se ha podido realizar el add secreto");
        }
        return  resultado;
    }

    public Either<String, List<String>> getAllSecretosByName(String nombreTribu, String passDelSecreto,String nombreJefe){
        Either<String, List<String>> resultado;
        //Lo primero que hare será coger todos los secretos guardados por la nombre de la tribu
        try {
            Either<String, List<Secreto>> eitherGetAllSecretosDao = dao.consultarSecreto(nombreTribu);
            if (eitherGetAllSecretosDao.isRight()) {
                //Una vez tengo todos, voy a ir uno por uno descifrandolo y comprobando que está firmado.
                //Para ello, primero vamos a coger la publickey del señor jefe.
                Either<String, PublicKey> eitherGetPublicKeyJefe = genConsClaves.getPublicKey(nombreJefe);
                if(eitherGetPublicKeyJefe.isRight()) {
                    //Ahora ya, teniendo la publica, empezamos recorriendo el array para primero verificar la firma y posteriormente, desencriptar.
                    List<String> fechasDesencriptadas = new ArrayList<>();
                    List<Secreto> listaDeSecretos = eitherGetAllSecretosDao.get();
                    for (Secreto secreto : listaDeSecretos) {
                        //Primero comprobamos la firma, necesito el nombre del jefe, que en teoría lo deben de saber los mismos usuarios.
                        Signature sign = Signature.getInstance("SHA256WithRSA");
                        MessageDigest hash = MessageDigest.getInstance("SHA-512");
                        //Lo ponemos en verificar
                        sign.initVerify(eitherGetPublicKeyJefe.get());
                        sign.update(hash.digest(secreto.getSecreto().getBytes()));
                        byte [] firma = Base64.getUrlDecoder().decode(secreto.getFirma());
                        if(sign.verify(firma)){
                            //Sabiendo que la firma es correcta, procedemos a desencriptar el secreto.
                            Either<String,String> eitherDesencriptar = encriptarSimetrico.desencriptando(secreto.getSecreto(),passDelSecreto);
                            if(eitherDesencriptar.isRight()){
                                fechasDesencriptadas.add(eitherDesencriptar.get() + " <- Y COMPROBADO QUE ESTA FIRMADO");
                            }else{
                                fechasDesencriptadas.add("Este secreto no ha sido posible desencriptarlo pero si se comprobó la firma correctamente");
                            }
                        }else{
                            fechasDesencriptadas.add("No se ha podido comprobar que la firma fuese correcta");
                        }
                    }
                    resultado = Either.right(fechasDesencriptadas);
                }else{
                    resultado = Either.left(eitherGetPublicKeyJefe.getLeft());
                }
            } else {
                resultado = Either.left(eitherGetAllSecretosDao.getLeft());
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
            resultado = Either.left("Error en el services del getallsecretos");
        }
        return resultado;
    }
}
