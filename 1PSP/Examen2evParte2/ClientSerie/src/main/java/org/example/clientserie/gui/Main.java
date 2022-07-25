package org.example.clientserie.gui;

import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import org.example.clientserie.dao.utils.UserCacheado;
import org.example.clientserie.servicios.SeriesServicios;
import org.example.clientserie.servicios.UsuarioServicios;
import org.example.common.modelo.Capitulo;
import org.example.common.modelo.Serie;
import org.example.common.modelo.Usuario;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        SeriesServicios ss = new SeriesServicios();
        UsuarioServicios us = new UsuarioServicios();
        Scanner sc = new Scanner(System.in);
        menu1(us, ss, sc);
    }

    public static void menu1(UsuarioServicios us, SeriesServicios ss, Scanner sc) {
        int opc;
        try {
            do {
                System.out.println("----BIENVENIDO A LA APP DE SERIES----");
                System.out.println("---Menú loggin---");
                System.out.println("1.- Crear cuenta.");
                System.out.println("2.- Entrar en la aplicación.");
                System.out.println("0.- Salir.");
                System.out.print("Indique lo que desea hacer: ");
                opc = sc.nextInt();
                sc.nextLine();
                if (opc == 1) {
                    addUser(us, sc);
                } else if (opc == 2) {
                    hacerLoggin(us, sc);
                    if (UserCacheado.usuario != null) {
                        menu2(ss, sc);
                    }
                    System.out.println(UserCacheado.usuario);
                } else if (opc == 0) {
                    System.out.println("ADIOS.");
                } else {
                    System.out.println("Numero inválido.");
                }
            } while (opc != 0);
        } catch (Exception e) {
            System.out.println("No has escrito un número, adios.");
        }
    }

    public static void menu2(SeriesServicios ss, Scanner sc) {
        int opc;
        try {
            do {
                System.out.println("----MENU DE LA APLICACIÓN----");
                System.out.println("1.- Ver todas las series.");
                System.out.println("2.- Ver una serie.");
                System.out.println("3.- Add serie.");
                System.out.println("4.- Add capitulo a una serie.");
                System.out.println("5.- update un capitulo de una serie.");
                System.out.println("6.- Delete serie.");
                System.out.println("7.- Delete un capitulo de una serie.");
                System.out.println("0.- Salir.");
                System.out.print("Indique lo que desea hacer: ");
                opc = sc.nextInt();
                sc.nextLine();
                switch (opc) {
                    case 1:
                        getAllSeries(ss);
                        break;
                    case 2:
                        getOneSerie(ss, sc);
                        break;
                    case 3:
                        addSerie(ss, sc);
                        break;
                    case 4:
                        addCapitulo(ss, sc);
                        break;
                    case 5:
                        updateCapitulo(ss, sc);
                        break;
                    case 6:
                        borrarSerie(ss, sc);
                        break;
                    case 7:
                        borrarCapitulo(ss, sc);
                        break;
                    case 0:
                        System.out.println("Estás saliendo de la app");
                        break;
                    default:
                        System.out.println("No has indicado una opción válida");
                }
            } while (opc != 0);
        } catch (Exception e) {
            System.out.println("No has indicado un número");
        }finally {
            UserCacheado.id = -1;
            UserCacheado.token = null;
            UserCacheado.usuario = null;
            UserCacheado.pass = null;
        }

    }

    public static void addUser(UsuarioServicios us, Scanner sc) {
        String nombre, pass, tipo = "";
        int opc = -1;
        System.out.println("VAMOS A AÑADIR UN USER: ");
        do {
            System.out.print("Indique el nombre del usuario: ");
            nombre = sc.nextLine();
        } while (nombre.isBlank());
        do {
            System.out.print("Ahora indique la contraseña: ");
            pass = sc.nextLine();
        } while (pass.isBlank());
        try {
            do {
                System.out.print("Por ultimo, indique con 0 si desea consumir datos ilimitados (Premium) o 1 consumir lo menos posible (Básico): ");
                opc = sc.nextInt();
                sc.nextLine();
                if (opc == 1) {
                    tipo = "Basico";
                } else if (opc == 0) {
                    tipo = "Premium";
                } else {
                    System.out.println("... Repita.");
                }
            } while (opc != 0 && opc != 1);
            Usuario u = Usuario.builder().tipo(tipo).pass(pass).nombre(nombre).build();
            Single<Either<String, Usuario>> resultado = us.addUser(u);
            resultado.blockingSubscribe(either -> either
                            .peek(user -> System.out.println("Se ha registrado correctamente."))
                            .peekLeft(fail -> System.out.println("ha habido un fallo. \n" + fail)),
                    throwable -> System.out.println(throwable.getMessage())
            );
        } catch (Exception e) {
            System.out.println("No has puesto un número. Intentelo de nuevo.");
        }
    }

    public static void hacerLoggin(UsuarioServicios us, Scanner sc) {
        String nombre, pass;
        System.out.println("---- HACIENDO LOGGIN ----");
        do {
            System.out.print("Indique el nombre del usuario: ");
            nombre = sc.nextLine();
        } while (nombre.isBlank());
        do {
            System.out.print("Ahora indique la contraseña: ");
            pass = sc.nextLine();
        } while (pass.isBlank());
        UserCacheado.usuario = nombre;
        UserCacheado.pass = pass;

        Single<Either<String, Usuario>> resultado = us.getUserLoggin(nombre, pass);
        resultado.blockingSubscribe(either -> either
                        .peek(user -> {
                                    System.out.println("Se ha loggeado correctamente. Bienvenido.");
                                    UserCacheado.id = user.getId();
                                    if (user.getTipo().equals("Premium")) {
                                        UserCacheado.token = user.getToken();
                                    }
                                }
                        )
                        .peekLeft(fail -> {System.out.println("ha habido un fallo. \n" + fail);
                            UserCacheado.usuario = null;
                            UserCacheado.pass = null;}),
                throwable -> System.out.println(throwable.getMessage())
        );
    }

    //Funciones que puede hacer el usuario.

    public static void addSerie(SeriesServicios ss, Scanner sc) {
        String nombreSerie;
        System.out.println("Vamos a añadir una serie a su cuenta.");
        do {
            System.out.print("Indique el nombre de la serie: ");
            nombreSerie = sc.nextLine();
        } while (nombreSerie.isBlank());
        Serie serie = Serie.builder().idUser(UserCacheado.id).nombre(nombreSerie).build();

        Single<Either<String, Serie>> resultado = ss.addSerie(serie);
        resultado.blockingSubscribe(either -> either
                        .peek(ser -> System.out.println("La serie se ha añadido correctamente."))
                        .peekLeft(fail -> System.out.println("ha habido un fallo. \n" + fail)),
                throwable -> System.out.println(throwable.getMessage())
        );
    }

    public static void addCapitulo(SeriesServicios ss, Scanner sc) {
        String nombreCapitulo;
        int idSerie;
        System.out.println("Vamos a añadir un capitulo a una serie de su cuenta.");
        try {
            System.out.print("Indique el id de la serie (la primera serie que agregaste es el 0... y así sucesivamente): ");
            idSerie = sc.nextInt();
            sc.nextLine();
            do {
                System.out.print("Indique el nombre del capitulo: ");
                nombreCapitulo = sc.nextLine();
            } while (nombreCapitulo.isBlank());
            Capitulo capitulo = Capitulo.builder().idSerie(idSerie).idUser(UserCacheado.id).nombreCap(nombreCapitulo).haSidoVisto(false).build();
            Single<Either<String, Capitulo>> resultado = ss.addCapitulo(capitulo);
            resultado.blockingSubscribe(either -> either
                            .peek(cap -> System.out.println("El capitulo se ha añadido correctamente a la serie indicada."))
                            .peekLeft(fail -> System.out.println("ha habido un fallo. \n" + fail)),
                    throwable -> System.out.println(throwable.getMessage())
            );
        } catch (Exception e) {
            System.out.println("Has escrito un string en vez de un número.");
        }
    }

    public static void borrarSerie(SeriesServicios ss, Scanner sc) {
        int idSerie;
        System.out.println("Vamos a borrar una serie.");
        try {
            System.out.print("Indique el id de la serie a borrar: ");
            idSerie = sc.nextInt();
            sc.nextLine();
            Single<Either<String, Boolean>> resultado = ss.deleteSerie(UserCacheado.id, idSerie);
            resultado.blockingSubscribe(either -> either
                            .peek(serie -> System.out.println("La serie indicada se ha borrado con éxito"))
                            .peekLeft(fail -> System.out.println("ha habido un fallo. \n motivo: " + fail)),
                    throwable -> System.out.println(throwable.getMessage())
            );
        } catch (Exception e) {
            System.out.println("Has escrito un string en vez de un número.");
        }
    }

    public static void borrarCapitulo(SeriesServicios ss, Scanner sc) {
        int idSerie, idCapitulo;
        System.out.println("Vamos a borrar un capitulo de una serie.");
        try {
            System.out.print("Indique el id de la serie a borrar: ");
            idSerie = sc.nextInt();
            sc.nextLine();
            System.out.print("Indique el id el capitulo a borrar: ");
            idCapitulo = sc.nextInt();
            sc.nextLine();
            Single<Either<String, Boolean>> resultado = ss.deleteCapitulo(UserCacheado.id, idSerie, idCapitulo);
            resultado.blockingSubscribe(either -> either
                            .peek(cap -> System.out.println("El capítulo de la serie indicada se ha borrado con éxito"))
                            .peekLeft(fail -> System.out.println("ha habido un fallo. \n motivo: " + fail)),
                    throwable -> System.out.println(throwable.getMessage())
            );
        } catch (Exception e) {
            System.out.println("Has escrito un string en vez de un número.");
        }
    }

    public static void updateCapitulo(SeriesServicios ss, Scanner sc) {
        int idSerie, idCapitulo, opc;
        boolean haSidoVista = false;
        System.out.println("Vamos a modificar un capitulo de una serie.");
        try {
            System.out.print("Indique el id de la serie a tratar: ");
            idSerie = sc.nextInt();
            sc.nextLine();
            System.out.print("Indique el id el capitulo a modificar: ");
            idCapitulo = sc.nextInt();
            sc.nextLine();
            do {
                System.out.print("Ahora, indique si ha sido o no vista: (1 vista - 0 No vista)");
                opc = sc.nextInt();
                if (opc == 1) {
                    haSidoVista = true;
                }
            } while (opc != 0 && opc != 1);
            Single<Either<String, Boolean>> resultado = ss.updateCapitulo(UserCacheado.id, idSerie, idCapitulo, haSidoVista);
            resultado.blockingSubscribe(either -> either
                            .peek(cap -> System.out.println("Se ha actualizado la información del capitulo."))
                            .peekLeft(fail -> System.out.println("ha habido un fallo. \n motivo: " + fail)),
                    throwable -> System.out.println(throwable.getMessage())
            );
        } catch (Exception e) {
            System.out.println("Has escrito un string en vez de un número.");
        }
    }

    public static void getAllSeries(SeriesServicios ss) {
        System.out.println("---Mostrando todas las series---");
        Single<Either<String, List<Serie>>> resultado = ss.getAllSeries(UserCacheado.id);
        resultado.blockingSubscribe(either -> either
                        .peek(series -> series.forEach(System.out::println))
                        .peekLeft(fail -> System.out.println("ha habido un fallo. \n motivo: " + fail)),
                throwable -> System.out.println(throwable.getMessage())
        );
    }

    public static void getOneSerie(SeriesServicios ss, Scanner sc) {
        int idSerie;
        System.out.println("Vamos a buscar una serie.");
        try {
            System.out.print("Indique el id de la serie que desea obtener: ");
            idSerie = sc.nextInt();
            sc.nextLine();
            Single<Either<String, Serie>> resultado = ss.getOneSerie(UserCacheado.id, idSerie);
            resultado.blockingSubscribe(either -> either
                            .peek(System.out::println)
                            .peekLeft(fail -> System.out.println("ha habido un fallo. \n motivo: " + fail)),
                    throwable -> System.out.println(throwable.getMessage())
            );
        } catch (Exception e) {
            System.out.println("Has escrito un string en vez de un número.");
        }
    }
}
