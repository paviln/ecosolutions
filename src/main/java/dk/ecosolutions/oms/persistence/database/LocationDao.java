package dk.ecosolutions.oms.persistence.database;

import dk.ecosolutions.oms.application.enums.Type;
import dk.ecosolutions.oms.domain.Location;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LocationDao implements Dao<Location> {
    @Override
    public Location get(int id) {
        try {
            Connection connection = Database.getConnection();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM locations WHERE id=" + id);
            if (rs.next()) {
                Location location = extractLocation(rs);
                connection.close();
                return location;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Location> all() {
        try {
            Connection connection = Database.getConnection();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM locations");
            List<Location> locations = new ArrayList<>();
            while (rs.next()) {
                Location location = extractLocation(rs);
                locations.add(location);
            }
            connection.close();

            Collections.sort(locations);

            return locations;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void save(Location location) {
        try {
            Connection connection = Database.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO locations (name, priority, type_id, address_id) VALUES (?, ?, ?, ?)");
            preparedStatement.setString(1, location.getName());
            preparedStatement.setInt(2, location.getPriority());
            switch (location.getType()) {
                case CLEANING_CENTRAL:
                    preparedStatement.setInt(3, 1);
                    break;
                case DELIVERY_POINT:
                    preparedStatement.setInt(3, 2);
                    break;
            }
            preparedStatement.setInt(4, location.getAddress().getId());
            preparedStatement.execute();
            connection.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void update(Location location) {
        try {
            Connection connection = Database.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE locations set name = ?, priority = ?, type_id = ?, address_id = ? where id = ?");
            preparedStatement.setString(1, location.getName());
            preparedStatement.setInt(2, location.getPriority());
            switch (location.getType()) {
                case CLEANING_CENTRAL:
                    preparedStatement.setInt(3, 1);
                    break;
                case DELIVERY_POINT:
                    preparedStatement.setInt(3, 2);
                    break;
            }
            preparedStatement.setInt(4, location.getAddress().getId());
            preparedStatement.setInt(5, location.getId());
            preparedStatement.execute();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Location location) {
        try {
            Connection connection = Database.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM locations WHERE id=?");
            preparedStatement.setInt(1, location.getId());
            preparedStatement.execute();
            connection.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    private Location extractLocation(ResultSet rs) throws SQLException {
        Location location = new Location();
        location.setId(rs.getInt("id"));
        location.setName(rs.getString("name").trim());
        location.setPriority(rs.getInt("priority"));

        switch (rs.getInt("type_id")) {
            case 1:
                location.setType(Type.CLEANING_CENTRAL);
                break;
            case 2:
                location.setType(Type.DELIVERY_POINT);
                break;
        }
        AddressDao addressDao = new AddressDao();
        location.setAddress(addressDao.get(rs.getInt("address_id")));
        return location;
    }
}
