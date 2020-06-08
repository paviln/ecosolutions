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
            Connection con = Database.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM locations WHERE id=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Location location = extractLocation(rs);
                con.close();

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
            Connection con = Database.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM locations");
            List<Location> locations = new ArrayList<>();
            while (rs.next()) {
                Location location = extractLocation(rs);
                locations.add(location);
            }
            con.close();
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
            Connection con = Database.getConnection();
            PreparedStatement ps = con.prepareStatement("INSERT INTO locations (name, priority, type_id, address_id) VALUES (?, ?, ?, ?)");
            ps.setString(1, location.getName());
            ps.setInt(2, location.getPriority());
            switch (location.getType()) {
                case CLEANING_CENTRAL:
                    ps.setInt(3, 1);
                    break;
                case DELIVERY_POINT:
                    ps.setInt(3, 2);
                    break;
            }
            ps.setInt(4, location.getAddress().getId());
            ps.execute();
            con.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void update(Location location) {
        try {
            Connection con = Database.getConnection();
            PreparedStatement ps = con.prepareStatement("UPDATE locations set name = ?, priority = ?, type_id = ?, address_id = ? where id = ?");
            ps.setString(1, location.getName());
            ps.setInt(2, location.getPriority());
            switch (location.getType()) {
                case CLEANING_CENTRAL:
                    ps.setInt(3, 1);
                    break;
                case DELIVERY_POINT:
                    ps.setInt(3, 2);
                    break;
            }
            ps.setInt(4, location.getAddress().getId());
            ps.setInt(5, location.getId());
            ps.execute();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Location location) {
        try {
            Connection con = Database.getConnection();
            PreparedStatement ps = con.prepareStatement("DELETE FROM locations WHERE id=?");
            ps.setInt(1, location.getId());
            ps.execute();
            con.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    private Location extractLocation(ResultSet rs) {
        try {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
