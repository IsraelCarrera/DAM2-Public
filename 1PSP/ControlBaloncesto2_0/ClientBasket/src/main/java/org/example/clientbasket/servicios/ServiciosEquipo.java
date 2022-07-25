package org.example.clientbasket.servicios;


import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import org.example.clientbasket.dao.DaoEquipos;
import org.example.common.modelo.Equipo;

import javax.inject.Inject;
import java.util.List;

public class ServiciosEquipo {
    private final DaoEquipos dao;

    @Inject
    public ServiciosEquipo(DaoEquipos dao) {
        this.dao = dao;
    }

    public Single<Either<String, List<Equipo>>> getAllEquipos() {
        return dao.getAllEquipos();
    }

    public Single<Either<String, Equipo>> addEquipo(Equipo equipo) {
        return dao.addEquipo(equipo);
    }

    public Single<Either<String, Equipo>> deleteEquipo(String nombreEquipo) {
        return dao.deleteEquipo(nombreEquipo);
    }

    public Single<Either<String, Equipo>> updateEquipo(Equipo equipo, String nombreEquipoNuevo) {
        return dao.updateEquipo(equipo, nombreEquipoNuevo);
    }
}
