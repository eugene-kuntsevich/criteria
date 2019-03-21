import java.util.List;
import java.util.Iterator;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import pojo.City;

public class MainClassHibernate {

    private static SessionFactory factory;

    public static void main(String[] args) {

        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }

        MainClassHibernate mainClassHibernate = new MainClassHibernate();

        /* List down all the cities */
        mainClassHibernate.listCities();
    }

    /* Method to  READ all the cities */
    public void listCities( ){
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            List cities = session.createQuery("FROM City").list();
            for (Iterator iterator = cities.iterator(); iterator.hasNext();){
                City city = (City) iterator.next();
                System.out.print("City : " + city.getName() + "\n");
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
