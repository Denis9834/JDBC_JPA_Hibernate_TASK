package overridetech.jdbc.jpa;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import overridetech.jdbc.jpa.dao.UserDaoHibernateImpl;
import overridetech.jdbc.jpa.model.User;
import overridetech.jdbc.jpa.service.UserService;
import overridetech.jdbc.jpa.service.UserServiceImpl;
import overridetech.jdbc.jpa.util.Util;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();
        userService.createUsersTable();

        userService.dropUsersTable();
        userService.dropUsersTable();

        userService.createUsersTable();
        userService.saveUser("Ivan", "Ivanov", (byte) 34);
        userService.saveUser("Petr", "Petrov", (byte) 54);
        userService.saveUser("Semen", "Sidorov", (byte) 65);
        userService.saveUser("Grigoriy", "Smirnov", (byte) 24);

        userService.getAllUsers().forEach(System.out::println);

        userService.removeUserById(3);

        userService.getAllUsers().forEach(System.out::println);

        userService.cleanUsersTable();

        userService.dropUsersTable();


//        try {
//            SessionFactory sessionFactory = Util.getSessionFactory();
//            System.out.println("Фабрика создана " + (sessionFactory != null));
//
//            Session session = sessionFactory.openSession();
//            System.out.println("Сессия создана " + (session != null));
//            session.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


//        UserService service = new UserServiceImpl();
//        service.createUsersTable();
//
//        service.saveUser("Ivan", "Ivanov", (byte) 34);
//        service.saveUser("Petr", "Petrov", (byte) 54);
//        service.saveUser("Semen", "Sidorov", (byte) 65);
//        service.saveUser("Grigoriy", "Smirnov", (byte) 24);
//
//        service.getAllUsers().forEach(System.out::println);
//
//        service.cleanUsersTable();
//        service.dropUsersTable();
    }
}
