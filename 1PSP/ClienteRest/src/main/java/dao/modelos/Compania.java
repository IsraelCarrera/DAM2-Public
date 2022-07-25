package dao.modelos;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

@Getter
public class Compania{

	@SerializedName("headquarters")
	private String headquarters;

	@SerializedName("parent_company")
	private Object parentCompany;

	@SerializedName("logo_path")
	private String logoPath;

	@SerializedName("name")
	private String name;

	@SerializedName("description")
	private String description;

	@SerializedName("id")
	private int id;

	@SerializedName("homepage")
	private String homepage;

	@SerializedName("origin_country")
	private String originCountry;

	@Override
	public String toString() {
		return name;
	}
}