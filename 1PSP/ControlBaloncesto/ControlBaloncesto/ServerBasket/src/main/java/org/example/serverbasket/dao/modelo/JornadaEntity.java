package org.example.serverbasket.dao.modelo;

import lombok.*;

import java.sql.Date;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JornadaEntity {
    private int idJornada;
    private Date fechaJornada;

}
