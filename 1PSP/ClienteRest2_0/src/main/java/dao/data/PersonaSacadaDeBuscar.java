package dao.data;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;

@Getter
public class PersonaSacadaDeBuscar {

	@SerializedName("known_for")
	private List<BasuraPersona> knownFor;

	@SerializedName("popularity")
	private double popularity;

	@SerializedName("name")
	private String name;

	@SerializedName("profile_path")
	private String profilePath;

	@SerializedName("id")
	private int id;

	@SerializedName("adult")
	private boolean adult;

	@Override
	public String toString() {
		return name;
	}
}