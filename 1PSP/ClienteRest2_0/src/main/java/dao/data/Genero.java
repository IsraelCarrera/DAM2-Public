package dao.data;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;

@Getter
public class Genero{

	@SerializedName("genres")
	private List<GenresItem> genres;

	@Override
	public String toString() {
		return genres.toString();
	}
}