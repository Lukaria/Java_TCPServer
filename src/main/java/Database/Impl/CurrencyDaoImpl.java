package Database.Impl;

import Database.Impl.DAO.CRUDDAO;
import Database.SessionFactoryImpl;
import entity.Currency;

import org.hibernate.HibernateError;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class CurrencyDaoImpl implements CRUDDAO<Currency> {
    @Override
    @Deprecated
    public List<Currency> ReadCompany(int id) {
        return null;
    }

    @Override
    public Currency Read(int id) {
        Currency currency= null;
        try {
            Session session = SessionFactoryImpl.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            currency = session.get(Currency.class, id);
            tx.commit();
            session.close();
        } catch (NoClassDefFoundError e) {
            System.out.println("Exception: " + e);
        }
        return currency;
    }

    @Override
    public List<Currency> ReadAll() {
        List<Currency> currencies= null;
        try{ currencies = (List<Currency>)SessionFactoryImpl.getSessionFactory().
                openSession().createQuery("FROM Currency ").list();}
        catch (HibernateError error){
            System.out.println(error);
        };
        return currencies;
    }


    @Override
    public boolean DeleteById(int id) {
        try {
            Session session = SessionFactoryImpl.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            Currency currency = new Currency();
            currency.setCurrencyId(id);
            currency.setLiquidities(null);
            session.delete(currency);
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
