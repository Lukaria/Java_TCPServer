import Database.SessionFactoryImpl;
import entity.Currency;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import service.Impl.CurrencyServiceImpl;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DAOTest {
    private static SessionFactory sessionFactory = null;

    @BeforeAll
    static void configure() {
        try {
            sessionFactory = SessionFactoryImpl.getSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    private static Session getSession() throws HibernateException {
        return sessionFactory.openSession();
    }

    @Test
    @Order(1)
    void toxml() throws JAXBException, IOException {
        Session session = getSession();
        JAXBContext context = null;

        Currency obj = new Currency(999, 999, '!',
                "TST", 99.99f, null);


        try {
            context = JAXBContext.newInstance(Currency.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            String filepath = "src/main/java/files/test.xml";
            File file = new File(filepath);
            file.createNewFile();
            file.deleteOnExit();
            BufferedWriter writer = new BufferedWriter(new FileWriter(filepath));
            marshaller.marshal(obj, writer);
            writer.close();
        } catch (JAXBException | IOException e) {
            fail();
        }
        finally {
            session.close();
        }
    }

    @Test
    @Order(2)
    void create() {
        try{
            Currency obj = (Currency)  JAXBContext.newInstance(Currency.class).
                    createUnmarshaller().unmarshal(new File("src/main/java/files/test.xml"));


            Session session = getSession();
            CurrencyServiceImpl csi = new CurrencyServiceImpl();
            int id = csi.addObject(obj);
            Currency testobj = csi.readObject(id);
            obj.setCurrencyId(id);
            testobj.setLiquidities(null);

            assertEquals(testobj, obj);


            Marshaller marshaller = JAXBContext.newInstance(Currency.class).createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(obj, (new FileWriter("src/main/java/files/test.xml")));

            session.close();
        } catch (JAXBException | IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    @Order(3)
    void update() {
        Session session = getSession();
        try {
            Currency obj = (Currency)  JAXBContext.newInstance(Currency.class).
                    createUnmarshaller().unmarshal(new File("src/main/java/files/test.xml"));

            obj.setCoeff(1010f);
            CurrencyServiceImpl csi = new CurrencyServiceImpl();
            if(!csi.updateObject(obj)) fail();
            Currency testobj = csi.readObject(obj.getCurrencyId());
            testobj.setLiquidities(null);
            assertEquals(testobj, obj);
        } catch (NullPointerException e) {
            fail();
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        } finally {
            session.close();
        }
    }

    @Test
    @Order(4)
    void delete() {
        Session session = getSession();
        try {
            Currency obj = (Currency)  JAXBContext.newInstance(Currency.class).
                    createUnmarshaller().unmarshal(new File("src/main/java/files/test.xml"));

            CurrencyServiceImpl csi = new CurrencyServiceImpl();
            if(!csi.deleteObject(obj)) fail();
            obj = csi.readObject(obj.getCurrencyId());
            assertNull(obj);

        } catch (Exception e) {
            fail();
        }
        session.close();
    }

}

