package dao;

import dao.JDBCD.JDBCDAOCustomers;
import dao.JDBCD.JDBCDAOItems;
import dao.JDBCD.JDBCDAOPurchases;
import dao.JDBCD.JDBCDAOReviews;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;


public class FactoryDAO {
    private static FactoryDAO instance;
    private String sourcecItems;
    private Properties daoProps;
    private final String properties_file = "propertiesFiles\\daoProperties.xml";

    public FactoryDAO() {
        setDAOType(properties_file);
    }

    private void setDAOType(String configFile) {
        daoProps = new Properties();
        try {
            daoProps.loadFromXML(Files.newInputStream(Paths.get(configFile)));
        } catch (IOException e) {
            Logger.getLogger(FactoryDAO.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public DAOItems getDAOItems() {
        DAOItems dao = null;
        sourcecItems = daoProps.getProperty("daoitems");
        switch (sourcecItems) {
            case "JDBCDAOItems":
                dao = new JDBCDAOItems();
                break;
        }
        return dao;
    }

    public DAOCustomers getDAOCustomers() {
        DAOCustomers dao = null;
        sourcecItems = daoProps.getProperty("daocustomers");
        switch (sourcecItems) {
            case "JDBCDAOCustomers":
                dao = new JDBCDAOCustomers();
                break;
        }
        return dao;
    }

    public DAOPurchases getDAOPurchases() {
        DAOPurchases dao = null;
        sourcecItems = daoProps.getProperty("daopurchases");
        switch (sourcecItems) {
            case "JDBCDAOPurchases":
                dao = new JDBCDAOPurchases();
                break;
        }
        return dao;
    }

    public DAOReviews getDAOReviews() {
        DAOReviews dao = null;
        sourcecItems = daoProps.getProperty("daopurchases");
        switch (sourcecItems) {
            case "JDBCDAOPurchases":
                dao = new JDBCDAOReviews();
                break;
        }
        return dao;
    }
}
