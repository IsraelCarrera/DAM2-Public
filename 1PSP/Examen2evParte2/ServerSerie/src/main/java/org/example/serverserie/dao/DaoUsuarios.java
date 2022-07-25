package org.example.serverserie.dao;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.example.common.modelo.ApiError;
import org.example.common.modelo.Capitulo;
import org.example.common.modelo.Serie;
import org.example.serverserie.model.UsuarioServer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DaoUsuarios {
    private static final List<UsuarioServer> usuarios = new ArrayList<>();

    static {
        Capitulo c1 = Capitulo.builder().id(0).haSidoVisto(false).nombreCap("Primer capitulo").build();
        Capitulo c2 = Capitulo.builder().id(1).haSidoVisto(false).nombreCap("segundo capitulo").build();
        List<Capitulo> capituloList = new ArrayList<>();
        capituloList.add(c1);
        Serie s1 = Serie.builder().id(0).nombre("primera Serie").capitulos(capituloList).build();
        capituloList.add(c2);
        Serie s2 = Serie.builder().id(1).nombre("segunda Serie").capitulos(capituloList).build();
        List<Serie> serieList = new ArrayList<>();
        serieList.add(s1);
        UsuarioServer u1 = UsuarioServer.builder().id(0).nombre("primerUsuario").passHash("PBKDF2WithHmacSHA512:3072:H+nv5wVX8oLEfW8cMJZRFmeXJQfszs7lPlIcGCiXVTpGz5lbB8V/T4cCfRgMpHTdxLwG9M2+T+uHsEZ02f/lzw==:+BLVCWCZ7DnUzOzszV3isk32awPLpKR2QY2jx4N9LPmSbyb8gwCGy/vD0rmpnnUQZaPNKJoNuUXXy73O+Ywu9A==").tipo("Premium").series(serieList).build();
        serieList.add(s2);
        UsuarioServer u2 = UsuarioServer.builder().id(1).nombre("segundoUsuario").passHash("PBKDF2WithHmacSHA512:3072:H+nv5wVX8oLEfW8cMJZRFmeXJQfszs7lPlIcGCiXVTpGz5lbB8V/T4cCfRgMpHTdxLwG9M2+T+uHsEZ02f/lzw==:+BLVCWCZ7DnUzOzszV3isk32awPLpKR2QY2jx4N9LPmSbyb8gwCGy/vD0rmpnnUQZaPNKJoNuUXXy73O+Ywu9A==").tipo("Basico").series(serieList).build();

        usuarios.add(u1);
        usuarios.add(u2);
    }

    //Se van a añadir a dos usuarios.

    @Inject
    public DaoUsuarios() {
    }

    //ADD Usuario
    public Either<ApiError, UsuarioServer> addUser(UsuarioServer usuario) {
        Either<ApiError, UsuarioServer> resultado;
        usuario.setSeries(new ArrayList<>());
        usuario.setId(usuarios.size());
        if (usuarios.add(usuario)) {
            resultado = Either.right(usuario);
        } else {
            resultado = Either.left(ApiError.builder().mensaje("No se ha podido añadir el usuario.").fecha(LocalDate.now()).build());
        }
        return resultado;
    }

    //Get user.
    public Either<ApiError, UsuarioServer> getUsuario(String nombre) {
        Either<ApiError, UsuarioServer> resultado;
        UsuarioServer u = usuarios.stream().filter(usu -> usu.getNombre().equals(nombre)).findFirst().orElse(null);
        if (u != null) {
            resultado = Either.right(u);
        } else {
            resultado = Either.left(ApiError.builder().mensaje("No se ha encontrado dicho usuario").fecha(LocalDate.now()).build());
        }
        return resultado;
    }

    //Vamos ya con funcionalidad y no registro.

    //Add serie
    public Either<ApiError, Serie> addSerie(Serie serie) {
        Either<ApiError, Serie> resultado;
        //Vamos a coger la ultima serie, cogemos su ID, y posteriormente añadimos uno más a la serie. Por qué? Por que hay un delete. IDEM EN CAPITULOS.
        try {
            if (usuarios.get(serie.getIdUser()).getSeries().isEmpty()) {
                serie.setId(0);
            } else {
                int idSerieAnterior = usuarios.get(serie.getIdUser()).getSeries().stream()
                        .reduce((serie1, serie2) -> serie1.getId() >= serie2.getId() ? serie1 : serie2)
                        .get().getId();
                serie.setId(idSerieAnterior + 1);
            }
            serie.setCapitulos(new ArrayList<>());
            if (usuarios.get(serie.getIdUser()).getSeries().add(serie)) {
                resultado = Either.right(serie);
            } else {
                resultado = Either.left(ApiError.builder().mensaje("No se ha podido añadir la serie.").fecha(LocalDate.now()).build());
            }
        } catch (Exception e) {
            resultado = Either.left(ApiError.builder().mensaje("Error interno.").fecha(LocalDate.now()).build());
        }
        return resultado;
    }

    //Add capitulo
    public Either<ApiError, Capitulo> addCapitulo(Capitulo capitulo) {
        Either<ApiError, Capitulo> resultado;
        //Primero cogemos la serie, para ver su index. Y SABEMOS QUE DEBE EXISTIR
        Serie serieDondeMeterCapitulo = usuarios.get(capitulo.getIdUser()).getSeries().stream()
                .filter(se -> se.getId() == capitulo.getIdSerie()).findFirst().orElse(null);
        if (serieDondeMeterCapitulo != null) {
            int indexSerie = usuarios.get(capitulo.getIdUser()).getSeries().indexOf(serieDondeMeterCapitulo);
            if(usuarios.get(capitulo.getIdUser()).getSeries().get(indexSerie).getCapitulos().isEmpty()){
                capitulo.setId(0);
            }else {
                int idCapituloAnterior = usuarios.get(capitulo.getIdUser()).getSeries().get(indexSerie).getCapitulos().stream()
                        .reduce((capitulo1, capitulo2) -> capitulo1.getId() >= capitulo2.getId() ? capitulo1 : capitulo2)
                        .get().getId();
                capitulo.setId(idCapituloAnterior + 1);
            }
            if (usuarios.get(capitulo.getIdUser()).getSeries().get(indexSerie).getCapitulos().add(capitulo)) {
                resultado = Either.right(capitulo);
            } else {
                resultado = Either.left(ApiError.builder().mensaje("No se ha podido añadir el capitulo.").fecha(LocalDate.now()).build());
            }
        } else {
            resultado = Either.left(ApiError.builder().mensaje("No hay serie con esa id..").fecha(LocalDate.now()).build());

        }
        return resultado;
    }

    //Modificar si se ha visto un capitulo
    public Either<ApiError, Boolean> updateCapitulo(int idUser, int idSerie, int idCapitulo, boolean quepaso) {
        Either<ApiError, Boolean> resultado;
        //Como arriba, tenemos que coger el index del capitulo y de la serie
        try {
            Serie serieDondeMeterCapitulo = usuarios.get(idUser).getSeries().stream()
                    .filter(se -> se.getId() == idSerie).findFirst().orElse(null);
            int indexSerie = usuarios.get(idUser).getSeries().indexOf(serieDondeMeterCapitulo);
            Capitulo capituloDondeModificado = usuarios.get(idUser).getSeries().get(indexSerie).getCapitulos().stream()
                    .filter(cap -> cap.getId() == idCapitulo).findFirst().orElse(null);
            int indexCapitulo = usuarios.get(idUser).getSeries().get(indexSerie).getCapitulos().indexOf(capituloDondeModificado);
            usuarios.get(idUser).getSeries().get(indexSerie).getCapitulos().get(indexCapitulo).setHaSidoVisto(quepaso);
            resultado = Either.right(true);
        } catch (Exception e) {
            resultado = Either.left(ApiError.builder().mensaje("No se ha podido modificar el capitulo.").fecha(LocalDate.now()).build());
        }
        return resultado;
    }

    //Borrar serie
    public Either<ApiError, Boolean> borrarSerie(int idUser, int idSerie) {
        Either<ApiError, Boolean> resultado;
        //Para borrar serie, NO deberá tener capitulos, así que comprobamos si es empty
        try {
            //Lo primero es coger el index de la serie.
            Serie serieBorrar = usuarios.get(idUser).getSeries().stream()
                    .filter(se -> se.getId() == idSerie).findFirst().orElse(null);
            int indexSerie = usuarios.get(idUser).getSeries().indexOf(serieBorrar);
            if (usuarios.get(idUser).getSeries().get(indexSerie).getCapitulos().isEmpty()) {
                //Aqui ya lo borramos, para ello, primero cogemos la serie o el index del que queremos borrar.
                usuarios.get(idUser).getSeries().remove(indexSerie);
                resultado = Either.right(true);
            } else {
                resultado = Either.left(ApiError.builder().mensaje("La serie que desea borrar tiene capitulos").fecha(LocalDate.now()).build());
            }
        } catch (Exception e) {
            resultado = Either.left(ApiError.builder().mensaje("No se ha podido borrar la serie.").fecha(LocalDate.now()).build());
        }
        return resultado;
    }

    //Borrar capitulo
    public Either<ApiError, Boolean> borrarCapitulo(int idUser, int idSerie, int idCapitulo) {
        Either<ApiError, Boolean> resultado;
        //Como arriba, tenemos que coger el index del capitulo y de la serie y luego comprobamos si ha sido vista.
        try {
            Serie serieDondeMeterCapitulo = usuarios.get(idUser).getSeries().stream()
                    .filter(se -> se.getId() == idSerie).findFirst().orElse(null);
            int indexSerie = usuarios.get(idUser).getSeries().indexOf(serieDondeMeterCapitulo);
            Capitulo capituloDondeModificado = usuarios.get(idUser).getSeries().get(indexSerie).getCapitulos().stream()
                    .filter(cap -> cap.getId() == idCapitulo).findFirst().orElse(null);
            int indexCapitulo = usuarios.get(idUser).getSeries().get(indexSerie).getCapitulos().indexOf(capituloDondeModificado);
            if (!usuarios.get(idUser).getSeries().get(indexSerie).getCapitulos().get(indexCapitulo).isHaSidoVisto()) {
                //Aqui se podría borrar
                usuarios.get(idUser).getSeries().get(indexSerie).getCapitulos().remove(indexCapitulo);
                resultado = Either.right(true);
            } else {
                //Aqui si la ha visto ergo no se puede borrar.
                resultado = Either.left(ApiError.builder().mensaje("El capitulo ha sido visto, así que no se puede borrar.").fecha(LocalDate.now()).build());
            }
        } catch (Exception e) {
            resultado = Either.left(ApiError.builder().mensaje("No se ha podido borrar el capitulo.").fecha(LocalDate.now()).build());
        }
        return resultado;
    }

    //Get una serie con too
    public Either<ApiError, Serie> getOneSerie(int idUser, int idSerie) {
        Either<ApiError, Serie> resultado;
        try {
            Serie serie = usuarios.get(idUser).getSeries().stream()
                    .filter(se -> se.getId() == idSerie).findFirst().orElse(null);
            if (serie != null) {
                resultado = Either.right(serie);
            } else {
                resultado = Either.left(ApiError.builder().mensaje("La serie no existe.").fecha(LocalDate.now()).build());
            }
        } catch (Exception e) {
            resultado = Either.left(ApiError.builder().mensaje("No hay usuario con dicha ID.").fecha(LocalDate.now()).build());
        }
        return resultado;
    }

    //Get all series, pero sin capitulos.
    public Either<ApiError, List<Serie>> getAllSeries(int idUser) {
        Either<ApiError, List<Serie>> resultado;
        try {
            List<Serie> series = usuarios.get(idUser).getSeries();
            if (!series.isEmpty()) {
                resultado = Either.right(series);
            } else {
                resultado = Either.left(ApiError.builder().mensaje("Ahora mismo no hay series. ¡Agrega una!").fecha(LocalDate.now()).build());
            }
        } catch (Exception e) {
            resultado = Either.left(ApiError.builder().mensaje("No hay usuario con dicha ID.").fecha(LocalDate.now()).build());
        }
        return resultado;
    }
}
