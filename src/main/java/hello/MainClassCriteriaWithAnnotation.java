package hello;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.internal.CriteriaImpl;
import org.hibernate.loader.criteria.CriteriaJoinWalker;
import org.hibernate.loader.criteria.CriteriaQueryTranslator;
import org.hibernate.persister.entity.OuterJoinLoadable;
import org.springframework.stereotype.Service;
import hello.pojo.City;
import hello.pojo.Language;
import hello.pojo.Localization;

import javax.persistence.criteria.JoinType;
import java.util.List;
import java.util.Properties;

@Service
public class MainClassCriteriaWithAnnotation {

    private static final SessionFactory concreteSessionFactory;

    static {
        try {
            Properties prop = new Properties();
            prop.setProperty("hibernate.connection.url", "jdbc:mysql://172.17.0.2:3306/city");
            prop.setProperty("hibernate.connection.username", "root");
            prop.setProperty("hibernate.connection.password", "root");
            prop.setProperty("dialect", "org.hibernate.dialect.MySQLDialect");

            Class.forName("com.mysql.jdbc.Driver");

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


    public static String mainMethod() {
        Session session = getSession();
        session.beginTransaction();

        Criteria criteria = session.createCriteria(City.class, "c")
                .createAlias("localizations", "ls", JoinType.LEFT.ordinal())
                .add(Restrictions.or(
                        Restrictions.isNull("ls.language.langId"),
                        Restrictions.eq("ls.language.langId", 2L)));

        criteria.setProjection(new CoalesceSQLProjection("val", "ls.value", "c.name"));

        criteria.addOrder(OrderBySqlFormula.sqlFormula("val asc"));

        System.out.println(queryAsString(criteria));

        List results = criteria.list();

        System.out.println();

        for (Object object : results) {

            System.out.println(((String) object));

            System.out.println("-----------");
        }

        StringBuilder builder = new StringBuilder();

        for (Object object : results) {
            builder.append(((String) object)).append("\n").append("-----------").append("\n");

        }

        session.close();

        return builder.toString();
    }

    private static String queryAsString(Criteria criteria) {
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

        return walker.getSQLString();
    }

    private static Session getSession() throws HibernateException {
        return concreteSessionFactory.openSession();
    }

}

































