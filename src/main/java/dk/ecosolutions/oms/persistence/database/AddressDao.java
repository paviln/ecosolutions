package dk.ecosolutions.oms.persistence.database;

import dk.ecosolutions.oms.application.helpers.DbHelper;
import dk.ecosolutions.oms.domain.Address;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Address model implementation of DAO interface.
 *
 * @author Jens Christensen
 * @version 1.0
 * @since 1.0
 */

public class AddressDao implements Dao<Address> {
    private Connection con;
    private Statement stmt;
    private PreparedStatement ps;
    private ResultSet rs;

    @Override
    public Address get(int id) {
        try {
            con = Database.getConnection();
            ps = con.prepareStatement("SELECT * FROM addresses WHERE id=?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                Address address = extractAddress(rs);

                return address;
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

    public List<Address> all() {
        try {
            con = Database.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM addresses");
            List<Address> addresses = new ArrayList<Address>();
            while (rs.next()) {
                Address address = extractAddress(rs);
                addresses.add(address);
            }

            return addresses;
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
    public void save(Address address) {
        try {
            con = Database.getConnection();
            ps = con.prepareStatement("INSERT INTO addresses (street, number, city, zip) VALUES (?,?,?,?)");
            ps.setString(1, address.getStreet());
            ps.setString(2, address.getNumber());
            ps.setString(3, address.getCity());
            ps.setString(4, address.getZip());
            ps.execute();
        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            DbHelper.close(ps);
            DbHelper.close(con);
        }
    }

    @Override
    public void update(Address address) {

    }

    @Override
    public void delete(Address address) {

    }

    private Address extractAddress(ResultSet rs) {
        try {
            Address address = new Address();
            address.setId(rs.getInt("id"));
            address.setStreet(rs.getString("street").trim());
            address.setNumber(rs.getString("number").trim());
            address.setCity(rs.getString("city").trim());
            address.setZip(rs.getString("zip"));

            return address;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}