package dao.data;

import java.time.LocalDate;
import java.util.List;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;

@Getter
public class SerieSacadaDeBuscar {

	@SerializedName("first_air_date")
	private LocalDate firstAirDate;

	@SerializedName("overview")
	private String overview;

	@SerializedName("original_language")
	private String originalLanguage;

	@SerializedName("genre_ids")
	private List<Integer> genreIds;

	@SerializedName("poster_path")
	private String posterPath;

	@SerializedName("origin_country")
	private List<String> originCountry;

	@SerializedName("backdrop_path")
	private String backdropPath;

	@SerializedName("popularity")
	private double popularity;

	@SerializedName("vote_average")
	private double voteAverage;

	@SerializedName("original_name")
	private String originalName;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;

	@SerializedName("vote_count")
	private int voteCount;

	@Override
	public String toString() {
		return name + " , " + originalName + " , " + firstAirDate;
	}
}