package Database;

import entity.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class SessionFactoryImpl {
    private static SessionFactory sessionFactory;

    private SessionFactoryImpl() {}

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration().configure();
                configuration.addAnnotatedClass(Company.class);
                configuration.addAnnotatedClass(Apives.class);
                configuration.addAnnotatedClass(Capital.class);
                configuration.addAnnotatedClass(Currency.class);
                configuration.addAnnotatedClass(Liquidity.class);
                configuration.addAnnotatedClass(Profit.class);
                configuration.addAnnotatedClass(User.class);
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(builder.build());

            } catch (Exception e) {
                System.out.println("Исключение!" + e);
            }
        }
        return sessionFactory;
    }
}
