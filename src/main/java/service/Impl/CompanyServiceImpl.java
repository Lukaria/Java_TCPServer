package service.Impl;

import Database.Impl.DAO.CRUDDAO;

import Database.Impl.CompanyDaoImpl;
import entity.Company;
import java.util.List;

public class CompanyServiceImpl extends SerivceFather<Company> {

    CRUDDAO<Company> CRUD = new CompanyDaoImpl();

    @Override
    public int addObject(Company obj) {
        return CRUD.Create(obj);
    }

    @Override
    public Company readObject(int id) {
        return CRUD.Read(id);
    }

    @Override
    public List<Company> readAll() {
        return CRUD.ReadAll();
    }

    @Override
    public boolean updateObject(Company obj) {
        return CRUD.Update(obj);
    }

    @Override
    public boolean deleteObject(Company obj) {
        return CRUD.Delete(obj);
    }

    @Override
    public boolean deleteObjectById(int id) {
        return CRUD.DeleteById(id);
    }

}
