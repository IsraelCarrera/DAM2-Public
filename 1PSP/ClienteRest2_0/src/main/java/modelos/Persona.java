package modelos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Data
@Setter
public class Persona {
    private int id;
    private String name;
    private String biography;
    private LocalDate birthday;
    private String placeOfBirth;
    private LocalDate deathday;
    private List<String> alsoKnownAs;
    private String profilePath;
    private String homepage;


    @Override
    public String toString() {
        String s = id + " " + name + " ";
        if (biography != null) {
            s += biography + " ";
        }
        if (birthday != null) {
            s += birthday + " ";
        }
        if (placeOfBirth != null) {
            s += placeOfBirth + " ";
        }
        if (deathday != null) {
            s += deathday + " ";
        }
        return s;
    }
}
