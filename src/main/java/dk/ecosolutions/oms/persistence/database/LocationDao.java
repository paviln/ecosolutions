package dk.ecosolutions.oms.persistence.database;

import dk.ecosolutions.oms.application.helpers.DbHelper;
import dk.ecosolutions.oms.domain.Location;
import dk.ecosolutions.oms.domain.Type;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Location model implementation of DAO interface.
 *
 * @author Jens Christensen
 * @version 1.0
 * @since 1.0
 */

public class LocationDao implements Dao<Location> {
    private Connection con;
    private Statement stmt;
    private PreparedStatement ps;
    private ResultSet rs;

    @Override
    public Location get(int id) {
        try {
            con = Database.getConnection();
            ps = con.prepareStatement("SELECT * FROM locations WHERE id=?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                Location location = extractLocation(rs);

                return location;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbHelper.close(rs);
            DbHelper.close(ps);
            DbHelper.close(con);
        }

        return null;
    }

    @Override
    public List<Location> all() {
        try {
            con = Database.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM locations");
            List<Location> locations = new ArrayList<>();
            while (rs.next()) {
                Location location = extractLocation(rs);
                locations.add(location);
            }
            Collections.sort(locations);

            return locations;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbHelper.close(rs);
            DbHelper.close(stmt);
            DbHelper.close(con);
        }

        return null;
    }

    @Override
    public void save(Location location) {
        try {
            con = Database.getConnection();
            ps = con.prepareStatement("INSERT INTO locations (name, priority, type_id, address_id) VALUES (?, ?, ?, ?)");
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
        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            DbHelper.close(ps);
            DbHelper.close(con);
        }
    }

    @Override
    public void update(Location location) {
        try {
            con = Database.getConnection();
            ps = con.prepareStatement("UPDATE locations set name = ?, priority = ?, type_id = ?, address_id = ? where id = ?");
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
        } finally {
            DbHelper.close(ps);
            DbHelper.close(con);
        }
    }

    @Override
    public void delete(Location location) {
        try {
            con = Database.getConnection();
            ps = con.prepareStatement("DELETE FROM locations WHERE id=?");
            ps.setInt(1, location.getId());
            ps.execute();
        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            DbHelper.close(ps);
            DbHelper.close(con);
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
