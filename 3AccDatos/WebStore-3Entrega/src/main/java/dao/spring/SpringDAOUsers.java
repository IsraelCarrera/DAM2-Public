package dao.spring;

import dao.DAOUsers;
import dao.DBConnPool;
import model.Item;
import model.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import utils.Querys;

import java.util.List;

public class SpringDAOUsers implements DAOUsers {
    @Override
    public User getUserById(int id) {
        JdbcTemplate bd = new JdbcTemplate(DBConnPool.getInstance().getDataSource());
        List<User> result = bd.query(Querys.SELECT_usersIdUser_QUERY, BeanPropertyRowMapper.newInstance(User.class), id);
        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public User getUserLoggin(String username, String password) {
        JdbcTemplate bd = new JdbcTemplate(DBConnPool.getInstance().getDataSource());
        List<User> result = bd.query(Querys.SELECT_userLogin_QUERY, BeanPropertyRowMapper.newInstance(User.class), username,password);
        return result.isEmpty() ? null : result.get(0);
    }
}
