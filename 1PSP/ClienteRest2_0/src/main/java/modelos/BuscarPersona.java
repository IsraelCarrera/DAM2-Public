package modelos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@Data
public class BuscarPersona {
    private List<Persona> personas;
}
