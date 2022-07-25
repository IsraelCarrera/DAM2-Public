package utils;

import dao.data.*;
import modelos.*;
import org.modelmapper.ModelMapper;


public class ProducesMapper {
    private final ModelMapper modelMapper;

    public ProducesMapper() {
        modelMapper = new ModelMapper();
    }

    //Personas
    public Persona mapearPersona(PersonaData p) {
        return modelMapper.map(p, Persona.class);
    }

    public BuscarPersona mapearListPersonas(BuscarPersonaData p) {
        return modelMapper.typeMap(BuscarPersonaData.class, modelos.BuscarPersona.class)
                .addMapping(BuscarPersonaData::getResults, modelos.BuscarPersona::setPersonas)
                .map(p);
    }

    //Series
    public Serie mapearSerie(SerieTvData s) {
        return modelMapper.map(s, Serie.class);
    }

    public TemporadaTV mapearTemporada(TemporadaTVData t) {
        return modelMapper.map(t, TemporadaTV.class);
    }

    public CreditosEpisodio mapearCreditosEpisodio(CreditosEpisodioData c) {
        return modelMapper.typeMap(CreditosEpisodioData.class, CreditosEpisodio.class)
                .addMapping(CreditosEpisodioData::getCast, CreditosEpisodio::setCast)
                .addMapping(CreditosEpisodioData::getGuestStars, CreditosEpisodio::setGuestStars)
                .map(c);
    }

    public BuscarSerie mapearListSerie(BuscarSerieData s) {
        return modelMapper.typeMap(BuscarSerieData.class, BuscarSerie.class)
                .addMapping(BuscarSerieData::getResults, BuscarSerie::setSeries)
                .map(s);

    }

    //Pelis
    public Pelicula mapearPeli(PeliculaData p) {
        return modelMapper.map(p, Pelicula.class);
    }

    public BuscarPelicula mapearListPeliculasPopulares(PeliculasPopulares p) {
        return modelMapper.typeMap(PeliculasPopulares.class, BuscarPelicula.class)
                .addMapping(PeliculasPopulares::getResults, BuscarPelicula::setPeliculas)
                .map(p);
    }

    public Colecciones mapearListPeliculasColecciones(ColeccionesData c) {
        return modelMapper.map(c, Colecciones.class);
    }

    public CreditosPelicula mapearPeliculaCreditos(CastPeliculaData c) {
        return modelMapper.typeMap(CastPeliculaData.class, CreditosPelicula.class)
                .addMapping(CastPeliculaData::getCast, CreditosPelicula::setCast)
                .map(c);
    }

    public BuscarPelicula mapearListPeliculas(BuscarPeliculaData p) {
        return modelMapper.typeMap(BuscarPeliculaData.class, BuscarPelicula.class)
                .addMapping(BuscarPeliculaData::getResults, BuscarPelicula::setPeliculas)
                .map(p);
    }
}
