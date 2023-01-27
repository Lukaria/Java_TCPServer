package Database.Impl;

import Database.Impl.DAO.CRUDDAO;
import Database.SessionFactoryImpl;
import entity.Profit;
import org.hibernate.HibernateError;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;

import java.util.List;

public class ProfitDaoImpl implements CRUDDAO<Profit> {
    @Override
    public List<Profit> ReadCompany(int id) {
        List<Profit> list= null;
        try {
            //list = (List<Liquidity>) .createQuery("FROM Capital WHERE company_id=:id").setParameter("id", id).list();


            Query query = SessionFactoryImpl.getSessionFactory().
                    openSession().createQuery("from Profit where company.id = :id");
            query.setParameter("id", id);
            list = query.getResultList();
        } catch (HibernateError error) {
            System.out.println(error);
        }
        return list;
    }

    @Override
    public Profit Read(int id) {
        Profit profit = null;
        try {
            Session session = SessionFactoryImpl.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            profit = session.get(Profit.class, id);
            tx.commit();
            session.close();
        } catch (NoClassDefFoundError e) {
            System.out.println("Exception: " + e);
        }
        return profit;
    }

    @Override
    public List<Profit> ReadAll() {
        List<Profit> profits = null;
        try {
            profits = (List<Profit>) SessionFactoryImpl.getSessionFactory().
                    openSession().createQuery("FROM Profit").list();
        } catch (HibernateError error) {
            System.out.println(error);
        }
        ;
        return profits;
    }


    @Override
    public boolean DeleteById(int id) {
        try {
            Session session = SessionFactoryImpl.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            Profit profit = new Profit();
            profit.setOperationId(id);
            session.delete(profit);
            tx.commit();
            session.close();

            return true;
        } catch (NoClassDefFoundError e) {
            System.out.println("Exception: " + e);
        }
        return false;
    }
}
