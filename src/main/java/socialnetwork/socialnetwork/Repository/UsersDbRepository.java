package socialnetwork.socialnetwork.Repository;

import socialnetwork.socialnetwork.Domain.User;

import java.sql.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.Vector;

public class UsersDbRepository {
    private String url;
    private String username;
    private String password;
    private Vector<User> userList;

    public UsersDbRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.userList= new Vector<User>();
        loadFromDb();
        System.out.println("Initialized UserDbRepo...");
    }

    public void loadFromDb() {
        Vector<User> users = new Vector<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from users");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String usrname = resultSet.getString("username");
                String passwd = resultSet.getString("password");
                int age = resultSet.getInt("age");

                User u = new User(usrname, passwd, age);
                u.setId((long) id);
                users.add(u);
            }
            this.userList=users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Vector<User> getUsersList() {
        return userList;
    }

    public void delete(Long id_user) {
        String deleteSQL = "DELETE FROM users WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(deleteSQL)) {
            statement.setLong(1, id_user);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void add(User user) {
        String addSQL = "INSERT INTO users(username, password, age) VALUES(?,?,?)";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(addSQL)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setInt(3, user.getAge());
            statement.executeUpdate();
            this.loadFromDb();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User getUser(Long u1) {
        for(User u : userList){
            if(Objects.equals(u.getId(), u1))return u;
        }
        return null;
    }

    public User getUser(String u1) {
        for(User u : userList){
            if(Objects.equals(u.getUsername(), u1))return u;
        }
        return null;
    }
}
