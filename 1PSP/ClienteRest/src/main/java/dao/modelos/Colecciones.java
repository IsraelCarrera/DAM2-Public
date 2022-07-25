package dao.modelos;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;

@Getter
public class Colecciones{

	@SerializedName("backdrop_path")
	private String backdropPath;

	@SerializedName("overview")
	private String overview;

	@SerializedName("name")
	private String name;

	@SerializedName("parts")
	private List<Pelicula> parts;

	@SerializedName("id")
	private int id;

	@SerializedName("poster_path")
	private Object posterPath;

	@Override
	public String toString() {
		return id + " " + name + " " + parts;
	}
}