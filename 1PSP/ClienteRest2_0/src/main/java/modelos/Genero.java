package modelos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class Genero {
    private String name;

    @Override
    public String toString() {
        return name;
    }
}
