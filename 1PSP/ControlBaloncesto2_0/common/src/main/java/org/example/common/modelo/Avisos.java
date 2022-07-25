package org.example.common.modelo;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Avisos {
    private String aviso;

    @Override
    public String toString() {
        return aviso;
    }
}
