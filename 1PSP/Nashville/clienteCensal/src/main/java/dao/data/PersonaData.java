package dao.data;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonaData {

	@SerializedName("fechaNacimiento")
	private LocalDate fechaNacimiento;

	@SerializedName("hijos")
	private List<PersonaData> hijos;

	@SerializedName("lugarProcedencia")
	private String lugarProcedencia;

	@SerializedName("estadoCivil")
	private String estadoCivil;

	@SerializedName("id")
	private String id;

	@SerializedName("sexo")
	private String sexo;

	@SerializedName("nombre")
	private String nombre;

	@SerializedName("idPareja")
	private String idPareja;

	@Override
	public String toString() {
		return "Nombre: " + nombre + ", fecha de nacimiento: " + fechaNacimiento +
				", lugar de Procedencia: " + lugarProcedencia + ", sexo: " + sexo +
				", estadoCivil: " + estadoCivil + ", numero de hijos: " + hijos.size();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		PersonaData that = (PersonaData) o;
		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}