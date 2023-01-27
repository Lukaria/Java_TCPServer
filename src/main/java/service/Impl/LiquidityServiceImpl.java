package service.Impl;

import Database.Impl.DAO.CRUDDAO;
import Database.Impl.LiquidityDaoImpl;
import entity.Apives;
import entity.Currency;
import entity.Liquidity;
import org.hibernate.HibernateError;
import exception.CustomException;

import java.util.List;

public class LiquidityServiceImpl extends SerivceFather<Liquidity> {
    CRUDDAO<Liquidity> CRUD = new LiquidityDaoImpl();

    public LiquidityServiceImpl() {
    }

    ;

    public Liquidity getLiquidity(Currency currency, Apives ap1, Apives ap2) {
        Liquidity liquidity = null;
        try {
            if ((!ap1.isActive() && !ap2.isActive()) || (ap1.isActive() && ap2.isActive())) {
                throw new CustomException("Оба объекта являются пассивами или активами!");
            } else if (ap1.getYear() != ap2.getYear()) {
                throw new CustomException("Года данных не совпадают! Получены " + ap1.getYear() + " и " + ap2.getYear());
            } else if (!ap1.isActive() && ap2.isActive()) {
                Apives buff = ap1;
                buff.setCompany(ap1.getCompany());
                ap1 = ap2;
                ap2 = buff;
                ap1.setCompany(buff.getCompany());

            }
            float devider = (ap2.getAP1() + ap2.getAP2());
            liquidity = new Liquidity();
            liquidity.setCurrentRatio(ap1.getAP1() + ap1.getAP2() - ap2.getAP1() - ap2.getAP2());
            liquidity.setExpectedRatio(ap1.getAP3() - ap2.getAP3());
            if(devider == 0){
                liquidity.setAbsoluteRatio(0);
            }
            else{
                liquidity.setAbsoluteRatio(ap1.getAP1() / devider);
            }

            devider = (ap2.getAP1() + ap2.getAP2());
            if(devider == 0){
                liquidity.setQuickRatio(0);
            }
            else{
                liquidity.setQuickRatio((ap1.getAP1() + ap1.getAP2()) / devider);
            }
            devider = (ap2.getAP1() + ap2.getAP2());
            if(devider == 0){
                liquidity.setCurrentRatioCoeff(0);
            }
            else{
                liquidity.setCurrentRatioCoeff((ap1.getAP1() + ap1.getAP2() + ap1.getAP3()) / devider);
            }

            liquidity.setStateOfBalance((ap1.getAP1() > ap2.getAP1()) && (ap1.getAP2() > ap2.getAP2())
                    && (ap1.getAP3() > ap1.getAP3()) && (ap1.getAP4() <= ap2.getAP4()));

            liquidity.setCompany(ap1.getCompany());
            liquidity.setOperationId(ap1.getOperationId());
            liquidity.setCurrency(currency);
            liquidity.setYear(ap1.getYear());

        } catch (CustomException e) {
            CustomException.showNotice();
        } catch (HibernateError e) {
            CustomException.showNotice(e);
        }
        return liquidity;
    }

    @Override
    public int addObject(Liquidity obj) {
        return CRUD.Create(obj);
    }

    @Override
    public Liquidity readObject(int id) {
        return CRUD.Read(id);
    }

    @Override
    public List<Liquidity> readAll() {
        return CRUD.ReadAll();
    }

    @Override
    public boolean updateObject(Liquidity obj) {
        return (CRUD.Update(obj));
    }

    @Override
    public boolean deleteObject(Liquidity obj) {
        return CRUD.Delete(obj);
    }

    @Override
    public boolean deleteObjectById(int id) {
        return CRUD.DeleteById(id);
    }

    @Override
    public List<Liquidity> ReadCompany(int id){
        return CRUD.ReadCompany(id);
    }
}
