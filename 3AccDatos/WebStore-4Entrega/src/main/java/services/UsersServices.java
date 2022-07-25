package services;

import dao.FactoryDAO;
import model.User;

public class UsersServices {
    private final FactoryDAO dao;

    public UsersServices() {
        dao = new FactoryDAO();
    }

    public User userById(int id){
        return dao.getDAOUsers().getUserById(id);
    }

    public User userLoggin(String username, String password){
        return dao.getDAOUsers().getUserLoggin(username,password);
    }
}
