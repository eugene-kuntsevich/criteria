import pojo.City;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class MainClass {

    public static void main(String[] args) {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("example-unit");

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
