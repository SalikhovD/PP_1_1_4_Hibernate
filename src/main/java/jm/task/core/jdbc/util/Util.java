package jm.task.core.jdbc.util;

import java.util.Properties;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;


public class Util {

    private static final String DB_HOST = "localHost";
    private static final String DB_PORT = "3306";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "root";
    private static final String DB_NAME = "katatraining";


    private static SessionFactory factory = configureFactory();

    public static Session getSession() {
        return factory.getCurrentSession();
    }

    public static Session openSession() {
        return factory.openSession();
    }

    private static SessionFactory configureFactory() {
        SessionFactory sessionfactory = null;
        try {
            Configuration configuration = new Configuration();
            Properties properties = new Properties();
            properties.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
            properties.put(Environment.URL, "jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME + "?useSSL=false");
            properties.put(Environment.USER, DB_USER);
            properties.put(Environment.PASS, DB_PASS);
            properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
            properties.put(Environment.SHOW_SQL, "true");
            properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
            configuration.setProperties(properties);
            configuration.addAnnotatedClass(User.class);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();

            sessionfactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sessionfactory;
    }
}
