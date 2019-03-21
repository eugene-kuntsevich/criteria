import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import pojo.City;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Iterator;
import java.util.List;

public class MainClassCriteria {

    private static SessionFactory factory;
    @PersistenceUnit
    private static EntityManagerFactory emfactory;

    public static void main(String[] args) {

        // = Persistence.createEntityManagerFactory("com.example");

        try {
            System.out.println("START...");
            EntityManager entitymanager = emfactory.createEntityManager();

            CriteriaBuilder criteriaBuilder = entitymanager.getCriteriaBuilder();

            CriteriaQuery<City> criteriaQuery = criteriaBuilder.createQuery(City.class);//create query object

            Root<City> cityRoot = criteriaQuery.from(City.class);//get object representing 'from' part

            criteriaQuery.select(cityRoot);//linking 'select' and 'from' parts, equivalent to 'select c from City c;'

            TypedQuery<City> typedQuery = entitymanager.createQuery(criteriaQuery);

            typedQuery.getResultList().forEach(System.out::println);

            entitymanager.close();

            System.out.println("FINISH...");
        } catch (Exception e) {

        } finally {
            emfactory.close();
        }

    }
}

































