package dao;

import model.User;

public interface DAOUsers {
    User getUserById(String id);
    User getUserLoggin(String username, String password);
    boolean addUser(User u);
    boolean deleteUser(String dni);
}
