import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.impl.CriteriaImpl;
import org.hibernate.loader.criteria.CriteriaJoinWalker;
import org.hibernate.loader.criteria.CriteriaQueryTranslator;
import org.hibernate.persister.entity.OuterJoinLoadable;
import pojo.City;
import pojo.Language;
import pojo.Localization;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class MainClassCriteriaWithAnnotation {

    private static final SessionFactory concreteSessionFactory;

    static {
        try {
            Properties prop = new Properties();
            prop.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/city");
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

    public static void main(String[] args) {
        Session session = getSession();
        session.beginTransaction();


        Criteria criteria = session.createCriteria(City.class);
        Criteria cr = criteria
                .createCriteria("localizations");
//                .createAlias("localizations", "ls");
                //.createAlias("city", "c")
//                .add(Restrictions.eq("localizations.language", getLanguage()));

        CriteriaImpl criteriaImpl = (CriteriaImpl) criteria;
        SessionImplementor session1 = criteriaImpl.getSession();
        SessionFactoryImplementor factory = session1.getFactory();
        CriteriaQueryTranslator translator = new CriteriaQueryTranslator(factory, criteriaImpl, criteriaImpl.getEntityOrClassName(), CriteriaQueryTranslator.ROOT_SQL_ALIAS);
        String[] implementors = factory.getImplementors(criteriaImpl.getEntityOrClassName());

        CriteriaJoinWalker walker = new CriteriaJoinWalker((OuterJoinLoadable) factory.getEntityPersister(implementors[0]),
                translator,
                factory,
                criteriaImpl,
                criteriaImpl.getEntityOrClassName(),
                session1.getLoadQueryInfluencers());

        String sql = walker.getSQLString();
        System.out.println(sql);

        List<City> results = criteria.list();

        for (City city : results) {
            //System.out.println(city.getName());
            for (Localization l : city.getLocalizations()) {
                //System.out.println(l.getLanguage().getNameLng());
                System.out.println(l.getValue());
            }
            System.out.println("-----------");
        }
    }

    private static Language getLanguage(){
        Criteria criteria = getSession().createCriteria(Language.class);

        criteria.add(Restrictions.eq("langId", 1L));

        List<Language> languages = criteria.list();

        return languages.iterator().next();
    }
}

































