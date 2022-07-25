package modelos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class Networks {
    private String name;
    private String originCountry;
    private String headquarters;

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
