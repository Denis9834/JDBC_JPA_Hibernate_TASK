package overridetech.jdbc.jpa.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import overridetech.jdbc.jpa.model.User;

import java.util.ArrayList;
import java.util.List;

import static overridetech.jdbc.jpa.util.Util.*;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        String create = "CREATE TABLE IF NOT EXISTS Users (" +
                "id SERIAL PRIMARY KEY, " +
                "name VARCHAR(50), " +
                "lastName VARCHAR(50), " +
                "age SMALLINT)";

        Session session = null;
        Transaction transaction = null;
        try {
            session = getSession();
            transaction = session.beginTransaction();
            session.createSQLQuery(create).executeUpdate();
            System.out.println("Таблица создана успешно.");
            transaction.commit();
        } catch (Exception e) {
            throw new RuntimeException("Не удачная попытка создания таблицы");
        } finally {
            closeSession(session);
        }
    }

    @Override
    public void dropUsersTable() {
        Session session = null;
        Transaction transaction = null;
        try {
            String delete = "DROP TABLE IF EXISTS Users";
            session = getSession();
            transaction = session.beginTransaction();
            session.createSQLQuery(delete).executeUpdate();
            System.out.println("Таблица удалена");
            transaction.commit();
        } catch (Exception e) {
            throw new RuntimeException("Не удачная попытка удаления таблицы");
        } finally {
            closeSession(session);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = getSession();
            transaction = session.beginTransaction();
            User user = new User();
            user.setName(name);
            user.setLastName(lastName);
            user.setAge(age);
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            throw new RuntimeException("Ошибка добавления пользователя в БД");
        } finally {
            closeSession(session);
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = getSession();
            transaction = session.beginTransaction();
            User user = (User) session.get(User.class, id);
            if (user != null) {
                session.delete(user);
            }
            transaction.commit();
            System.out.printf("Пользователь с id %d, успешно удален.", id);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при удалении пользователя по id");
        } finally {
            closeSession(session);
        }
    }

    @Override
    public List<User> getAllUsers() {
        Session session = null;
        try {
            session = getSession();
            return session.createQuery("from User").list();
        } finally {
            closeSession(session);
        }
    }

    @Override
    public void cleanUsersTable() {
        Session session = null;
        Transaction transaction = null;
        try {
            session = getSession();
            transaction = session.beginTransaction();
            session.createSQLQuery("TRUNCATE TABLE Users").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при очистки БД");
        } finally {
            closeSession(session);
        }
    }
}
