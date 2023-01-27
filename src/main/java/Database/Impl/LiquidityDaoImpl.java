package Database.Impl;

import Database.Impl.DAO.CRUDDAO;
import Database.SessionFactoryImpl;
import entity.*;
import org.hibernate.HibernateError;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;

import java.util.List;

public class LiquidityDaoImpl implements CRUDDAO<Liquidity> {

    @Override
    public List<Liquidity> ReadCompany(int id) {
        List<Liquidity> list= null;
        try {
            //list = (List<Liquidity>) .createQuery("FROM Capital WHERE company_id=:id").setParameter("id", id).list();


            Query query = SessionFactoryImpl.getSessionFactory().
                    openSession().createQuery("from Liquidity where company.id = :id");
            query.setParameter("id", id);
            list = query.getResultList();
        } catch (HibernateError error) {
            System.out.println(error);
        }
        return list;
    }

    @Override
    public Liquidity Read(int id) {
        Liquidity liquidity = null;
        try {
            Session session = SessionFactoryImpl.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            liquidity = session.get(Liquidity.class, id);
            tx.commit();
            session.close();
        } catch (NoClassDefFoundError e) {
            System.out.println("Exception: " + e);
        }
        return liquidity;
    }

    @Override
    public List<Liquidity> ReadAll() {
        List<Liquidity> liquidities = null;
        try{ liquidities = (List<Liquidity>)SessionFactoryImpl.getSessionFactory().
                openSession().createQuery("FROM Liquidity ").list();}
        catch (HibernateError error){
            System.out.println(error);
        };
        return liquidities;
    }


    @Override
    public boolean DeleteById(int id) {
        try {
            Session session = SessionFactoryImpl.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            Liquidity liquidity = new Liquidity();
            liquidity.setOperationId(id);
            session.delete(liquidity);
            tx.commit();
            session.close();

            return true;
        }
        catch (NoClassDefFoundError e) {
            System.out.println("Exception: " + e);
        }
        return false;
    }
}
