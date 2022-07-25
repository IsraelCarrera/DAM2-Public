package dao.modelo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Trabajador {
    private int id;
    private int idEmpresa;
    private String dni;
    private String nombre;
    private LocalDate fechaNacimiento;
    private String direccion;
    private String puestoTrabajo;
}
