package dk.ecosolutions.oms.persistence.database;

import dk.ecosolutions.oms.domain.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Customer model implementation of DAO interface.
 *
 * @author Chamling Ram Rai
 * @version 1.0
 * @since 1.0
 */

public class CustomerDao implements Dao<Customer> {
    public Customer get(int id) {
        try {
            Connection con = Database.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM customers WHERE id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Customer customer = extractCustomer(rs);
                con.close();
                return customer;
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Customer> all() {
        try {
            Connection con = Database.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM customers");
            List<Customer> customers = new ArrayList<Customer>();
            while (rs.next()) {
                Customer customer = extractCustomer(rs);
                customers.add(customer);
            }
            con.close();

            return customers;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void save(Customer customer) {

        try {
            Connection connection = Database.getConnection();
            PreparedStatement ps = connection.prepareStatement("INSERT INTO customers (name, phone) VALUES (?, ?) ");
            ps.setString(1, customer.getName());
            ps.setString(2, customer.getPhone());
            ps.execute();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Customer customer) {
        try {
            Connection connection = Database.getConnection();
            PreparedStatement ps = connection.prepareStatement("UPDATE customers set name = ?, phone = ? where id = ?");
            ps.setString(1, customer.getName());
            ps.setString(2, customer.getPhone());
            ps.setInt(3, customer.getId());
            ps.execute();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Customer customer) {
        try {
            Connection connection = Database.getConnection();
            PreparedStatement ps = connection.prepareStatement("DELETE FROM customers WHERE name =?");
            ps.setString(1, customer.getName());
            ps.execute();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Customer extractCustomer(ResultSet rs) {
        try {
            Customer customer = new Customer();
            customer.setId(rs.getInt("id"));
            customer.setName(rs.getString("name"));
            customer.setPhone(rs.getString("phone"));

            return customer;
        } catch (SQLException
                e) {
            e.printStackTrace();
        }

        return null;
    }
}