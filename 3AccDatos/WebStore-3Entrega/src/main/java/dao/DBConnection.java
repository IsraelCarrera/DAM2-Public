package dao;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnection {

//    public String urlDB;
//    public String userName;
//    public String password;

    //Coger conexión
    public Connection getConnection() {
        Connection con = null;
        //Hacerlo sin Pool
//        urlDB= ConfigProperties.getInstance().getProperty("urlDB");
//        userName = ConfigProperties.getInstance().getProperty("user_name");
//        password = ConfigProperties.getInstance().getProperty("password");
//        try {
//            con = DriverManager.getConnection(urlDB, userName, password);
//        }catch (SQLException e){
//            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE,e.getMessage(),e);
//        }
        con = DBConnPool.getInstance().getConnection();
        return con;
    }

    //Cerrar conexion.

    public void closeConnection(Connection con) {
        try {
            if (con != null) {
                con.close();
                con = null;
            }
        } catch (SQLException e) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public void releaseResource(PreparedStatement pstmt) {
        try {
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException e) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public void releaseResource(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }

        } catch (SQLException e) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public void releaseResource(Statement stmt) {
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }


}
