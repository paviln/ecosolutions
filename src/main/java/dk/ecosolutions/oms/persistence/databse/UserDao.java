package dk.ecosolutions.oms.persistence.databse;

import dk.ecosolutions.oms.domain.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDao implements Dao<User> {
    public Object get(int id) {
        try {
            Connection connection = Database.getConnection();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE user_id=" + id);
            if (rs.next()) {
                return extractUser(rs);
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

    }

    private User extractUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setName(rs.getString("name").trim());
        user.setEmail(rs.getString("email").trim());
        user.setPassword(rs.getString("password").trim());
        user.setRole_id(rs.getInt("role_id"));
        user.setLocation_id(rs.getInt("location_id"));
        return user;
    }

}
