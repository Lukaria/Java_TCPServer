package Database.Impl.DAO;

import Database.SessionFactoryImpl;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public interface CRUDDAO<T> {

    default int Create(T obj) {
        try {
            Session session = SessionFactoryImpl.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            int id = (int)session.save(obj);
            tx.commit();
            session.close();
            return id;
        } catch (NoClassDefFoundError e) {
            System.out.println("Exception: " + e);
        }
        return 0;
    }

    public List<T> ReadCompany(int id);
    public T Read(int id);

    public List<T> ReadAll();


    default boolean Update(T obj) {
        try {
            Session session = SessionFactoryImpl.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            session.update(obj);
            tx.commit();
            session.close();
            return true;
        } catch (NoClassDefFoundError e) {
            System.out.println("Exception: " + e);
        }
        return false;
    }

    default boolean Delete(T obj) {
        try {
            Session session = SessionFactoryImpl.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            session.delete(obj);
            tx.commit();
            session.close();

            return true;
        } catch (NoClassDefFoundError e) {
            System.out.println("Exception: " + e);
        }
        return false;
    }

    public boolean DeleteById(int id);

}
