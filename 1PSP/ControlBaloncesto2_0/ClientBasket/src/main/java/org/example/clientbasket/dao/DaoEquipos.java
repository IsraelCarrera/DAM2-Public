package org.example.clientbasket.dao;

import com.google.gson.Gson;
import io.reactivex.rxjava3.core.Single;
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

    public Single<Either<String, List<Equipo>>> getAllEquipos() {
        return this.construirSingle(equipoAPI.getAllEquipos());
    }

    public Single<Either<String, Equipo>> addEquipo(Equipo equipo) {
        return this.construirSingle(equipoAPI.addEquipo(equipo));
    }

    public Single<Either<String, Equipo>> deleteEquipo(String nombreEquipo) {
        return this.construirSingle(equipoAPI.deleteEquipo(nombreEquipo));
    }

    public Single<Either<String, Equipo>> updateEquipo(Equipo equipo, String nombreEquipoNuevo) {
        return this.construirSingle(equipoAPI.updateEquipo(equipo, nombreEquipoNuevo));
    }
}
