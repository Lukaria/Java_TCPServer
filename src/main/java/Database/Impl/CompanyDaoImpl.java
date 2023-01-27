package Database.Impl;

import Database.Impl.DAO.CRUDDAO;
import Database.SessionFactoryImpl;
import entity.Company;
import org.hibernate.HibernateError;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;


public class CompanyDaoImpl implements CRUDDAO<Company> {

    @Override
    @Deprecated
    public List<Company> ReadCompany(int id) {
        return null;
    }

    @Override
    public Company Read(int id) {
        Company company = null;
        try {
            Session session = SessionFactoryImpl.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            company = session.get(Company.class, id);
            tx.commit();
            session.close();
        } catch (NoClassDefFoundError e) {
            System.out.println("Exception: " + e);
        }
        return company;
    }

    @Override
    public List<Company> ReadAll() {
        List<Company> companies = null;
        try{ companies = (List<Company>)SessionFactoryImpl.getSessionFactory().
                openSession().createQuery("FROM Company ").list();}
        catch (HibernateError error){
            System.out.println(error);
        };
        return companies;
    }


    @Override
    public boolean DeleteById(int id) {
        try {
            Session session = SessionFactoryImpl.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            Company company = new Company();
            company.setCompanyId(id);
            company.nullAll();
            session.delete(company);
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
