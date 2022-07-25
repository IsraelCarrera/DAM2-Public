package dao;

import dao.JDBCD.JDBCDAOCustomers;
import dao.JDBCD.JDBCDAOItems;
import dao.JDBCD.JDBCDAOPurchases;
import dao.JDBCD.JDBCDAOReviews;
import dao.spring.*;

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
        sourcecItems = daoProps.getProperty("daoItems");
        if ("JDBCDAOItems".equals(sourcecItems)) {
            dao = new JDBCDAOItems();
        } else if ("SpringDAOItems".equals(sourcecItems)) {
            dao = new SpringDAOItems();
        }
        return dao;
    }

    public DAOCustomers getDAOCustomers() {
        DAOCustomers dao = null;
        sourcecItems = daoProps.getProperty("daoCustomers");
        if ("JDBCDAOCustomers".equals(sourcecItems)) {
            dao = new JDBCDAOCustomers();
        } else if ("SpringDAOCustomers".equals(sourcecItems)) {
            dao = new SpringDAOCustomers();
        }
        return dao;
    }

    public DAOPurchases getDAOPurchases() {
        DAOPurchases dao = null;
        sourcecItems = daoProps.getProperty("daoPurchases");
        if ("JDBCDAOPurchases".equals(sourcecItems)) {
            dao = new JDBCDAOPurchases();
        } else if ("SpringDAOPurchases".equals(sourcecItems)) {
            dao = new SpringDAOPurchases();
        }
        return dao;
    }

    public DAOReviews getDAOReviews() {
        DAOReviews dao = null;
        sourcecItems = daoProps.getProperty("daoReviews");
        if ("JDBCDAOReviews".equals(sourcecItems)) {
            dao = new JDBCDAOReviews();
        } else if ("SpringDAOReviews".equals(sourcecItems)) {
            dao = new SpringDAOReviews();
        }
        return dao;
    }

    public DAOUsers getDAOUsers() {
        DAOUsers dao = null;
        sourcecItems = daoProps.getProperty("daoUsers");
        if ("SpringDAOUsers".equals(sourcecItems)) {
            dao = new SpringDAOUsers();
        }
        return dao;
    }
}
