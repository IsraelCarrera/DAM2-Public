package services;

import dao.FactoryDAO;
import model.User;

public class UsersServices {
    private final FactoryDAO dao;

    public UsersServices() {
        dao = new FactoryDAO();
    }

    public User userLoggin(String username, String password){
        return dao.getDAOUsers().getUserLoggin(username,password);
    }
}
