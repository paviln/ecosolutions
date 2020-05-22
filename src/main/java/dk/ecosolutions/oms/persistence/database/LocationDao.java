package dk.ecosolutions.oms.persistence.database;

import dk.ecosolutions.oms.domain.Location;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LocationDao implements Dao<Location> {
    @Override
    public Location get(int id) {
        return null;
    }

    @Override
    public List<Location> all() {
        try {
            Connection connection = Database.getConnection();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM locations");
            List<Location> locations = new ArrayList<Location>();
            while (rs.next()) {
                Location location = extractLocation(rs);
                locations.add(location);
            }
            connection.close();
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
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO locations (name, type_id, address_id) VALUES (?, ?, ?)");
            preparedStatement.setString(1, location.getName());
            preparedStatement.setInt(2, location.getTypes_id());
            preparedStatement.setInt(3, location.getAddress().getId());
            preparedStatement.execute();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void update(Location location) {

    }

    @Override
    public void delete(Location location) {

    }

    private Location extractLocation(ResultSet rs) throws SQLException {
        Location location = new Location();
        location.setId(rs.getInt("id"));
        location.setName(rs.getString("name").trim());
        location.setTypes_id(rs.getInt("type_id"));
        AddressDao addressDao = new AddressDao();
        location.setAddress(addressDao.get(rs.getInt("address_id")));
        return location;
    }
}
