package modelos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter @Data
public class BuscarSerie {
    private List<Serie> series;
}
