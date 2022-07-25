package servicios;

import dao.DAOPeli;
import io.vavr.control.Either;
import modelos.CreditosPelicula;
import modelos.Pelicula;

import javax.inject.Inject;
import java.util.List;

public class PeliculasServicios {
    DAOPeli dao;

    @Inject
    public PeliculasServicios(DAOPeli dao) {
        this.dao = dao;
    }

    public Either<String, Pelicula> peliculaPorId(int id) {
        return dao.getPeliculaId(id);
    }

    public Either<String, List<Pelicula>> peliculasPopulares(int pag) {
        return dao.peliculasPopulares(pag);
    }

    public Either<String, List<Pelicula>> coleccionPelis(int id) {
        return dao.getColecciones(id);
    }

    public Either<String, CreditosPelicula> castPeli(int id) {
        return dao.getCastPeli(id);
    }

    public Either<String, List<Pelicula>> buscarPelicula(String name) {
        return dao.buscarPelicula(name);
    }
}
