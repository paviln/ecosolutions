package dk.ecosolutions.oms.persistence.database;

import dk.ecosolutions.oms.application.enums.Role;
import dk.ecosolutions.oms.domain.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao implements Dao<User> {
    public User get(int id) {
        try {
            Connection connection = Database.getConnection();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE user_id=" + id);
            if (rs.next()) {
                User user = extractUser(rs);
                connection.close();
                return user;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<User> all() {
        try {
            Connection connection = Database.getConnection();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users");
            List<User> users = new ArrayList<User>();
            while (rs.next()) {
                User user = extractUser(rs);
                users.add(user);
            }
            connection.close();
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void save(User user) {

    }

    public void update(User user) {

    }

    public void delete(User user) {
        try {
            Connection connection = Database.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM users WHERE id=?");
            preparedStatement.setInt(1, user.getId());
            preparedStatement.execute();
            connection.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    private User extractUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setName(rs.getString("name").trim());
        user.setEmail(rs.getString("email").trim());
        user.setPassword(rs.getString("password").trim());
        switch (rs.getInt("role_id")) {
            case 1:
                user.setRole(Role.owner);
                break;
            case 2:
                user.setRole(Role.assistent);
                break;
        }
        user.setLocation_id(rs.getInt("location_id"));
        return user;
    }
}
