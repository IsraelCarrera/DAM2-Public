package org.example.serverbasket.servicios;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.example.common.error.ApiError;
import org.example.common.modelo.Equipo;
import org.example.serverbasket.dao.DaoEquipo;

import java.util.List;

public class ServiciosEquipo {
    private final DaoEquipo dao;

    @Inject
    public ServiciosEquipo(DaoEquipo dao) {
        this.dao = dao;
    }

    public Either<ApiError, List<Equipo>> getAllEquipos(){
        return dao.getAllEquipos();
    }

    public Either<ApiError,Equipo> addEquipo(Equipo equipo){
        return dao.addEquipo(equipo);
    }

    public Either<ApiError,Equipo> deleteEquipo(Equipo equipo){
        return dao.deleteEquipo(equipo);
    }

    public Either<ApiError,Equipo> updateEquipo(Equipo equipo, String nombreEquipoNuevo){
        return dao.updateEquipo(equipo, nombreEquipoNuevo);
    }
}
