package dao.data;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;

@Getter
public class BuscarSerieData {

	@SerializedName("page")
	private int page;

	@SerializedName("total_pages")
	private int totalPages;

	@SerializedName("results")
	private List<SerieSacadaDeBuscar> results;

	@SerializedName("total_results")
	private int totalResults;
}