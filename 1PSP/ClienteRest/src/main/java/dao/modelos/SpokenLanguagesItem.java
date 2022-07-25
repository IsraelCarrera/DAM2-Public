package dao.modelos;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

@Getter
public class SpokenLanguagesItem {

    @SerializedName("name")
    private String name;

    @SerializedName("iso_639_1")
    private String iso6391;

    @SerializedName("english_name")
    private String englishName;

    @Override
    public String toString() {
        return name;
    }
}