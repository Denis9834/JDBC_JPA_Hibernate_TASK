package overridetech.jdbc.jpa;

import overridetech.jdbc.jpa.service.UserService;
import overridetech.jdbc.jpa.service.UserServiceImpl;
import overridetech.jdbc.jpa.util.Util;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        UserService service = new UserServiceImpl();
        service.createUsersTable();

        service.saveUser("Ivan", "Ivanov", (byte) 34);
        service.saveUser("Petr", "Petrov", (byte) 54);
        service.saveUser("Semen", "Sidorov", (byte) 65);
        service.saveUser("Grigoriy", "Smirnov", (byte) 24);

        service.getAllUsers().forEach(System.out::println);

        service.cleanUsersTable();
        service.dropUsersTable();
    }
}
