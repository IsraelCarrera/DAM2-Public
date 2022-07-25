package dao.data;


import java.time.LocalDate;
import java.util.List;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;

@Getter
public class EpisodioData {

	@SerializedName("production_code")
	private String productionCode;

	@SerializedName("air_date")
	private LocalDate airDate;

	@SerializedName("overview")
	private String overview;

	@SerializedName("episode_number")
	private int episodeNumber;

	@SerializedName("vote_average")
	private double voteAverage;

	@SerializedName("name")
	private String name;

	@SerializedName("season_number")
	private int seasonNumber;

	@SerializedName("id")
	private int id;

	@SerializedName("still_path")
	private String stillPath;

	@SerializedName("vote_count")
	private int voteCount;

	@SerializedName("crew")
	private List<PersonaData> crew;

	@SerializedName("guest_stars")
	private List<PersonaData> guestStars;

	@Override
	public String toString() {
		String s =seasonNumber + "x" + episodeNumber + " " + name + " ";
		if(airDate != null){
			s+="fecha de emisión: " + airDate;
		}
		return s;
	}
}