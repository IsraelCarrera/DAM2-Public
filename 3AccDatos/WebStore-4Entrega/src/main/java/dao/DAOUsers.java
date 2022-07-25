package dao;

import model.User;

public interface DAOUsers {
    User getUserById(int id);
    User getUserLoggin(String username, String password);
}
