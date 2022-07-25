package dao.spring;

import dao.DAOCustomers;
import dao.DBConnPool;
import model.Customer;
import model.User;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
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

public class SpringDAOCustomers implements DAOCustomers {
    @Override
    public Customer get(int id) {
        JdbcTemplate bd =new JdbcTemplate(DBConnPool.getInstance().getDataSource());
        return bd.queryForObject(Querys.SELECT_customerID_QUERY, BeanPropertyRowMapper.newInstance(Customer.class),id);
    }

    @Override
    public List<Customer> getAll() {
        JdbcTemplate bd =new JdbcTemplate(DBConnPool.getInstance().getDataSource());
        List<Customer> result = bd.query(Querys.SELECT_allCustomers_QUERY, BeanPropertyRowMapper.newInstance(Customer.class));
        return result.isEmpty() ? null : result;
    }

    @Override
    public int save(Customer t, User u) {
        int id = 0;
        KeyHolder kh = new GeneratedKeyHolder();
        TransactionDefinition txDef = new DefaultTransactionDefinition();
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(DBConnPool.getInstance().getDataSource());
        TransactionStatus txStatus = transactionManager.getTransaction(txDef);
        try{
        JdbcTemplate insert = new JdbcTemplate(transactionManager.getDataSource());
            insert.update(conn -> {
            PreparedStatement ps = conn
                    .prepareStatement(Querys.INSERT_users_QUERY, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, u.getUsername());
            ps.setString(2, u.getPassword());
            return ps;
        }, kh);
            id = kh.getKey().intValue();
            insert.update(Querys.INSERT_customer_QUERY,id,t.getName(),t.getTelephone(),t.getAddress());
            transactionManager.commit(txStatus);
        }catch (DuplicateKeyException e){
            //Preguntar mañana. Porque teniendo el catch me sigue lanzando la Exception en consola.
            transactionManager.rollback(txStatus);
            Logger.getLogger(SpringDAOCustomers.class.getName()).log(Level.SEVERE, null, e);
            id=-1;
        }
        catch (Exception e){
            transactionManager.rollback(txStatus);
            Logger.getLogger(SpringDAOCustomers.class.getName()).log(Level.SEVERE, null, e);

        }
        return id;
    }

    @Override
    public void update(Customer t) {
        JdbcTemplate bd =new JdbcTemplate(DBConnPool.getInstance().getDataSource());
            bd.update(Querys.UPDATE_customer_QUERY, t.getName(), t.getTelephone(), t.getAddress(), t.getIdCustomer());
    }

    @Override
    public boolean delete(Customer t, User u) {
        boolean isDelete= false;
        TransactionDefinition txDef = new DefaultTransactionDefinition();
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(DBConnPool.getInstance().getDataSource());
        TransactionStatus txStatus = transactionManager.getTransaction(txDef);
        try{
            JdbcTemplate bd = new JdbcTemplate(transactionManager.getDataSource());
            bd.update(Querys.DELETE_Customer_QUERY,t.getIdCustomer());
            bd.update(Querys.DELETE_users_QUERY, u.getIdUser());
            transactionManager.commit(txStatus);
            isDelete=true;
        }catch (DataIntegrityViolationException e){
            transactionManager.rollback(txStatus);
            Logger.getLogger(SpringDAOCustomers.class.getName()).log(Level.SEVERE, null, e);
        }

        return isDelete;
    }
}
