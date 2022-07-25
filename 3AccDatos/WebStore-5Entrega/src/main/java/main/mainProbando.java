package main;

import dao.mongoDB.DAOUsersMongo;
import model.User;

public class mainProbando {
    public static void main(String[] args) {
        DAOUsersMongo dao = new DAOUsersMongo();

        User s = dao.getUserLoggin("root","root");
        System.out.println(s);
    }
}
