package service.Impl;

import Database.Impl.DAO.CRUDDAO;
import Database.Impl.ApivesDaoImpl;
import entity.Apives;

import java.util.List;

public class ApivesServiceImpl extends SerivceFather<Apives> {

    CRUDDAO<Apives> CRUD = new ApivesDaoImpl();

    @Override
    public int addObject(Apives obj) {
        return CRUD.Create(obj);
    }
    @Override
    public Apives readObject(int id) {
        return CRUD.Read(id);
    }
    @Override
    public List<Apives> readAll() {
        return CRUD.ReadAll();
    }

    @Override
    public boolean updateObject(Apives obj) {
        return CRUD.Update(obj);
    }
    @Override
    public boolean deleteObject(Apives obj) {
        return CRUD.Delete(obj);
    }

    @Override
    public boolean deleteObjectById(int id) {
        return CRUD.DeleteById(id);
    }

    public List<Apives> ReadCompany(int id){
        return CRUD.ReadCompany(id);
    }
}
