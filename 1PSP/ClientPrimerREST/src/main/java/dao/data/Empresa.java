package dao.data;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class Empresa {

    @SerializedName("cif")
    private String cif;

    @SerializedName("trabajadores")
    private List<String> trabajadores;

    @SerializedName("direccion")
    private String direccion;

    @SerializedName("fechaCreacion")
    private LocalDate fechaCreacion;

    @SerializedName("id")
    private int id;

    @SerializedName("nombre")
    private String nombre;

    public Empresa(String cif, String nombre, String direccion, LocalDate fechaCreacion) {
        this.cif = cif;
        this.nombre = nombre;
        this.direccion = direccion;
        this.fechaCreacion = fechaCreacion;
        this.trabajadores = new ArrayList<>();
    }

    @Override
    public String toString() {
        return
                "cif: " + cif +
                        ", id: " + id +
                        ", nombre: " + nombre +
                        ", direccion: " + direccion +
                        ", fechaCreacion: " + fechaCreacion;
    }
}