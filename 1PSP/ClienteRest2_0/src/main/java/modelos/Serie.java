package modelos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Data
public class Serie {
    private int id;
    private String name;
    private String originalName;
    private String originalLanguage;
    private List<TemporadaTV> seasons;
    private int numberOfEpisodes;
    private int numberOfSeasons;
    private String status;
    private String homepage;
    private List<Compania> productionCompanies;
    private List<Persona> createdBy;
    private List<Genero> genres;
    private List<Networks> networks;
    private String overview;
    private String posterPath;

    @Override
    public String toString() {
        String s = id + " " + name + " " + originalName + " " + originalLanguage;
        if (numberOfSeasons != 0) {
            s += " temporadas:" + numberOfSeasons;
        }
        if (numberOfEpisodes != 0) {
            s += " episodios:" + numberOfEpisodes;
        }
        return s;
    }
}
