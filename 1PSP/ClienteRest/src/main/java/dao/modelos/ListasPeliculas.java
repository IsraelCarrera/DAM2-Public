package dao.modelos;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

import java.util.List;

@Getter
public class ListasPeliculas {

    @SerializedName("item_count")
    private int itemCount;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("favorite_count")
    private int favoriteCount;

    @SerializedName("id")
    private String id;

    @SerializedName("created_by")
    private String createdBy;

    @SerializedName("items")
    private List<Pelicula> items;

    @SerializedName("iso_639_1")
    private String iso6391;

    @SerializedName("poster_path")
    private String posterPath;

    @Override
    public String toString() {
        return items.toString();
    }
}