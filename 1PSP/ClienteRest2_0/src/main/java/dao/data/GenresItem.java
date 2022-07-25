package dao.data;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

@Getter
public class GenresItem {

    @SerializedName("name")
    private String name;

    @SerializedName("id")
    private int id;

    @Override
    public String toString() {
        return name;
    }
}