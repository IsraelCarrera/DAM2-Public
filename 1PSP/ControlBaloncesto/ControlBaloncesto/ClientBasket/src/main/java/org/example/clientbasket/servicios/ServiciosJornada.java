package org.example.clientbasket.servicios;

import io.vavr.control.Either;
import org.example.clientbasket.dao.DaoJornadas;
import org.example.common.modelo.Jornada;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.List;

public class ServiciosJornada {
    private final DaoJornadas dao;

    @Inject
    public ServiciosJornada(DaoJornadas dao) {
        this.dao = dao;
    }

    public Either<String, List<Jornada>> getAllJornadas() {
        return dao.getAllJornadas();
    }

    public Either<String, Jornada> addJornada(Jornada jornada) {
        return dao.addJornada(jornada);
    }

    public Either<String, Integer> deleteJornada(int idJornada) {
        return dao.deleteJornada(idJornada);
    }

    public Either<String, Jornada> updateJornada(Jornada jornada, LocalDate nuevaFecha) {
        return dao.updateJornada(jornada, nuevaFecha);
    }
}
