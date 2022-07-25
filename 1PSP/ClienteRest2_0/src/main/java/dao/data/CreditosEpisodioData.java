package dao.data;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;

@Getter
public class CreditosEpisodioData {

	@SerializedName("cast")
	private List<PersonaData> cast;

	@SerializedName("id")
	private int id;

	@SerializedName("crew")
	private List<PersonaData> crew;

	@SerializedName("guest_stars")
	private List<PersonaData> guestStars;
}