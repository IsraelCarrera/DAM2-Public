package dao.data;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class SerieTvData {

    @SerializedName("original_language")
    private String originalLanguage;

    @SerializedName("number_of_episodes")
    private int numberOfEpisodes;

    @SerializedName("networks")
    private List<NetworksData> networks;

    @SerializedName("type")
    private String type;

    @SerializedName("backdrop_path")
    private String backdropPath;

    @SerializedName("genres")
    private List<GenresItem> genres;

    @SerializedName("popularity")
    private double popularity;

    @SerializedName("production_countries")
    private List<ProductionCountriesItem> productionCountries;

    @SerializedName("id")
    private int id;

    @SerializedName("number_of_seasons")
    private int numberOfSeasons;

    @SerializedName("vote_count")
    private int voteCount;

    @SerializedName("first_air_date")
    private LocalDate firstAirDate;

    @SerializedName("overview")
    private String overview;

    @SerializedName("seasons")
    private List<TemporadaTVData> seasons;

    @SerializedName("languages")
    private List<String> languages;

    @SerializedName("created_by")
    private List<PersonaData> createdBy;

    @SerializedName("last_episode_to_air")
    private EpisodioData lastEpisodeToAir;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("origin_country")
    private List<String> originCountry;

    @SerializedName("spoken_languages")
    private List<SpokenLanguagesItem> spokenLanguages;

    @SerializedName("production_companies")
    private List<CompaniaData> productionCompanies;

    @SerializedName("original_name")
    private String originalName;

    @SerializedName("vote_average")
    private double voteAverage;

    @SerializedName("name")
    private String name;

    @SerializedName("tagline")
    private String tagline;

    @SerializedName("episode_run_time")
    private List<Integer> episodeRunTime;

    @SerializedName("next_episode_to_air")
    private EpisodioData nextEpisodeToAir;

    @SerializedName("in_production")
    private boolean inProduction;

    @SerializedName("last_air_date")
    private LocalDate lastAirDate;

    @SerializedName("homepage")
    private String homepage;

    @SerializedName("status")
    private String status;

    @Override
    public String toString() {
        return id + " " + name + " " + originalName + " " + originalLanguage + " " +
                " temporadas:" + seasons.size() + " episodios:" + numberOfEpisodes;
    }
}