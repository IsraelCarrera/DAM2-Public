package dao.hibernate;

import dao.DAOUsers;
import dao.HibernateUtils;
import lombok.extern.log4j.Log4j2;
import model.User;
import org.hibernate.Session;
import org.hibernate.query.Query;

@Log4j2
public class HibernateDAOUsers implements DAOUsers {
    Session session;

    //HECHO
    @Override
    public User getUserById(int id) {
        User user = null;
        try{
            session = HibernateUtils.getSession();
            user = session.get(User.class,id);
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }finally {
            session.close();
        }
        return user;
    }

    //HECHO
    @Override
    public User getUserLoggin(String username, String password) {
        User user = null;
        try{
            session = HibernateUtils.getSession();
            user= session
                    .createNamedQuery("get_UserPassword",User.class)
                    .setParameter("nombreUser",username)
                    .setParameter("contra", password)
                    .getSingleResult();
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }finally {
            session.close();
        }
        return user;
    }
}
