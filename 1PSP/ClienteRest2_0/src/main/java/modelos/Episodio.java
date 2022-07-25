package modelos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Data
public class Episodio {
    private String name;
    private int seasonNumber;
    private int episodeNumber;
    private LocalDate airDate;
    private String overview;


    @Override
    public String toString() {
        String s = seasonNumber + "x" + episodeNumber + " " + name + " ";
        if (airDate != null) {
            s += "fecha de emisión: " + airDate;
        }
        return s;
    }
}
