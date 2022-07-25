package modelos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter @Data
public class BuscarPelicula {
    private List<Pelicula> peliculas;
}
