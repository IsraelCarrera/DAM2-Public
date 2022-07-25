package modelos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter @Data
public class Colecciones {
    private int id;
    private String name;
    private List<Pelicula> parts;

    @Override
    public String toString() {
        return id + " " + name + " " + parts;
    }
}
