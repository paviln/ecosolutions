package dk.ecosolutions.oms.persistence.database;

import dk.ecosolutions.oms.domain.Role;
import dk.ecosolutions.oms.domain.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * User model implementation of DAO interface.
 *
 * @author Jens Christensen
 * @version 1.0
 * @since 1.0
 */

public class UserDao implements Dao<User> {
    public User get(int id) {
        try {
            Connection con = Database.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM users WHERE id=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            con.close();

            if (rs.next()) {
                User user = extractUser(rs);

                return user;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<User> all() {
        try {
            Connection con = Database.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users");
            List<User> users = new ArrayList<User>();
            while (rs.next()) {
                User user = extractUser(rs);
                users.add(user);
            }
            con.close();

            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void save(User user) {
        try {
            Connection con = Database.getConnection();
            PreparedStatement ps = con.prepareStatement("INSERT INTO users (name, phone, email, password, role_id, location_id) VALUES (?, ?, ?, ?, ?, ?)");
            ps.setString(1, user.getName());
            ps.setString(2, user.getPhone());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPassword());
            switch (user.getRole()) {
                case OWNER:
                    ps.setInt(5, 1);
                    break;
                case ASSISTANT:
                    ps.setInt(5, 2);
                    break;
                case DRIVER:
                    ps.setInt(5, 3);
                    break;
                case WORKER:
                    ps.setInt(5, 4);
                    break;
            }
            ps.setInt(6, user.getLocation().getId());
            ps.execute();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(User user) {

    }

    public void delete(User user) {
        try {
            Connection con = Database.getConnection();
            PreparedStatement preparedStatement = con.prepareStatement("DELETE FROM users WHERE id=?");
            preparedStatement.setInt(1, user.getId());
            preparedStatement.execute();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private User extractUser(ResultSet rs) {
        try {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setName(rs.getString("name").trim());
            user.setPhone(rs.getString("phone").trim());
            user.setEmail(rs.getString("email").trim());
            user.setPassword(rs.getString("password").trim());
            switch (rs.getInt("role_id")) {
                case 1:
                    user.setRole(Role.OWNER);
                    break;
                case 2:
                    user.setRole(Role.ASSISTANT);
                    break;
                case 3:
                    user.setRole(Role.DRIVER);
                    break;
                case 4:
                    user.setRole(Role.WORKER);
                    break;
            }
            LocationDao locationDao = new LocationDao();
            user.setLocation(locationDao.get(rs.getInt("location_id")));

            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
