package dao;

import io.vavr.control.Either;
import modelos.CreditosPelicula;
import modelos.Pelicula;

import java.util.List;

public interface DAOPeli {
    Either<String, Pelicula> getPeliculaId(int id);

    Either<String, List<Pelicula>> peliculasPopulares(int pag);

    Either<String, List<Pelicula>> getColecciones(int id);

    Either<String, CreditosPelicula> getCastPeli(int id);

    Either<String, List<Pelicula>> buscarPelicula(String name);
}
