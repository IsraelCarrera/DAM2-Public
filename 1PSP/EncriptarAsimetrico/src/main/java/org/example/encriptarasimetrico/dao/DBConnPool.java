package org.example.encriptarasimetrico.dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.example.encriptarasimetrico.config.Config;
import org.example.encriptarasimetrico.dao.utils.Constantes;

import javax.inject.Inject;
import javax.sql.DataSource;

public class DBConnPool {

    private final Config config;
    private DataSource hirakiDatasource = null;

    @Inject
    public DBConnPool(Config config) {
        this.config = config;
        hirakiDatasource = getDataSourceHikari();
    }


    private DataSource getDataSourceHikari() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(this.config.getRuta());
        config.setUsername(this.config.getUser_bd());
        config.setPassword(this.config.getPassword_bd());
        config.setDriverClassName(this.config.getDriver());
        config.setMaximumPoolSize(1);
        config.addDataSourceProperty(Constantes.DBPOOL_CACHE_PREP_STMTS, Constantes.DBPOOL_VALUE_CACHE_PREP_STMTS);
        config.addDataSourceProperty(Constantes.DBPOOL_PREP_STMT_CACHE_SIZE, Constantes.DBPOOL_VALUE_CACHE_SIZE);
        config.addDataSourceProperty(Constantes.DBPOOL_PREP_STMT_CACHE_SQL_LIMIT, Constantes.DBPOOL_VALUE_PREP_STMT_CACHE_SQL_LIMIT);
        HikariDataSource datasource = new HikariDataSource(config);

        return datasource;
    }

    public DataSource getDataSource() {
        return hirakiDatasource;
    }


    public void closePool() {
        ((HikariDataSource) hirakiDatasource).close();
    }
}
