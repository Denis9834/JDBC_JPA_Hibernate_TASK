package overridetech.jdbc.jpa.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import overridetech.jdbc.jpa.model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String URL = "jdbc:postgresql://localhost:5432/users_postgres_database";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "postgre";
    private static SessionFactory factory;

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при подключении к базе данных");
        }
    }

    public static SessionFactory getSessionFactory() {
        if (factory == null) {
            try {
                factory = new Configuration()
                        .addAnnotatedClass(User.class)
                        .buildSessionFactory();
            } catch (Exception e) {
                throw new RuntimeException("Ошибка при создании SessionFactory");
            }
        }
        return factory;
    }

    public static Session getSession() {
        return getSessionFactory().openSession();
    }

    public static void closeSession(Session session) {
        if (session != null) {
            session.close();
        }
    }
}
