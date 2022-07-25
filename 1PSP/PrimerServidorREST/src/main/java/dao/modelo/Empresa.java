package dao.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Empresa {
    private int id;
    private String cif;
    private String nombre;
    private String direccion;
    private LocalDate fechaCreacion;
    //Nombres de ellos.
    private List<String> trabajadores;
}
