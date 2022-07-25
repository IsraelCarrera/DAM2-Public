package dao;

import dao.utils.Constantes;
import error.ApiError;
import io.vavr.control.Either;
import modelo.EstadoCivil;
import modelo.Persona;
import modelo.Sexo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DaoPersonas {
    private static final List<Persona> personas = new ArrayList<>();

    static {
        Persona p2 = new Persona("2", "Lucas García", EstadoCivil.Casado.name(), Sexo.Hombre.name(), LocalDate.of(1975, 12, 21), "Nashville");
        Persona p3 = new Persona("3", "Lucía Menendez", EstadoCivil.Casada.name(), Sexo.Mujer.name(), LocalDate.of(1974, 1, 1), "Nashville");
        Persona p5 = new Persona("5", "Patricia Alvarez", EstadoCivil.Casada.name(), Sexo.Mujer.name(), LocalDate.of(1985, 2, 10), "Nashville");
        Persona p7 = new Persona("7", "Alberto Soroya", EstadoCivil.Casado.name(), Sexo.Hombre.name(), LocalDate.of(1977, 3, 21), "Nashville");
        Persona p9 = new Persona("9", "Yolanda Grillo", EstadoCivil.Casada.name(), Sexo.Mujer.name(), LocalDate.of(1987, 4, 30), "Nashville");
        Persona p12 = new Persona("12", "Sergio Pérez", EstadoCivil.Casado.name(), Sexo.Hombre.name(), LocalDate.of(1989, 5, 2), "Nashville");
        Persona p14 = new Persona("14", "Helena Métral", EstadoCivil.Soltera.name(), Sexo.Mujer.name(), LocalDate.of(1990, 6, 13), "Nashville");
        Persona p4 = new Persona("4", "Rocío Mortadelo", EstadoCivil.Soltera.name(), Sexo.Mujer.name(), LocalDate.of(1991, 7, 24), "Nashville");
        Persona p8 = new Persona("8", "Lucas Martínez", EstadoCivil.Soltero.name(), Sexo.Hombre.name(), LocalDate.of(1992, 11, 3), "Nashville");
        Persona p10 = new Persona("10", "Pepe Solar", EstadoCivil.Soltero.name(), Sexo.Hombre.name(), LocalDate.of(1993, 8, 14), "Nashville");
        Persona p1 = new Persona("1", "Ramón Salazar", EstadoCivil.Soltero.name(), Sexo.Hombre.name(), LocalDate.of(1994, 9, 25), "Madrid");
        Persona p6 = new Persona("6", "Susana Gutierrez", EstadoCivil.Soltera.name(), Sexo.Mujer.name(), LocalDate.of(1995, 10, 6), "Barcelona");
        Persona p13 = new Persona("13", "Oscar Ander", EstadoCivil.Soltero.name(), Sexo.Hombre.name(), LocalDate.of(1996, 1, 17), "Nashville");
        Persona p11 = new Persona("11", "Martina Mar", EstadoCivil.Soltera.name(), Sexo.Mujer.name(), LocalDate.of(1997, 2, 28), "Logroño");
        Persona p15 = new Persona("15", "Hernan Suíz", EstadoCivil.Viudo.name(), Sexo.Hombre.name(), LocalDate.of(1945, 12, 9), "Nashville");
        //Pareja 1
        p2.setIdPareja("3");
        p3.setIdPareja("2");
        p2.getHijos().add(p14);
        p2.getHijos().add(p4);
        p3.getHijos().add(p14);
        p3.getHijos().add(p4);
        //Pareja 2
        p5.setIdPareja("7");
        p7.setIdPareja("5");
        p5.getHijos().add(p8);
        p7.getHijos().add(p8);
        //Pareja 3
        p9.setIdPareja("12");
        p12.setIdPareja("9");
        p9.getHijos().add(p10);
        p12.getHijos().add(p10);
        p15.getHijos().add(p13);
        //Metemos todos ya en el DAO.
        personas.add(p1);
        personas.add(p2);
        personas.add(p3);
        personas.add(p4);
        personas.add(p5);
        personas.add(p6);
        personas.add(p7);
        personas.add(p8);
        personas.add(p9);
        personas.add(p10);
        personas.add(p11);
        personas.add(p12);
        personas.add(p13);
        personas.add(p14);
        personas.add(p15);
    }

    public DaoPersonas() {
    }

    //Todas las de APIPersona.
    public Either<ApiError, List<Persona>> getAll() {
        Either<ApiError, List<Persona>> resultado;
        if (!personas.isEmpty()) {
            resultado = Either.right(personas);
        } else {
            resultado = Either.left(new ApiError(Constantes.LISTA_DE_PERSONAS_VACIA, LocalDate.now()));
        }
        return resultado;
    }

    public Either<ApiError, Persona> getOnePersona(String id) {
        Either<ApiError, Persona> resultado;
        Persona p = personas.stream().filter(persona -> persona.getId().equals(id))
                .findFirst().orElse(null);
        if (p != null) {
            resultado = Either.right(p);
        } else {
            resultado = Either.left(new ApiError(Constantes.NO_SE_HA_ENCONTRADO_PERSONA_CON_DICHA_ID, LocalDate.now()));
        }
        return resultado;
    }

    public Either<ApiError, Persona> addPersona(Persona p) {
        Either<ApiError, Persona> resultado;
        Persona pers = personas.stream().
                reduce(((persona1, persona2) -> Integer.parseInt(persona1.getId()) >= Integer.parseInt(persona2.getId()) ? persona1 : persona2))
                .get();
        p.setId(String.valueOf(Integer.parseInt(pers.getId()) + 1));
        if (p.getSexo().equals(Sexo.Hombre.name())) {
            p.setEstadoCivil(EstadoCivil.Soltero.name());
        } else {
            p.setEstadoCivil(EstadoCivil.Soltera.name());
        }
        p.setHijos(new ArrayList<>());
        boolean add = personas.add(p);
        if (add) {
            resultado = Either.right(p);
        } else {
            resultado = Either.left(new ApiError(Constantes.NO_SE_HA_PODIDO_AGREGAR_A_LA_PERSONA_INDICADA, LocalDate.now()));
        }
        return resultado;
    }

    public boolean deletePersona(String id) {
        return personas.removeIf(p -> p.getId().equals(id));
    }

    public Either<ApiError, Persona> updatePersona(Persona p) {
        Either<ApiError, Persona> resultado;
        int indice = personas.indexOf(p);
        if (indice != -1) {
            personas.set(indice, p);
            resultado = Either.right(p);
        } else {
            resultado = Either.left(new ApiError(Constantes.NO_SE_HA_ENCONTRADO_PERSONA, LocalDate.now()));
        }
        return resultado;
    }

    //DE lo api de casamientos

    public Either<ApiError, List<Persona>> getPersonaPorSexo(String sexo) {
        Either<ApiError, List<Persona>> resultado;
        List<Persona> per = null;
        if (sexo.equals(Sexo.Hombre.name())) {
            per = personas.stream().filter(persona -> persona.getSexo().equalsIgnoreCase(sexo))
                    .filter(persona -> persona.getEstadoCivil().equals(EstadoCivil.Soltero.name()))
                    .collect(Collectors.toList());
        } else {
            per = personas.stream().filter(persona -> persona.getSexo().equalsIgnoreCase(sexo))
                    .filter(persona -> persona.getEstadoCivil().equals(EstadoCivil.Soltera.name()))
                    .collect(Collectors.toList());
        }
        if (!per.isEmpty()) {
            resultado = Either.right(per);
        } else {
            resultado = Either.left(new ApiError(Constantes.NO_SE_HA_ENCONTRADO_PERSONAS_CON_DICHO_SEXO, LocalDate.now()));
        }
        return resultado;
    }

    //Lanzo excepción aquí, para que si hay algun fallo lo recoja el Service y poner el boolean en false.
    public void cambiarPareja(String idPersona, String idPareja) throws Exception {
        Persona p = getOnePersona(idPersona).get();
        p.setIdPareja(idPareja);
        if (p.getSexo().equals(Sexo.Hombre.name())) {
            p.setEstadoCivil(EstadoCivil.Casado.name());
        } else {
            p.setEstadoCivil(EstadoCivil.Casada.name());
        }
        personas.set(personas.indexOf(p), p);
    }

    public boolean comprobarQueSonPareja(String idPersona, String idPareja) throws Exception {
        boolean sonPareja = false;
        Persona p = getOnePersona(idPersona).get();
        if (p.getIdPareja().equals(idPareja)) {
            sonPareja = true;
        }
        return sonPareja;
    }

    public void registrarBebe(String idHombre, String idMujer, Persona bebe) throws Exception {
        Either<ApiError, Persona> add = addPersona(bebe);
        if (add.isRight()) {
            personas.get(personas.indexOf(personas.stream().filter(p -> p.getId().equals(idHombre)).findFirst().get())).getHijos().add(bebe);
            personas.get(personas.indexOf(personas.stream().filter(p -> p.getId().equals(idMujer)).findFirst().get())).getHijos().add(bebe);
        }
    }

    public boolean esHijoDe(String idPersona) {
        boolean tieneHijos = false;
        //TODO CAMBIAR no me da tiempo.
        for (Persona per : personas) {
            for (Persona hijos : per.getHijos()) {
                if (hijos.getId().equals(idPersona)) {
                    tieneHijos = true;
                    break;
                }
            }
        }
        return tieneHijos;
    }

    public void registrarDefuncion(String idPersona) throws Exception {
        Persona p = getOnePersona(idPersona).get();
        //Comprobamos si está casad@. Si lo está, cogemos a la pareja y la modificamos. Teniendo ya un update, como que paso de hacer código again. Lanzo Excepción por si hay fallo, que lo recoja services.
        if (p.getEstadoCivil().equals(EstadoCivil.Casada.name()) || p.getEstadoCivil().equals(EstadoCivil.Casado.name())) {
            Persona pareja = getOnePersona(p.getIdPareja()).get();
            if (pareja.getSexo().equals(Sexo.Hombre.name())) {
                pareja.setEstadoCivil(EstadoCivil.Viudo.name());
            } else {
                pareja.setEstadoCivil(EstadoCivil.Viuda.name());
            }
            //Vamos a poner el ID de la pareja a null, ya que claro, ya no tiene pareja. Quizá quiera volver a casarse otra vez, aunque siendo ultraconservadores... Eso ya, valóralo tú.
            pareja.setIdPareja(null);
            updatePersona(pareja);
        }
        deletePersona(idPersona);
    }

    public Either<ApiError, List<Persona>> getConFiltros(String lugarProcedencia, int anoNacimiento, int nHijos, String estadoCivil) {
        //Cosas que tendré en cuenta. Si los numeros son -1, es que estaban vacios.
        Either<ApiError, List<Persona>> resultado;
        //Empezamos desde uno hasta otro. Miramos si es Empty, si lo es, no se filtra en base a eso. Así que creo una List con todos ellos y vamos modificandolo.
        List<Persona> personasFiltradas = personas;
        if (!lugarProcedencia.isEmpty()) {
            personasFiltradas = personasFiltradas.stream().filter(p -> p.getLugarProcedencia().contains(lugarProcedencia)).collect(Collectors.toList());
        }
        if (anoNacimiento != -1) {
            personasFiltradas = personasFiltradas.stream().filter(p -> p.getFechaNacimiento().getYear() == anoNacimiento).collect(Collectors.toList());
        }
        if (nHijos != -1) {
            personasFiltradas = personasFiltradas.stream().filter(p -> p.getHijos().size() == nHijos).collect(Collectors.toList());
        }
        if (!estadoCivil.isEmpty()) {
            personasFiltradas = personasFiltradas.stream().filter(p -> p.getEstadoCivil().contains(estadoCivil)).collect(Collectors.toList());
        }
        //Ahora comprobamos, si hay lista vacia, mandamos ApiError.
        if (!personasFiltradas.isEmpty()) {
            resultado = Either.right(personasFiltradas);
        } else {
            resultado = Either.left(new ApiError(Constantes.NO_HAY_PERSONAS_CON_ESE_TIPO_DE_CARACTERISTICAS, LocalDate.now()));
        }
        return resultado;
    }
}
