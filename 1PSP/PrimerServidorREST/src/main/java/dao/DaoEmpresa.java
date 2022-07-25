package dao;

import EE.errores.ApiError;
import dao.modelo.Empresa;
import dao.utils.Constantes;
import io.vavr.control.Either;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DaoEmpresa {
    private static final List<Empresa> empresas = new ArrayList<>();

    static {

        empresas.add(new Empresa(1, "45584512S", "García y amigos", "Calle García noblejas, 12", LocalDate.of(1993, 12, 1), new ArrayList<>(Arrays.asList("Gonzalo García Gutierrez", "Fernando Petra Seta", "Luz Casal Madariaga"))));
        empresas.add(new Empresa(2, "15677456B", "Sophre", "Calle Francisco de Vitoria, 523", LocalDate.of(2019, 10, 13), new ArrayList<>(Arrays.asList("Juan Pedro Martínez Almeida", "Lucía Soria Perez", "Lucrecia Gomez Arta", "Mario Velazquez Venn", "Lucás Trello Sol"))));
        empresas.add(new Empresa(3, "12133454Q", "PeliculasPelis S.L", "Calle Gran Via, 1", LocalDate.of(2000, 1, 1), new ArrayList<>(Arrays.asList("Marta Alonso Pez", "Paulo Brail Sed"))));
        empresas.add(new Empresa(4, "15671231T", "Fontanería Paco", "Calle Alcalá, 520", LocalDate.of(2010, 8, 15), new ArrayList<>(Arrays.asList("Francisco Montañez Gonzalez"))));
        empresas.add(new Empresa(5, "12133110P", "Panadería Panta", "Calle Quevedo, 3", LocalDate.of(1900, 1, 1), new ArrayList<>(Arrays.asList("María Magdalena Robles", "Raul Simón Martinez"))));
    }

    public DaoEmpresa() {
    }

    public Either<ApiError, Empresa> getEmpresa(int id) {
        Either<ApiError, Empresa> resultado;
        Empresa e = empresas.stream()
                .filter(emp -> emp.getId() == id)
                .findFirst().orElse(null);
        if (e == null) {
            resultado = Either.left(new ApiError(Constantes.ERROR_NOEMPRESA, LocalDate.now()));
        } else {
            resultado = Either.right(e);
        }
        return resultado;
    }

    public Either<ApiError, List<Empresa>> getAll() {
        Either<ApiError, List<Empresa>> resultado;
        if (empresas.isEmpty()) {
            resultado = Either.left(new ApiError(Constantes.ERROR_LISTA_EMPRESAS_VACIA, LocalDate.now()));
        } else {
            resultado = Either.right(empresas);
        }
        return resultado;
    }

    public Empresa addEmpresa(Empresa e) {
        e.setId(empresas.size() + 1);
        empresas.add(e);
        return e;
    }

    public boolean borrarEmpresa(int id) {
        return empresas.remove(empresas.stream()
                .filter(em -> em.getId() == id)
                .findFirst().orElse(null));
    }

    public Either<ApiError, Empresa> updateEmpresa(Empresa e) {
        Either<ApiError, Empresa> resultado;
        if (empresas.stream().filter(emp -> emp.getId() == e.getId()).findFirst().orElse(null) != null) {
            empresas.set((e.getId() - 1), e);
            resultado = Either.right(e);
        } else {
            resultado = Either.left(new ApiError(Constantes.ERROR_EMPRESA_DATOS, LocalDate.now()));
        }
        return resultado;
    }

    public void meterTrabajadorEnEmpresa(int id, String nombre) {
        empresas.get(id - 1).getTrabajadores().add(nombre);
    }

    public void deleteTrabajadorEmpresa(int id, String nombre) {
        empresas.get(id - 1).getTrabajadores().remove(nombre);
    }

    public void updateTrabajadorEmpresa(int id, String nombreNuevo, String nombreViejo) {
        //No tengo tiempo para buscar como coger el index de un array de string. Busqué y me estaba dando bastantes fallos,
        // y como me está comiendo el tiempo... Si encuentro algun modo lo modifico.
        int index = -1;
        for (int i = 0; i < empresas.get(id - 1).getTrabajadores().size(); i++) {
            if (empresas.get(id - 1).getTrabajadores().get(i).equals(nombreViejo)) {
                index = i;
            }
        }
        if (index != -1) {
            empresas.get(id - 1).getTrabajadores().set(index, nombreNuevo);
        }
    }
}
