package org.example.encriptar.utils;

public class Querys {
    public static final String INSERT_SECRETOS_QUERY = "INSERT INTO secretos (nombre,secreto) VALUES (?,?)";
    public static final String SELECT_SECRETOS = "SELECT * FROM secretos WHERE nombre=?";
}
