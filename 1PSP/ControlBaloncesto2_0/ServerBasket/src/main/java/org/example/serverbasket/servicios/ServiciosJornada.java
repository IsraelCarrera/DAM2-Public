package org.example.serverbasket.servicios;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.example.common.error.ApiError;
import org.example.common.modelo.Jornada;
import org.example.serverbasket.dao.DaoJornada;

import java.time.LocalDate;
import java.util.List;

public class ServiciosJornada {
    private final DaoJornada dao;

    @Inject
    public ServiciosJornada(DaoJornada dao) {
        this.dao = dao;
    }

    public Either<ApiError, List<Jornada>> getAllJornadas(){
        return dao.getAllJornadas();
    }

    public  Either<ApiError,Jornada> addJornada(Jornada jornada){
        return dao.addJornada(jornada);
    }

    public  Either<ApiError,Integer> deleteJornada(int idJornada){
        return dao.deleteJornada(idJornada);
    }

    public  Either<ApiError,Jornada> updateJornada(Jornada jornada, LocalDate nuevaFecha){
        return dao.updateJornada(jornada, nuevaFecha);
    }
}
