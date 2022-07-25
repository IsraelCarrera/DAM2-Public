package dao;

import EE.errores.ApiError;
import dao.modelo.Trabajador;
import dao.utils.Constantes;
import io.vavr.control.Either;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DaoTrabajador {
    private static final List<Trabajador> trabajadores = new ArrayList<>();

    static {
        trabajadores.add(new Trabajador(1, 2, "123123D", "Juan Pedro Martínez Almeida", LocalDate.of(1985, 12, 1), "Calle dos, 3", "Teleoperador"));
        trabajadores.add(new Trabajador(2, 2, "463465F", "Lucía Soria Perez", LocalDate.of(1875, 11, 20), "Calle Gran Vía de Hortaleza, 23", "Director"));
        trabajadores.add(new Trabajador(3, 2, "156433S", "Lucrecia Gomez Arta", LocalDate.of(1954, 1, 21), "Calle Serrano,1", "Limpiador"));
        trabajadores.add(new Trabajador(4, 2, "4258245F", "Mario Velazquez Venn", LocalDate.of(1965, 7, 14), "Calle Guadalajara, 15", "Asistente"));
        trabajadores.add(new Trabajador(5, 2, "1231S", "Lucás Trello Sol", LocalDate.of(1968, 12, 1), "Calle Torrejón,6", "RecursosHumanos"));
        trabajadores.add(new Trabajador(6, 1, "123123D", "Gonzalo García Gutierrez", LocalDate.of(1952, 11, 25), "Calle Soroya, 12", "Marinero"));
        trabajadores.add(new Trabajador(7, 1, "463465F", "Fernando Petra Seta", LocalDate.of(1978, 10, 30), "Avenida del Mediterráneo, 23", "Director"));
        trabajadores.add(new Trabajador(8, 1, "156433S", "Luz Casal Madariaga", LocalDate.of(1979, 8, 28), "Travesía Hilo, 11", "Comercial"));
        trabajadores.add(new Trabajador(9, 3, "4258245F", "Marta Alonso Pez", LocalDate.of(1985, 12, 7), "Calle fumigadores, 77", "Director"));
        trabajadores.add(new Trabajador(10, 3, "1231S", "Paulo Brail Sed", LocalDate.of(1999, 9, 9), "Calle Francisco Soller, 11", "RecursosHumanos"));
        trabajadores.add(new Trabajador(11, 4, "463465F", "Francisco Montañez Gonzalez", LocalDate.of(1980, 2, 29), "Avenida 2 de mayo, 1", "Jefe"));
        trabajadores.add(new Trabajador(12, 5, "156433S", "María Magdalena Robles", LocalDate.of(1988, 4, 5), "Calle Triángulo, 3", "Jefe"));
        trabajadores.add(new Trabajador(13, 5, "4258245F", "Raul Simón Martinez", LocalDate.of(1990, 3, 17), "Calle catorce, 11", "Cocinero"));
    }

    public DaoTrabajador() {
    }

    public Either<ApiError, List<Trabajador>> getAll() {
        Either<ApiError, List<Trabajador>> resultado;
        if (trabajadores.isEmpty()) {
            resultado = Either.left(new ApiError(Constantes.ERROR_LISTA_DE_TRABAJADORES_VACIA, LocalDate.now()));
        } else {
            resultado = Either.right(trabajadores);
        }
        return resultado;
    }

    public Either<ApiError, Trabajador> getTrabajador(int id) {
        Either<ApiError, Trabajador> resultado;
        Trabajador e = trabajadores.stream()
                .filter(emp -> emp.getId() == id)
                .findFirst().orElse(null);
        if (e == null) {
            resultado = Either.left(new ApiError(Constantes.ERROR_NOTRABAJADOR, LocalDate.now()));
        } else {
            resultado = Either.right(e);
        }
        return resultado;
    }

    public Either<ApiError, List<Trabajador>> getTrabajadorPorEmpresa(int idEmpresa) {
        Either<ApiError, List<Trabajador>> resultado;
        List<Trabajador> list = trabajadores.stream().filter(tra -> tra.getIdEmpresa() == idEmpresa)
                .collect(Collectors.toList());
        if (!list.isEmpty()) {
            resultado = Either.right(list);
        } else {
            resultado = Either.left(new ApiError(Constantes.ERROR_NOTRABAJADORES_EMPRESA, LocalDate.now()));
        }
        return resultado;
    }

    public Trabajador addTrabajador(Trabajador t) {
        t.setId(trabajadores.size() + 1);
        trabajadores.add(t);
        return t;
    }

    public boolean borrarTrabajador(int id) {
        return trabajadores.remove(trabajadores.stream()
                .filter(em -> em.getId() == id)
                .findFirst().orElse(null));
    }

    public Either<ApiError, Trabajador> updateTrabajador(Trabajador e) {
        Either<ApiError, Trabajador> resultado;
        if (trabajadores.stream().filter(tra -> tra.getId() == e.getId()).findFirst().orElse(null) != null) {
            trabajadores.set((e.getId() - 1), e);
            resultado = Either.right(e);
        } else {
            resultado = Either.left(new ApiError(Constantes.ERROR_TRABAJADOR_DATOS, LocalDate.now()));
        }
        return resultado;
    }
}
