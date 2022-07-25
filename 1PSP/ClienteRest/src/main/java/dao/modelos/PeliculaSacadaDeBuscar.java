package dao.modelos;

import java.time.LocalDate;
import java.util.List;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;

@Getter
public class PeliculaSacadaDeBuscar {

	@SerializedName("overview")
	private String overview;

	@SerializedName("original_language")
	private String originalLanguage;

	@SerializedName("original_title")
	private String originalTitle;

	@SerializedName("video")
	private boolean video;

	@SerializedName("title")
	private String title;

	@SerializedName("genre_ids")
	private List<Integer> genreIds;

	@SerializedName("poster_path")
	private String posterPath;

	@SerializedName("backdrop_path")
	private String backdropPath;

	@SerializedName("release_date")
	private LocalDate releaseDate;

	@SerializedName("popularity")
	private double popularity;

	@SerializedName("vote_average")
	private double voteAverage;

	@SerializedName("id")
	private int id;

	@SerializedName("adult")
	private boolean adult;

	@SerializedName("vote_count")
	private int voteCount;

	@Override
	public String toString() {
		return title + " , " + originalTitle + " , Fecha de lanzamiento; " + releaseDate;
	}
}