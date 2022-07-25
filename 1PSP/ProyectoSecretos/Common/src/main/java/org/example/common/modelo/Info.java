package org.example.common.modelo;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Info {
    String mensaje;

    @Override
    public String toString() {
        return mensaje;
    }
}
