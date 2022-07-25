package dao.data;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class ApiError {

    @SerializedName("fecha")
    private LocalDate fecha;

    @SerializedName("mensaje")
    private String mensaje;
}