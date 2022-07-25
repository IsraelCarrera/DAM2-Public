package org.example.serverbasket.dao.utils;

public class Querys {
    public static final String INSERT_USER = "INSERT INTO user (nombre, certificado) VALUES (?,?)";
    public static final String SELECT_ALL_USERS = "SELECT * FROM user";
    public static final String INSERT_SECRETOS_QUERY = "INSERT INTO secretos (nombreUser,secreto,firma) VALUES (?,?,?)";
    public static final String SELECT_SECRETOS = "SELECT * FROM secretos WHERE id=?";
    public static final String INSERT_SECRETOSCOMPARTIDOS_QUERY = "INSERT INTO secretos_compartidos (id,userACompartir,ClaveCifradaPublica) VALUES (?,?,?)";
    public static final String SELECT_SECRETOSCOMPARTIDOS = "SELECT * FROM secretos_compartidos WHERE userACompartir=?";
    public static final String SELECT_SECRETOSCOMPARTIDOS_PORID = "SELECT * FROM secretos_compartidos WHERE id=?";
    public static final String SELECT_SECRETOSALL = "SELECT s.id,userACompartir,claveCifradaPublica,s.secreto, s.firma,s.nombreUser from secretos_compartidos " +
            "inner join secretos s on secretos_compartidos.id = s.id " +
            "where secretos_compartidos.userACompartir = ?";
}
