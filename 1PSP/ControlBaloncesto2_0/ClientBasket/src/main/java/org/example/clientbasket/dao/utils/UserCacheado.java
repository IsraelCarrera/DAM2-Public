package org.example.clientbasket.dao.utils;

import lombok.Data;

import javax.inject.Singleton;

@Data
@Singleton
public class UserCacheado {

    private String usuario;
    private String pass;
    private String token;
}
