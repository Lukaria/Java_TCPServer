package service.Impl;

import Database.Impl.DAO.CRUDDAO;

import Database.Impl.UserDaoImpl;
import entity.User;
import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class UserServiceImpl extends SerivceFather<User> {

    CRUDDAO<User> CRUD = new UserDaoImpl();

    @Override
    public int addObject(User obj) {
        return CRUD.Create(obj);
    }

    @Override
    public User readObject(int id) {
        return CRUD.Read(id);
    }

    @Override
    public List<User> readAll() {
        return CRUD.ReadAll();
    }

    @Override
    public boolean updateObject(User obj) {
        return CRUD.Update(obj);
    }

    @Override
    public boolean deleteObject(User obj) {
        return CRUD.Delete(obj);
    }

    @Override
    public boolean deleteObjectById(int id) {
        return CRUD.DeleteById(id);
    }

    public User isExist(User obj) {
        List<User> users = CRUD.ReadAll();
        for (User compare : users) {
            if (obj.getLogin().equals(compare.getLogin()) && obj.getPassword().equals(compare.getPassword())) {
                return compare;
            }
        }
        return null;
    }

    public User isLoginExist(User obj) {
        List<User> users = CRUD.ReadAll();
        for (User compare : users) {
            if (obj.getLogin().equals(compare.getLogin())) return compare;
        }
        return null;
    }

    @Override
    public boolean recordObject(User obj){
        JAXBContext context = null;
        obj.setCompanies(null);
        try {
            context = JAXBContext.newInstance(User.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            String filepath = "src/main/java/files/"+obj.getLogin()+".xml";
            File file = new File(filepath);
            file.createNewFile();
            BufferedWriter writer = new BufferedWriter(new FileWriter(filepath));
            marshaller.marshal(obj, writer);
            writer.close();
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);

        }
        return true;

    }
}
