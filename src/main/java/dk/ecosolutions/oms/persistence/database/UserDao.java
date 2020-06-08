package dk.ecosolutions.oms.persistence.database;

import dk.ecosolutions.oms.application.enums.Role;
import dk.ecosolutions.oms.domain.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao implements Dao<User> {
    public User get(int id) {
        try {
            Connection con = Database.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM users WHERE id=?");
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
            PreparedStatement ps = con.prepareStatement("INSERT INTO users (name, email, password, role_id, location_id) VALUES (?, ?, ?, ?, ?)");
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            switch (user.getRole()) {
                case OWNER:
                    ps.setInt(4, 1);
                    break;
                case ASSISTENT:
                    ps.setInt(4, 2);
                    break;
                case DRIVER:
                    ps.setInt(4, 3);
                    break;
                case WORKER:
                    ps.setInt(4, 4);
                    break;
            }
            ps.setInt(5, user.getLocation().getId());
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
        User user = new User();
        try {
            user.setId(rs.getInt("id"));
            user.setName(rs.getString("name").trim());
            user.setEmail(rs.getString("email").trim());
            user.setPassword(rs.getString("password").trim());
            switch (rs.getInt("role_id")) {
                case 1:
                    user.setRole(Role.OWNER);
                    break;
                case 2:
                    user.setRole(Role.ASSISTENT);
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
}
