package dk.ecosolutions.oms.persistence.databse;

import dk.ecosolutions.oms.domain.Address;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AddressDao implements Dao<Address> {
    @Override
    public Object get(int id) {
        return null;
    }

    public List<Address> all() {
        try {
            Connection connection = Database.getConnection();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM addresses");
            List<Address> addresses = new ArrayList<Address>();
            while (rs.next()) {
                Address address = extractAddress(rs);
                addresses.add(address);
            }
            connection.close();
            return addresses;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void save(Address address) {
        try {
            Connection connection = Database.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO addresses (street, number, city, zip) VALUES (?,?,?,?)");
            preparedStatement.setString(1, address.getStreet());
            preparedStatement.setString(2, address.getNumber());
            preparedStatement.setString(3, address.getCity());
            preparedStatement.setString(4, address.getZip());
            preparedStatement.execute();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void update(Address address) {

    }

    @Override
    public void delete(Address address) {

    }

    private Address extractAddress(ResultSet rs) throws SQLException {
        Address address = new Address();
        address.setId(rs.getInt("id"));
        address.setStreet(rs.getString("street").trim());
        address.setNumber(rs.getString("number").trim());
        address.setCity(rs.getString("city").trim());
        address.setZip(rs.getString("zip"));
        return address;
    }

}
