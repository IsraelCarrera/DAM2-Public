package modelos;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Data
public class Pelicula {
    private int id;
    private String title;
    private String originalTitle;
    private List<Genero> genres;
    private Colecciones belongsToCollection;
    private LocalDate releaseDate;
    private List<Compania> productionCompanies;
    private String overview;
    private String originalLanguage;
    private String posterPath;
    private String homepage;


    @Override
    public String toString() {
        String s = id + " " + title + " " + " " + originalTitle + " ";
        if (genres != null) {
            s += genres + " ";
        }
        if (belongsToCollection != null) {
            s += belongsToCollection.getName() + " ";
        }
        s = s + releaseDate + " ";
        if (productionCompanies != null) {
            s += productionCompanies;
        }
        return s;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pelicula pelicula = (Pelicula) o;
        return id == pelicula.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
