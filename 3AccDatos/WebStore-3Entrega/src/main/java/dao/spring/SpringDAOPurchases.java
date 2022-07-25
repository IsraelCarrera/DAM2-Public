package dao.spring;

import dao.DAOPurchases;
import dao.DBConnPool;
import model.Customer;
import model.Item;
import model.Purchase;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import utils.Querys;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SpringDAOPurchases implements DAOPurchases {
    @Override
    public Purchase get(int id) {
        JdbcTemplate bd = new JdbcTemplate(DBConnPool.getInstance().getDataSource());
        List<Purchase> result = bd.query(Querys.SELECT_purchasesID_QUERY, BeanPropertyRowMapper.newInstance(Purchase.class), id);
        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public List<Purchase> getAll() {
        JdbcTemplate bd = new JdbcTemplate(DBConnPool.getInstance().getDataSource());
        List<Purchase> purchases = bd.query(Querys.SELECT_allPurchases_QUERY, new ProductMapper());
        return purchases.isEmpty() ? null : purchases;
    }

    @Override
    public int save(Purchase t) {
        KeyHolder kh = new GeneratedKeyHolder();
        JdbcTemplate insert = new JdbcTemplate(DBConnPool.getInstance().getDataSource());
        insert.update(conn -> {
            PreparedStatement ps = conn
                    .prepareStatement(Querys.INSERT_purchases_QUERY, Statement.RETURN_GENERATED_KEYS);
            ps.setDate(1, Date.valueOf(t.getDate()));
            ps.setInt(2, t.getCustomer().getIdCustomer());
            ps.setInt(3, t.getItem().getIdItem());
            return ps;
        }, kh);
        return kh.getKey().intValue();
    }

    @Override
    public void update(Purchase t) {
        JdbcTemplate bd = new JdbcTemplate(DBConnPool.getInstance().getDataSource());
        bd.update(Querys.UPDATE_purchase_QUERY, t.getDate(), t.getIdPurchase());
    }

    @Override
    public boolean delete(Purchase t) {
        JdbcTemplate bd = new JdbcTemplate(DBConnPool.getInstance().getDataSource());
        bd.update(Querys.DELETE_purchases_QUERY,t.getIdPurchase());
        return true;
    }

    @Override
    public List<Purchase> checkPurchaseByIdItem(int idItem) {
        JdbcTemplate bd = new JdbcTemplate(DBConnPool.getInstance().getDataSource());
        List<Purchase> result = bd.query(Querys.SELECT_purchasesIdItem_QUERY, BeanPropertyRowMapper.newInstance(Purchase.class), idItem);
        return result.isEmpty() ? null : result;
    }

    @Override
    public List<Purchase> getPurchasesByDate(LocalDate date) {
        Date d = Date.valueOf(date);
        JdbcTemplate bd = new JdbcTemplate(DBConnPool.getInstance().getDataSource());
        List<Purchase> result = bd.query(Querys.SELECT_purchasesDate_QUERY, new ProductMapper(), date);
        return result.isEmpty() ? null : result;
    }

    @Override
    public List<Purchase> getPurchasesByIdCostumer(int idCostumer) {
        JdbcTemplate bd = new JdbcTemplate(DBConnPool.getInstance().getDataSource());
        List<Purchase> result = bd.query(Querys.SELECT_purchasesIdCostumer_QUERY,new ProductMapper(), idCostumer);
        return result.isEmpty() ? null : result;
    }

    private static final class ProductMapper implements RowMapper<Purchase>{
        @Override
        public Purchase mapRow(ResultSet rs, int rowNum) throws SQLException {
            Purchase p = new Purchase();
            p.setIdPurchase(rs.getInt("idPurchase"));
            p.setDate(new java.sql.Date(rs.getDate("date").getTime()).toLocalDate());
            Customer c = new Customer(rs.getInt("Customers.idCustomer"),rs.getString("Customers.name"),rs.getString("telephone"),rs.getString("address"));
            Item i = new Item(rs.getInt("Items.idItem"),rs.getString("Items.name"),rs.getString("company"),rs.getFloat("price"));
            p.setCustomer(c);
            p.setItem(i);
            return p;
        }
    }
}
