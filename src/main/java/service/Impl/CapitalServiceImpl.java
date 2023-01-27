package service.Impl;

import Database.Impl.DAO.CRUDDAO;
import Database.Impl.CapitalDaoImpl;

import entity.Capital;


import java.util.List;

public class CapitalServiceImpl  extends SerivceFather<Capital> {

    CRUDDAO<Capital> CRUD = new CapitalDaoImpl();
    @Override
    public int addObject(Capital obj) {
        return CRUD.Create(obj);
    }
    @Override
    public Capital readObject(int id) {
        return CRUD.Read(id);
    }
    @Override
    public List<Capital> readAll() {
        return CRUD.ReadAll();
    }
    @Override
    public boolean updateObject(Capital obj) {
        return CRUD.Update(obj);
    }
    @Override
    public boolean deleteObject(Capital obj) {
        return CRUD.Delete(obj);
    }
    @Override
    public boolean deleteObjectById(int id) {
        return CRUD.DeleteById(id);
    }

    @Override
    public List<Capital> ReadCompany(int id){
        return CRUD.ReadCompany(id);
    }
}
