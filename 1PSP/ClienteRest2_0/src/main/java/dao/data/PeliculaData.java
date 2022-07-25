package dao.data;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Getter
public class PeliculaData {

    @SerializedName("original_language")
    private String originalLanguage;

    @SerializedName("imdb_id")
    private String imdbId;

    @SerializedName("video")
    private boolean video;

    @SerializedName("title")
    private String title;

    @SerializedName("backdrop_path")
    private String backdropPath;

    @SerializedName("revenue")
    private int revenue;

    @SerializedName("genres")
    private List<GenresItem> genres;

    @SerializedName("popularity")
    private double popularity;

    @SerializedName("production_countries")
    private List<ProductionCountriesItem> productionCountries;

    @SerializedName("id")
    private int id;

    @SerializedName("vote_count")
    private int voteCount;

    @SerializedName("budget")
    private int budget;

    @SerializedName("overview")
    private String overview;

    @SerializedName("original_title")
    private String originalTitle;

    @SerializedName("runtime")
    private int runtime;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("spoken_languages")
    private List<SpokenLanguagesItem> spokenLanguages;

    @SerializedName("production_companies")
    private List<CompaniaData> productionCompanies;

    @SerializedName("release_date")
    private LocalDate releaseDate;

    @SerializedName("vote_average")
    private double voteAverage;

    @SerializedName("belongs_to_collection")
    private ColeccionesData belongsToCollection;

    @SerializedName("tagline")
    private String tagline;

    @SerializedName("adult")
    private boolean adult;

    @SerializedName("homepage")
    private String homepage;

    @SerializedName("status")
    private String status;

    @Override
    public String toString() {
        String s = id + " " + title + " " + " " + originalTitle + " ";
        if (genres != null) {
            s += genres + " ";
        }
        if (belongsToCollection != null) {
            s += belongsToCollection.getName() + " ";
        }
        s = s + releaseDate + " ";
        if (productionCompanies != null) {
            s += productionCompanies;
        }
        return s;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PeliculaData peliculaData = (PeliculaData) o;
        return id == peliculaData.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}