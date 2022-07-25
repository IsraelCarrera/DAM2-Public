package dao.data;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

import java.util.List;

@Getter
public class CastPeliculaData {

    @SerializedName("cast")
    private List<PersonaData> cast;

    @SerializedName("id")
    private int id;

    @SerializedName("crew")
    private List<PersonaData> crew;
}