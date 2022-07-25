package dao.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {

    private String id;
    private String nombre;
    private String pass;

    public Usuario(String nombre, String pass) {
        this.nombre = nombre;
        this.pass = pass;
    }

    @Override
    public String toString() {
        return "id: " + id + " nombre: " + nombre + "  pass: " + pass;
    }
}
