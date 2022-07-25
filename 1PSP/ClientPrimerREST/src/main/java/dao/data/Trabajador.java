package dao.data;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class Trabajador {

    @SerializedName("puestoTrabajo")
    private String puestoTrabajo;

    @SerializedName("fechaNacimiento")
    private LocalDate fechaNacimiento;

    @SerializedName("direccion")
    private String direccion;

    @SerializedName("idEmpresa")
    private int idEmpresa;

    @SerializedName("id")
    private int id;

    @SerializedName("nombre")
    private String nombre;

    @SerializedName("dni")
    private String dni;

    public Trabajador(String dni, String nombre, String direccion, LocalDate fechaNacimiento, String puestoTrabajo, int idEmpresa) {
        this.dni = dni;
        this.nombre = nombre;
        this.direccion = direccion;
        this.fechaNacimiento = fechaNacimiento;
        this.puestoTrabajo = puestoTrabajo;
        this.idEmpresa = idEmpresa;
    }

    @Override
    public String toString() {
        return " id:" + id +
                ", dni: " + dni +
                ", nombre='" + nombre +
                "puestoTrabajo: " + puestoTrabajo +
                ", fechaNacimiento: " + fechaNacimiento +
                ", direccion: " + direccion;
    }
}