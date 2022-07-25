package dao;

import model.Purchase;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DraftDAOPurchases implements DAOPurchases{
    private static List<Purchase> dao;

    public DraftDAOPurchases(){
            dao = new ArrayList<>();
            /*Purchase p1 = new Purchase(1, 1, LocalDate.now());
            Purchase p2 = new Purchase(2, 3, LocalDate.now());
            dao.add(p1);
            dao.add(p2);*/
        // Para añadir uno nuevo, utilizar el getSize() (O como sea). Hay que comprobar que hay una ID, entonces, si devuelve -1 que sea false, y lo otro, que sea la longitud.
    }


    @Override
    public Purchase get(int id) {
        return null;
    }

    @Override
    public List<Purchase> getAll() {
        return dao;
    }

    @Override
    public boolean save(Purchase t) {
        return dao.add(t);
    }

    @Override
    public void update(Purchase t) {

    }

    @Override
    public boolean delete(Purchase t) {
        return false;
    }
}
