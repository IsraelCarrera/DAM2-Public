package modelo;

import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Persona {
    String id;
    String nombre;
    String estadoCivil;
    String sexo;
    LocalDate fechaNacimiento;
    String lugarProcedencia;
    String idPareja;
    List<Persona> hijos;

    //Quiero hacer yo mismo los constructores para poder diferenciar entre cuando es nacimiento, y cuando vienen de otro lado.


    //Constructor del que viene.
    public Persona(String nombre, String sexo, LocalDate fechaNacimiento, String lugarProcedencia) {
        this.id = null; //Se la ponen en el servidor.
        this.nombre = nombre;
        this.estadoCivil = "Soltero";
        this.sexo = sexo;
        this.fechaNacimiento = fechaNacimiento;
        this.lugarProcedencia = lugarProcedencia;
        this.idPareja = null; //Está soltero.
        this.hijos = new ArrayList<>();
    }

    //Constructor del que nace aquí
    public Persona(String nombre, String sexo, LocalDate fechaNacimiento) {
        this.id = null; //Se la ponen en el servidor
        this.nombre = nombre;
        this.estadoCivil = "Soltero";
        this.sexo = sexo;
        this.fechaNacimiento = fechaNacimiento;
        this.lugarProcedencia = "Nashville";
        this.idPareja = null; //Un hijo no nace con pareja, por muy ultraconservadores que sean.
        this.hijos = new ArrayList<>();
    }

    //Constructor para el servidor. Para tener varias personas en el poblado. La lista de hijos se añade a mano ahí, igual que la de pareja.
    public Persona(String id, String nombre, String estadoCivil, String sexo, LocalDate fechaNacimiento, String lugarProcedencia) {
        this.id = id;
        this.nombre = nombre;
        this.estadoCivil = estadoCivil;
        this.sexo = sexo;
        this.fechaNacimiento = fechaNacimiento;
        this.lugarProcedencia = lugarProcedencia;
        this.idPareja = null;
        this.hijos = new ArrayList<>();
    }

    //Una persona es igual si tiene el mismo ID.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Persona persona = (Persona) o;
        return Objects.equals(id, persona.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
