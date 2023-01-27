package Database.Impl;

import Database.Impl.DAO.CRUDDAO;
import Database.SessionFactoryImpl;
import entity.Apives;

import org.hibernate.HibernateError;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;

import java.util.List;

public class ApivesDaoImpl implements CRUDDAO<Apives> {
    @Override
    public List<Apives> ReadCompany(int id) {
        List<Apives> list= null;
        try {
            Query query = SessionFactoryImpl.getSessionFactory().
                    openSession().createQuery("from Apives where company.id = :id");
            query.setParameter("id", id);
            list = query.getResultList();
        } catch (HibernateError error) {
            System.out.println(error);
        }
        return list;
    }

    @Override
    public Apives Read(int id) {
        Apives apives = null;
        try {
            Session session = SessionFactoryImpl.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            apives= session.get(Apives.class, id);
            tx.commit();
            session.close();
        } catch (NoClassDefFoundError e) {
            System.out.println("Exception: " + e);
        }
        return apives;
    }


    @Override
    public List<Apives> ReadAll() {
        List<Apives> apives = null;
        try {
            apives = (List<Apives>) SessionFactoryImpl.getSessionFactory().
                    openSession().createQuery("FROM Apives").list();
        } catch (HibernateError error) {
            System.out.println(error);
        }
        ;
        return apives;
    }


    @Override
    public boolean DeleteById(int id) {
        try {
            Session session = SessionFactoryImpl.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            Apives apives = new Apives();
            apives.setOperationId(id);
            session.delete(apives);
            tx.commit();
            session.close();
            return true;
        } catch (NoClassDefFoundError e) {
            System.out.println("Exception: " + e);
        }
        return false;
    }
}
