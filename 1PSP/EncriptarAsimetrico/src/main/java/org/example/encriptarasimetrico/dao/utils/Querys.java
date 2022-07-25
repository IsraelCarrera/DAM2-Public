package org.example.encriptarasimetrico.dao.utils;

public class Querys {
    public static final String INSERT_SECRETOS_QUERY = "INSERT INTO secretos (nombre,secreto) VALUES (?,?)";
    public static final String SELECT_SECRETOS = "SELECT * FROM secretos WHERE id=?";
    public static final String INSERT_SECRETOSCOMPARTIDOS_QUERY = "INSERT INTO secretos_compartidos (id,userACompartir,ClaveCifradaPublic) VALUES (?,?,?)";
    public static final String SELECT_SECRETOSCOMPARTIDOS = "SELECT * FROM secretos_compartidos WHERE userACompartir=?";
    public static final String SELECT_SECRETOSCOMPARTIDOS_PORID = "SELECT * FROM secretos_compartidos WHERE id=?";

}
