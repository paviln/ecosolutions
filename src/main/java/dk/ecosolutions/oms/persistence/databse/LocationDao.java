package dk.ecosolutions.oms.persistence.databse;

import dk.ecosolutions.oms.domain.Location;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class LocationDao implements Dao<Location> {
    @Override
    public Object get(int id) {
        return null;
    }

    @Override
    public List<Location> all() {
        return null;
    }

    @Override
    public void save(Location location) {
        try {
            Connection connection = Database.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO locations (name, type_id, address_id) VALUES (?, ?, ?)");
            preparedStatement.setString(1, location.getName());
            preparedStatement.setInt(2, location.getTypes_id());
            preparedStatement.setInt(3, location.getAddresses_id());
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
}
