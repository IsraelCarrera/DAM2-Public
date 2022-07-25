package dao;


import dao.hibernate.*;

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
        if ("HibernateDAOItems".equals(sourcecItems)) {
            dao = new HibernateDAOItems();
        }
        return dao;
    }

    public DAOCustomers getDAOCustomers() {
        DAOCustomers dao = null;
        sourcecItems = daoProps.getProperty("daoCustomers");
        if ("HibernateDAOCustomers".equals(sourcecItems)) {
            dao = new HibernateDAOCustomers();
        }
        return dao;
    }

    public DAOPurchases getDAOPurchases() {
        DAOPurchases dao = null;
        sourcecItems = daoProps.getProperty("daoPurchases");
        if ("HibernateDAOPurchases".equals(sourcecItems)) {
            dao = new HibernateDAOPurchases();
        }
        return dao;
    }

    public DAOReviews getDAOReviews() {
        DAOReviews dao = null;
        sourcecItems = daoProps.getProperty("daoReviews");
        if ("HibernateDAOReviews".equals(sourcecItems)) {
            dao = new HibernateDAOReviews();
        }
        return dao;
    }

    public DAOUsers getDAOUsers() {
        DAOUsers dao = null;
        sourcecItems = daoProps.getProperty("daoUsers");
        if ("HibernateDAOUsers".equals(sourcecItems)) {
            dao = new HibernateDAOUsers();
        }
        return dao;
    }
}
