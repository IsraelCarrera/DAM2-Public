package org.example.parteuno.gui;

import io.vavr.control.Either;
import org.example.parteuno.servicios.SecretosServicios;

import java.security.PrivateKey;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        SecretosServicios ss = new SecretosServicios();
        Scanner sc = new Scanner(System.in);

        //Primero generamos la keyStore del jefe, que lo necesita para poder firmar la fecha (encriptada de forma simetrica)
        //Inicio esta linea y luego la comento, para que solo se genere el keystore una vez.
//        crearKeyStoreDelJefe(ss,sc);

        // El jefe está creado ya su pass y su nombre es --> root.
        menu(ss,sc);
    }

    public static void menu(SecretosServicios ss, Scanner sc){
        String opcion;
        do {
            System.out.println("OPCIONES");
            System.out.println("1.- Add secreto");
            System.out.println("2.- Consultar secretos");
            System.out.println("0. Salir");
            System.out.print("Indique opción: ");
            opcion = sc.nextLine();
            if(opcion.equals("1")){
                addSecreto(ss,sc);
            }else if(opcion.equals("2")){
                comprobarSecretos(ss,sc);
            }else if (!opcion.equals("0")){
                System.out.println("OPCION NO VALIDA.");
            }
        }while (!opcion.equals("0"));
        System.out.println("ADIOS");
    }

    public static void crearKeyStoreDelJefe(SecretosServicios ss, Scanner sc){
        String nameJefeTribu;
        String passJefeTribu;
        System.out.println("JEFE DE LA TRIBU:");
        System.out.print("Indique su nombre para generar las contraseñas: ");
        nameJefeTribu = sc.nextLine();
        System.out.print("Indique la password para generar las contraseñas: ");
        passJefeTribu = sc.nextLine();
        Either<String, String> respuesta = ss.hacerKeystoreJefeTribu(nameJefeTribu,passJefeTribu);
        if (respuesta.isRight()){
            System.out.println(respuesta.get());
        }else{
            System.out.println(respuesta.getLeft());
        }
    }

    //Funcion addSecreto.
    public static void addSecreto(SecretosServicios ss, Scanner sc){
        String nombreTribu;
        String passTribu;
        String fechaReunion;
        String nombreJefe;
        String passJefe;
        System.out.println("Vamos a añadir un secreto.");
        System.out.println("PARA EL DE LA TRIBU:");
        System.out.print("Indique el nombre de la tribu: ");
        nombreTribu = sc.nextLine();
        System.out.print("Indique la pass de con la que desee guardar el secreto: ");
        passTribu = sc.nextLine();
        System.out.print("Por último, Indique la fecha de la reunión: ");
        fechaReunion = sc.nextLine();
        System.out.println("//////////////////////////////////////////////// AHORA PASALE EL ORDENADOR AL JEFE//////////////////////////");
        System.out.println("AHORA VA A FIRMAR DICHO SECRETO, JEFE.");
        System.out.print("Indique su nombre:");
        nombreJefe = sc.nextLine();
        System.out.print("Indique su pass: ");
        passJefe = sc.nextLine();
        System.out.println("***************************************************************");
        Either<String, String> resultado = ss.addSecreto(nombreTribu,passTribu,fechaReunion,nombreJefe,passJefe);
        if(resultado.isRight()){
            System.out.println(resultado.get());
        }else{
            System.out.println(resultado.getLeft());
        }
    }

    public static void comprobarSecretos(SecretosServicios ss, Scanner sc){
        String nombreTribu;
        String passTribu;
        String nombreJefe;
        System.out.println("*** VAMOS A COMPROBAR LAS FECHAS ***");
        System.out.print("Indique primero el nombre de la tribu: ");
        nombreTribu = sc.nextLine();
        System.out.print("Ahora indique la password: ");
        passTribu = sc.nextLine();
        System.out.print("Y por ultimo, indique el nombre del jefe supremo: ");
        nombreJefe = sc.nextLine();
        Either<String, List<String>> resultado = ss.getAllSecretosByName(nombreTribu,passTribu,nombreJefe);
        if(resultado.isRight()){
            System.out.println("Lista de secretos: ");
            resultado.get().forEach(System.out::println);
        }else{
            System.out.println(resultado.getLeft());
        }
    }
}
