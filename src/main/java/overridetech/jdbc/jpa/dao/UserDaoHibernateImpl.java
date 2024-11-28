package overridetech.jdbc.jpa.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import overridetech.jdbc.jpa.model.User;
import overridetech.jdbc.jpa.util.Util;

import java.util.ArrayList;
import java.util.List;

import static overridetech.jdbc.jpa.util.Util.*;

public class UserDaoHibernateImpl implements UserDao {
    private SessionFactory sessionFactory;

    public UserDaoHibernateImpl() {
        this.sessionFactory = Util.getSessionFactory();
    }

    @Override
    public void createUsersTable() {
        String create = "CREATE TABLE IF NOT EXISTS Users (" +
                "id SERIAL PRIMARY KEY, " +
                "name VARCHAR(50), " +
                "lastName VARCHAR(50), " +
                "age SMALLINT)";
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createNativeQuery(create).executeUpdate();
            System.out.println("Таблица создана успешно.");
            transaction.commit();
        } catch (Exception e) {
            throw new RuntimeException("Не удачная попытка создания таблицы");
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            String delete = "DROP TABLE IF EXISTS Users";
            session.createNativeQuery(delete).executeUpdate();
            System.out.println("Таблица удалена");
            transaction.commit();
        } catch (Exception e) {
            throw new RuntimeException("Не удачная попытка удаления таблицы");
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            User user = new User();
            user.setName(name);
            user.setLastName(lastName);
            user.setAge(age);
            System.out.println("добавление user");
            session.persist(user);
            transaction.commit();
        } catch (Exception e) {
            throw new RuntimeException("Ошибка добавления пользователя в БД");
        }
    }

    @Override
    public void removeUserById(long id) {

        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.delete(user);
            }
            transaction.commit();
            System.out.printf("Пользователь с id %d, успешно удален.", id);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при удалении пользователя по id");
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Users", User.class).list();
        }
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createNativeQuery("TRUNCATE TABLE Users").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при очистки БД");
        }
    }
}
