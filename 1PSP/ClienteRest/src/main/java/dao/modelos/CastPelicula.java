package dao.modelos;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

import java.util.List;

@Getter
public class CastPelicula {

    @SerializedName("cast")
    private List<Persona> cast;

    @SerializedName("id")
    private int id;

    @SerializedName("crew")
    private List<Persona> crew;
}