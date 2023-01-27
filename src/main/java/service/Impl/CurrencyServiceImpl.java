package service.Impl;

import Database.Impl.DAO.CRUDDAO;
import Database.Impl.CurrencyDaoImpl;

import entity.Currency;
import entity.User;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CurrencyServiceImpl extends SerivceFather<Currency> {

    CRUDDAO<Currency> CRUD = new CurrencyDaoImpl();
    @Override
    public int addObject(Currency obj) {
        return CRUD.Create(obj);
    }
    @Override
    public Currency readObject(int id) {
        return CRUD.Read(id);
    }
    @Override
    public List<Currency> readAll() {
        return CRUD.ReadAll();
    }
    @Override
    public boolean updateObject(Currency obj) {
        return CRUD.Update(obj);
    }
    @Override
    public boolean deleteObject(Currency obj) {
        return CRUD.Delete(obj);
    }
    @Override
    public boolean deleteObjectById(int id) {
        return CRUD.DeleteById(id);
    }
    @Override
    public boolean recordObject(Currency obj){
        JAXBContext context = null;
        obj.setLiquidities(null);
        try {
            context = JAXBContext.newInstance(User.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            String filepath = "src/main/java/files/currency"+obj.getCurrencyCode()+".xml";
            File file = new File(filepath);
            file.createNewFile();
            BufferedWriter writer = new BufferedWriter(new FileWriter(filepath));
            marshaller.marshal(obj, writer);
            writer.close();
        } catch (JAXBException | IOException e) {
            throw new RuntimeException(e);
        }
        return true;

    }

}
