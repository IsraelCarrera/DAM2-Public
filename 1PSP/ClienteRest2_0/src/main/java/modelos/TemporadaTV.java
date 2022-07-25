package modelos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Data
public class TemporadaTV {
    private String name;
    private int seasonNumber;
    private LocalDate airDate;
    private List<Episodio> episodes;


    @Override
    public String toString() {
        return seasonNumber + " " + name + " fecha de emisión: " + airDate;
    }
}
