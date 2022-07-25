package dao.data;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

@Getter
public class NetworksData {

    @SerializedName("headquarters")
    private String headquarters;

    @SerializedName("logo_path")
    private String logoPath;

    @SerializedName("name")
    private String name;

    @SerializedName("id")
    private int id;

    @SerializedName("homepage")
    private String homepage;

    @SerializedName("origin_country")
    private String originCountry;

    @Override
    public String toString() {
        String s = name + " ";
        if (originCountry != null) {
            s += originCountry + " ";
        }
        if (headquarters != null) {
            s += originCountry;
        }
        return s;
    }
}