import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.impl.CriteriaImpl;
import org.hibernate.loader.criteria.CriteriaJoinWalker;
import org.hibernate.loader.criteria.CriteriaQueryTranslator;
import org.hibernate.persister.entity.OuterJoinLoadable;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;
import pojo.City;
import pojo.Language;
import pojo.Localization;

import javax.persistence.criteria.JoinType;
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


    public static void main(String[] args) {
        Session session = getSession();
        session.beginTransaction();

        Criteria criteria = session.createCriteria(City.class)
                .createAlias("localizations", "ls", JoinType.LEFT.ordinal())
                .add(Restrictions.or(
                        Restrictions.isNull("ls.language.langId"),
                        Restrictions.eq("ls.language.langId", 2L)))
                .setProjection(Projections.sqlProjection("coalesce (value, name) as value", new String[]{"value"}, new Type[]{new StringType()}))
                .addOrder(Order.asc("ls.value"));
        
        printQuery(criteria);

        List results = criteria.list();

        System.out.println();

        for (Object object : results) {

            System.out.println(((String) object));

            System.out.println("-----------");
        }

        session.close();
    }

    private static void printQuery(Criteria criteria) {
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
    }

    private static Session getSession() throws HibernateException {
        return concreteSessionFactory.openSession();
    }

}

































