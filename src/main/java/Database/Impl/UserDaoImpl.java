package Database.Impl;

import Database.Impl.DAO.CRUDDAO;
import Database.SessionFactoryImpl;
import entity.User;
import org.hibernate.HibernateError;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoImpl implements CRUDDAO<User> {

    @Override
    @Deprecated
    public List<User> ReadCompany(int id) {
        return null;
    }

    @Override
    public User Read(int id) {
       User user = null;
        try {
            Session session = SessionFactoryImpl.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            user = session.get(User.class, id);
            tx.commit();
            session.close();
        } catch (NoClassDefFoundError e) {
            System.out.println("Exception: " + e);
        }
        return user;
    }

    @Override
    public List<User> ReadAll() {
        List<User> users = null;
        try {
            users = (List<User>) SessionFactoryImpl.getSessionFactory().
                    openSession().createQuery("FROM User").list();
        } catch (HibernateError error) {
            System.out.println(error);
        }
        ;
        return users;
    }


    @Override
    public boolean DeleteById(int id) {
        try {
            Session session = SessionFactoryImpl.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            User user = new User();
            user.setUserId(id);
            user.setCompanies(null);
            session.delete(user);
            tx.commit();
            session.close();
            return true;
        } catch (NoClassDefFoundError e) {
            System.out.println("Exception: " + e);
        }
        return false;
    }
}
