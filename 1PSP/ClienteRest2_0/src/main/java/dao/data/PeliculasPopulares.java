package dao.data;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

import java.util.List;

@Getter
public class PeliculasPopulares {

    @SerializedName("page")
    private int page;

    @SerializedName("total_pages")
    private int totalPages;

    @SerializedName("results")
    private List<PeliculaData> results;

    @SerializedName("total_results")
    private int totalResults;

    @Override
    public String toString() {
        return results.toString();
    }
}