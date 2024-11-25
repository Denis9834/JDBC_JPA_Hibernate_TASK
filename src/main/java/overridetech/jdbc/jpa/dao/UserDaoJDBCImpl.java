package overridetech.jdbc.jpa.dao;

import overridetech.jdbc.jpa.model.User;
import overridetech.jdbc.jpa.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String create = "CREATE TABLE IF NOT EXISTS Users (" +
                "id SERIAL PRIMARY KEY, " +
                "name VARCHAR(100), " +
                "lastName VARCHAR(100), " +
                "age SMALLINT)";

        try (Connection connection = Util.getConnection();
             Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(create);
            System.out.println("Таблица создана");
        } catch (SQLException e) {
            throw new RuntimeException("Не удачная попытка создания таблицы");
        }
    }

    public void dropUsersTable() {
        String delete = "DROP TABLE IF EXISTS Users";

        try (Connection connection = Util.getConnection();
             Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(delete);
            System.out.println("Таблица удалена");
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при удалении таблицы");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String save = "INSERT INTO Users (name, lastName, age) VALUES (?, ?, ?)";

        try (Connection connection = Util.getConnection();
             PreparedStatement stmt = connection.prepareStatement(save)) {
            stmt.setString(1, name);
            stmt.setString(2, lastName);
            stmt.setByte(3, age);
            stmt.executeUpdate();
            System.out.printf("Пользователь - %s %s добавлен в базу данных" + "\n", name, lastName);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка добавления пользователя");
        }
    }

    public void removeUserById(long id) {
        String remove = "DELETE FROM Users WHERE id = ?";
        try (Connection connection = Util.getConnection();
             PreparedStatement stmt = connection.prepareStatement(remove)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
            System.out.printf("Пользователь с id %d, успешно удален.", id);

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при удалении пользователя по id");
        }

    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String allUsers = "SELECT * FROM Users";
        try (Connection connection = Util.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet resultSet = stmt.executeQuery(allUsers)) {
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при выводе таблицы");
        }
        return users;
    }

    public void cleanUsersTable() {
        String clean = "DELETE FROM Users";
        try (Connection connection = Util.getConnection();
             Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(clean);
            System.out.println("Таблица очищена");
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при стирании данных в таблице");
        }
    }
}
