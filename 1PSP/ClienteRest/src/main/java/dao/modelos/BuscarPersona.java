package dao.modelos;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;

@Getter
public class BuscarPersona {

	@SerializedName("page")
	private int page;

	@SerializedName("total_pages")
	private int totalPages;

	@SerializedName("results")
	private List<PersonaSacadaDeBuscar> results;

	@SerializedName("total_results")
	private int totalResults;
}