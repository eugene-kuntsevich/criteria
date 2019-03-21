import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import pojo.City;
import pojo.Language;
import pojo.Localization;

import java.util.Properties;

public class MainClassHibernateWithAnnotation {

    private static final SessionFactory concreteSessionFactory;

    static {
        try {
            Properties prop = new Properties();
            prop.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/city2");
            prop.setProperty("hibernate.connection.username", "root");
            prop.setProperty("hibernate.connection.password", "root");
            prop.setProperty("dialect", "org.hibernate.dialect.MySQLDialect");

            concreteSessionFactory = new Configuration()
                    .addProperties(prop)
                    .addAnnotatedClass(City.class)
                    .addAnnotatedClass(Language.class)
                    .addAnnotatedClass(Localization.class)
                    .buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session getSession() throws HibernateException {
        return concreteSessionFactory.openSession();
    }

    public static void main(String... args) {
        Session session = getSession();
        session.beginTransaction();
        City city = (City) session.get(City.class, new Long(1));
        System.out.println(city.getName());
        session.close();
    }
}
