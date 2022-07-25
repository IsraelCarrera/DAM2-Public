package org.example.clientbasket.dao;

import com.google.gson.Gson;
import io.vavr.control.Either;
import org.example.clientbasket.dao.retrofit.EquipoAPI;
import org.example.common.modelo.Equipo;

import javax.inject.Inject;
import java.util.List;

public class DaoEquipos extends DaoGenerico {
    private final EquipoAPI equipoAPI;

    @Inject
    public DaoEquipos(EquipoAPI equipoAPI, Gson g) {
        super(g);
        this.equipoAPI = equipoAPI;
    }

    public Either<String, List<Equipo>> getAllEquipos() {
        return this.construirEither(equipoAPI.getAllEquipos());
    }

    public Either<String, Equipo> addEquipo(Equipo equipo) {
        return this.construirEither(equipoAPI.addEquipo(equipo));
    }

    public Either<String, Equipo> deleteEquipo(String nombreEquipo) {
        return this.construirEither(equipoAPI.deleteEquipo(nombreEquipo));
    }

    public Either<String, Equipo> updateEquipo(Equipo equipo, String nombreEquipoNuevo) {
        return this.construirEither(equipoAPI.updateEquipo(equipo, nombreEquipoNuevo));
    }
}
