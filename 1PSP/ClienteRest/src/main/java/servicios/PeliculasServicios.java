package servicios;

import dao.DaoPelicula;
import dao.modelos.*;
import io.vavr.control.Either;

import java.util.List;

public class PeliculasServicios {

    public Either<String, Pelicula> peliculaPorId(int id) {
        DaoPelicula dao = new DaoPelicula();
        return dao.getPeliculaId(id);
    }

    public Either<String, PeliculasPopulares> peliculasPopulares(int pag) {
        DaoPelicula dao = new DaoPelicula();
        return dao.peliculasPopulares(pag);
    }

    public Either<String,List<Pelicula>>  coleccionPelis(int id) {
        DaoPelicula dao = new DaoPelicula();
        return dao.getColecciones(id);
    }

    public Either <String,CastPelicula> castPeli(int id) {
        DaoPelicula dao = new DaoPelicula();
        return dao.getCastPeli(id);
    }

    public Either <String,List<PeliculaSacadaDeBuscar>> buscarPelicula(String name) {
        DaoPelicula dao = new DaoPelicula();
        return dao.buscarPelicula(name);
    }
}
