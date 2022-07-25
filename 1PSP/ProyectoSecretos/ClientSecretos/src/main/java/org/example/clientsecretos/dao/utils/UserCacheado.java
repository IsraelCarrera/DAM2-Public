package org.example.clientsecretos.dao.utils;


import lombok.Data;

import javax.inject.Singleton;

@Data
@Singleton
public class UserCacheado {
    private String nombre;
    private String pass;
    private String token;
    private String certificadoBase64;
}
