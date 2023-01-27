package Database.Impl;

import Database.Impl.DAO.CRUDDAO;
import Database.SessionFactoryImpl;
import entity.Capital;

import org.hibernate.HibernateError;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;

import java.util.List;

public class CapitalDaoImpl implements CRUDDAO<Capital> {
    @Override
    public List<Capital> ReadCompany(int id) {
        List<Capital> list= null;
        try {
            Query query = SessionFactoryImpl.getSessionFactory().
                    openSession().createQuery("from Capital where company.id = :id");
            query.setParameter("id", id);
            list = query.getResultList();
        } catch (HibernateError error) {
            System.out.println(error);
        }
        return list;
    }

    @Override
    public Capital Read(int id) {
        Capital capital = null;
        try {
            Session session = SessionFactoryImpl.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            capital = session.get(Capital.class, id);
            tx.commit();
            session.close();
        } catch (NoClassDefFoundError e) {
            System.out.println("Exception: " + e);
        }
        return capital;
    }

    @Override
    public List<Capital> ReadAll() {
        List<Capital> capitals = null;
        try {
            capitals = (List<Capital>) SessionFactoryImpl.getSessionFactory().
                    openSession().createQuery("FROM Capital ").list();
        } catch (HibernateError error) {
            System.out.println(error);
        }
        return capitals;
    }


    @Override
    public boolean DeleteById(int id) {
        try {
            Session session = SessionFactoryImpl.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            Capital capital = new Capital();
            capital.setOperationId(id);
            session.delete(capital);
            tx.commit();
            session.close();
            return true;
        } catch (NoClassDefFoundError e) {
            System.out.println("Exception: " + e);
        }
        return false;
    }
}
