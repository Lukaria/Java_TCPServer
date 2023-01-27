package service.Impl;

import Database.Impl.DAO.CRUDDAO;
import Database.Impl.ProfitDaoImpl;
import entity.Capital;
import entity.Profit;
import org.hibernate.HibernateError;
import exception.CustomException;


import java.util.List;

public class ProfitServiceImpl  extends SerivceFather<Profit> {
    CRUDDAO<Profit> CRUD = new ProfitDaoImpl();

    public Profit getProfit(Capital capital){
        Profit profit = new Profit();
        try {
            profit.setROA(capital.getNetProfit() / capital.getActives() * 100);
            profit.setROS(capital.getNetProfit() / capital.getRWVAT() * 100);
            profit.setROE(capital.getNetProfit() / capital.getNetWorth() * 100);
            profit.setROI(capital.getNetProfit() / capital.getInvestedFunds() * 100);
            profit.setYear(capital.getYear());

            profit.setCompany(capital.getCompany());
            profit.setOperationId(capital.getOperationId());

        }catch (HibernateError e){
            CustomException.showNotice(e);
        }
        return profit;
    }

    @Override
    public int addObject(Profit obj) {
        return CRUD.Create(obj);
    }

    @Override
    public Profit readObject(int id) {
        return CRUD.Read(id);
    }

    @Override
    public List<Profit> readAll() {
        return CRUD.ReadAll();
    }

    @Override
    public boolean updateObject(Profit obj) {
        return CRUD.Update(obj);
    }

    @Override
    public boolean deleteObject(Profit obj) {
        return CRUD.Delete(obj);
    }

    @Override
    public boolean deleteObjectById(int id) {
        return CRUD.DeleteById(id);
    }

    @Override
    public List<Profit> ReadCompany(int id){
        return CRUD.ReadCompany(id);
    }
}
