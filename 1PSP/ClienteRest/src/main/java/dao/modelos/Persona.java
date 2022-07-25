package dao.modelos;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class Persona {

    @SerializedName("birthday")
    private LocalDate birthday;

    @SerializedName("also_known_as")
    private List<String> alsoKnownAs;

    @SerializedName("gender")
    private int gender;

    @SerializedName("imdb_id")
    private String imdbId;

    @SerializedName("known_for_department")
    private String knownForDepartment;

    @SerializedName("profile_path")
    private String profilePath;

    @SerializedName("biography")
    private String biography;

    @SerializedName("deathday")
    private LocalDate deathday;

    @SerializedName("place_of_birth")
    private String placeOfBirth;

    @SerializedName("popularity")
    private double popularity;

    @SerializedName("name")
    private String name;

    @SerializedName("id")
    private int id;

    @SerializedName("adult")
    private boolean adult;

    @SerializedName("homepage")
    private String homepage;

    @Override
    public String toString() {
        String s = id + " " + name + " ";
        if (biography != null) {
            s += biography + " ";
        }
        if (birthday != null) {
            s += birthday + " ";
        }
        if (placeOfBirth != null) {
            s += placeOfBirth + " ";
        }
        if (deathday != null) {
            s += deathday + " ";
        }
        return s;
    }
}