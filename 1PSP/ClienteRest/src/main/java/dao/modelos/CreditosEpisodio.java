package dao.modelos;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;

@Getter
public class CreditosEpisodio{

	@SerializedName("cast")
	private List<Persona> cast;

	@SerializedName("id")
	private int id;

	@SerializedName("crew")
	private List<Persona> crew;

	@SerializedName("guest_stars")
	private List<Persona> guestStars;
}