package dao.data;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

@Getter
public class ProductionCountriesItem {

    @SerializedName("iso_3166_1")
    private String iso31661;

    @SerializedName("name")
    private String name;

    @Override
    public String toString() {
        return name;
    }
}