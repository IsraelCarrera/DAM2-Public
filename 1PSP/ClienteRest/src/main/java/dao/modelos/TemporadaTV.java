package dao.modelos;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class TemporadaTV {

    @SerializedName("air_date")
    private LocalDate airDate;

    @SerializedName("overview")
    private String overview;

    @SerializedName("name")
    private String name;

    @SerializedName("season_number")
    private int seasonNumber;

    @SerializedName("_id")
    private String _id; //Feísimo ese _id, pero como no sé si me dará problemas, lo dejaré así...

    @SerializedName("id")
    private int id;

    @SerializedName("episodes")
    private List<Episodio> episodes;

    @SerializedName("poster_path")
    private String posterPath;

    @Override
    public String toString() {
        return seasonNumber + " " + name + " fecha de emisión: " + airDate;
    }
}