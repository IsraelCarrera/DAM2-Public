package dao.spring;

import dao.DAOItems;
import dao.DBConnPool;
import model.Item;
import model.Purchase;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import utils.Querys;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SpringDAOItems implements DAOItems {
    @Override
    public Item get(int id) {
        JdbcTemplate bd = new JdbcTemplate(DBConnPool.getInstance().getDataSource());
        List<Item> result = bd.query(Querys.SELECT_itemID_QUERY, BeanPropertyRowMapper.newInstance(Item.class), id);
        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public List<Item> getAll() {
        JdbcTemplate bd = new JdbcTemplate(DBConnPool.getInstance().getDataSource());
        List<Item> result = bd.query(Querys.SELECT_allItems_QUERY, BeanPropertyRowMapper.newInstance(Item.class));
        return result.isEmpty() ? null : result;
    }

    @Override
    public int save(Item t) {
        KeyHolder kh = new GeneratedKeyHolder();
        JdbcTemplate insert = new JdbcTemplate(DBConnPool.getInstance().getDataSource());
        insert.update(conn -> {
            PreparedStatement ps = conn
                    .prepareStatement(Querys.INSERT_item_QUERY, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, t.getName());
            ps.setString(2, t.getCompany());
            ps.setFloat(3, (float) t.getPrice());
            return ps;
        }, kh);
        return kh.getKey().intValue();
    }

    @Override
    public void update(Item t) {
        JdbcTemplate bd = new JdbcTemplate(DBConnPool.getInstance().getDataSource());
        bd.update(Querys.UPDATE_item_QUERY, t.getName(), t.getCompany(), t.getPrice(), t.getIdItem());
    }

    @Override
    public boolean delete(Item t, List<Purchase> purchases) {
        boolean isDelete;
        if (purchases == null) {
            isDelete = deleteWithoutPurchases(t);
        } else {
            isDelete = deleteWithPurchases(t, purchases);
        }
        return isDelete;
    }

    @Override
    public boolean existId(int id) {
        boolean exist = false;
        List<Item> items = getAll();
        for (Item i : items) {
            if (i.getIdItem() == id) {
                exist = true;
            }
        }
        return exist;
    }

    @Override
    public void closeDBPool() {
        DBConnPool.closePool(DBConnPool.getInstance().pool);
    }

    //TODO Comprobar
    private boolean deleteWithPurchases(Item t, List<Purchase> purchases) {
        boolean isDelete = false;
        TransactionDefinition txDef = new DefaultTransactionDefinition();
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(DBConnPool.getInstance().getDataSource());
        TransactionStatus txStatus = transactionManager.getTransaction(txDef);
        try {
            JdbcTemplate delete = new JdbcTemplate(transactionManager.getDataSource());
            for (Purchase p : purchases) {
                delete.update(Querys.DELETE_purchases_QUERY, p.getIdPurchase());
            }
            delete.update(Querys.DELETE_item_QUERY, t.getIdItem());
            transactionManager.commit(txStatus);
            isDelete = true;
        } catch (Exception e) {
            transactionManager.rollback(txStatus);
            Logger.getLogger(SpringDAOItems.class.getName()).log(Level.SEVERE, null, e);
        }
        return isDelete;
    }

    private boolean deleteWithoutPurchases(Item t) {
        JdbcTemplate delete = new JdbcTemplate(DBConnPool.getInstance().getDataSource());
        delete.update(Querys.DELETE_item_QUERY, t.getIdItem());
        return true;
    }
}
