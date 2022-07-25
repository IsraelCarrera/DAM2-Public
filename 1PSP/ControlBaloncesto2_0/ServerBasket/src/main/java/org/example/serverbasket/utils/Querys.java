package org.example.serverbasket.utils;

public class Querys {
    //USER
    public static final String INSERT_USER_QUERY = "INSERT INTO user (nombreUser,passHash,codActivacion,estaActivo,fechaLimite,idTipoUsuario, codRestPass,correoElectronico) values(?,?,?,?,?,?,?,?)";
    public static final String USUARIO_POR_CODACTIVACION_QUERY = "SELECT * FROM user WHERE codActivacion= ?";
    public static final String UPDATE_USER_ACTIVADO = "UPDATE user SET estaActivo=true, codActivacion=null WHERE idUser= ?";
    public static final String UPDATE_CODIGO_PARA_ADMINISTRADOR = "UPDATE user SET codSerAdmin=? WHERE idUser = ?";
    public static final String UPDATE_A_ADMINISTRADOR = "UPDATE user SET idTipoUsuario=1, codSerAdmin=null WHERE codSerAdmin= ?";
    public static final String CHECK_SI_ESTA_ACTIVO_QUERY = "SELECT * FROM user WHERE idUser = ?";
    public static final String CHECK_CODIGO_ADMIN = "SELECT * FROM user WHERE codSerAdmin=?";
    public static final String UPDATE_CODIGO_CAMBIO_PASS = "UPDATE user SET codRestPass=? WHERE nombreUser = ?";
    public static final String SELECT_EMAIL_USUARIO = "SELECT correoElectronico FROM user WHERE nombreUser = ?";
    public static final String CHECK_CODIGO_CAMBIO_PASS = "SELECT count(*) FROM user WHERE codRestPass = ?";
    public static final String COGER_USER_POR_CODIGO_PASS = "SELECT * FROM user WHERE codRestPass= ?";
    public static final String UPDATE_CAMBIAR_PASS = "UPDATE user SET passHash=?, codRestPass=null WHERE codRestPass= ?";
    public static final String UPDATE_REINICIAR_TIEMPO_ACTIVACION_CUENTA = "UPDATE user SET codActivacion = ?, fechaLimite=? WHERE codActivacion = ?";
    public static final String SELECT_EMAIL_POR_CODIGOACTIVACION = "SELECT correoElectronico FROM user WHERE codActivacion = ?";
    public static final String SELECT_USUARIO_IDUSER = "SELECT * FROM user WHERE nombreUser= ?";
    public static final String SELECT_ALL_USERS = "SELECT * FROM user";
    public static final String DELETE_USER_QUERY = "DELETE FROM user WHERE idUser = ?";

    //EQUIPO
    public static final String SELECT_ALL_EQUIPOS = "SELECT * FROM equipo";
    public static final String INSERT_EQUIPO_QUERY = "INSERT INTO equipo (nombreEquipo) VALUES (?)";
    public static final String DELETE_EQUIPO_QUERY = "DELETE FROM equipo WHERE nombreEquipo=?";
    public static final String UPDATE_EQUIPO_QUERY = "UPDATE equipo SET nombreEquipo= ? WHERE nombreEquipo=?";
    public static final String SELECT_NOMBRE_EQUIPO = "SELECT nombreEquipo FROM equipo WHERE idEquipo=?";
    public static final String SELECT_ID_EQUIPO = "SELECT idEquipo FROM equipo WHERE nombreEquipo=?";

    //JORNADA
    public static final String SELECT_ALL_JORNADAS = "SELECT * FROM jornada";
    public static final String INSERT_JORNADA_QUERY = "INSERT INTO jornada (fechaJornada) VALUES (?)";
    public static final String DELETE_JORNADA_QUERY = "DELETE FROM jornada WHERE idJornada = ?";
    public static final String UPDATE_JORNADA_QUERY = "UPDATE jornada SET fechaJornada = ? WHERE idJornada = ?";

    //PARTIDO
    public static final String SELECT_ALL_PARTIDOS = "SELECT * FROM partido";
    public static final String INSERT_PARTIDO_QUERY = "INSERT INTO partido (idJornada, idEquipoLocal, idEquipoVisitante, resultado) VALUES (?,?,?,?)";
    public static final String DELETE_PARTIDO_QUERY = "DELETE FROM partido WHERE idJornada = ? AND idEquipoLocal= ? AND idEquipoVisitante= ?";
    public static final String UPDATE_PARTIDO_QUERY = "UPDATE partido SET resultado = ? WHERE idJornada = ? AND idEquipoLocal= ? AND idEquipoVisitante=?";
    public static final String SELECT_PARTIDOS_EQUIPO = "SELECT * FROM partido WHERE idEquipoLocal= (SELECT idEquipo FROM equipo WHERE nombreEquipo= ?) " +
            "OR idEquipoVisitante= (SELECT idEquipo FROM equipo WHERE nombreEquipo= ?)";
    public static final String SELECT_PARTIDO_JORNADA = "SELECT * FROM partido WHERE idJornada = ?";
}
