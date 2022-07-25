package dao;




import dao.mongoDB.DAOCustomerMongo;
import dao.mongoDB.DAOItemsMongo;
import dao.mongoDB.DAOUsersMongo;

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
            dao = new DAOItemsMongo();
        }
        return dao;
    }

    public DAOCustomers getDAOCustomers() {
        DAOCustomers dao = null;
        sourcecItems = daoProps.getProperty("daoCustomers");
        if ("HibernateDAOCustomers".equals(sourcecItems)) {
            dao = new DAOCustomerMongo();
        }
        return dao;
    }

    public DAOUsers getDAOUsers() {
        DAOUsers dao = null;
        sourcecItems = daoProps.getProperty("daoUsers");
        if ("DAOUsersMongo".equals(sourcecItems)) {
            dao = new DAOUsersMongo();
        }
        return dao;
    }
}
