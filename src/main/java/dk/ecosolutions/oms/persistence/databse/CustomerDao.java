package dk.ecosolutions.oms.persistence.databse;

import dk.ecosolutions.oms.domain.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class CustomerDao implements Dao<Customer> {
    public Object get(int id) throws SQLException {

        return null;
    }

    public List<Customer> all() {
        return null;
    }

    public void save(Customer customer) {

        try {
        Connection connection = Database.getConnection();
        PreparedStatement ps = connection.prepareStatement("INSERT INTO customers (name, phone) VALUES (?, ?) ");
        ps.setString(1, customer.getName());
        ps.setString(2,customer.getPhone());
        ps.execute();
        connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void update(Customer customer) {

    }

    public void delete(Customer customer) {

    }
}
