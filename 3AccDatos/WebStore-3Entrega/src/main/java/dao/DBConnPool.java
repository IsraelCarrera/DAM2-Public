package dao;

import configuration.ConfigProperties;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnPool {
    public static DBConnPool dbConnectionPool = null;
    public BasicDataSource pool;

    private DBConnPool() {
        super();
        pool = this.getPool();
    }

    public static DBConnPool getInstance() {
        if (dbConnectionPool == null) {
            dbConnectionPool = new DBConnPool();
        }
        return dbConnectionPool;
    }

    private BasicDataSource getPool() {
        //String driver = ConfigProperties.getInstance().getProperty("driver");
        String urlDB = ConfigProperties.getInstance().getProperty("urlDB");
        String userName = ConfigProperties.getInstance().getProperty("user_name");
        String password = ConfigProperties.getInstance().getProperty("password");

        BasicDataSource bds = new BasicDataSource();
        //bds.setDriverClassName(driver);
        bds.setUsername(userName);
        bds.setPassword(password);
        bds.setUrl(urlDB);

        return bds;
    }

    public Connection getConnection() {
        Connection con = null;
        try {
            con = pool.getConnection();
        } catch (SQLException e) {
            Logger.getLogger(DBConnPool.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return con;
    }

    public static void closePool(BasicDataSource bds) {
        if (bds != null) {
            try {
                bds.close();
                bds = null;
            } catch (SQLException e) {
                Logger.getLogger(DBConnPool.class.getName()).log(Level.SEVERE, e.getMessage(), e);
            }
        }
    }

    public BasicDataSource getDataSource(){
        return pool;
    }
}
