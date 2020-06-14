package dk.ecosolutions.oms.persistence.database;

import dk.ecosolutions.oms.application.helpers.DbHelper;
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
    private Connection con;
    private Statement stmt;
    private PreparedStatement ps;
    private ResultSet rs;

    public Customer get(int id) {
        try {
            con = Database.getConnection();
            ps = con.prepareStatement("SELECT * FROM customers WHERE id = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                Customer customer = extractCustomer(rs);
                con.close();
                return customer;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbHelper.close(rs);
            DbHelper.close(ps);
            DbHelper.close(con);
        }

        return null;
    }

    public List<Customer> all() {
        try {
            con = Database.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM customers");
            List<Customer> customers = new ArrayList<Customer>();
            while (rs.next()) {
                Customer customer = extractCustomer(rs);
                customers.add(customer);
            }

            return customers;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbHelper.close(rs);
            DbHelper.close(stmt);
            DbHelper.close(con);
        }

        return null;
    }

    public void save(Customer customer) {

        try {
            con = Database.getConnection();
            ps = con.prepareStatement("INSERT INTO customers (name, phone) VALUES (?, ?) ");
            ps.setString(1, customer.getName());
            ps.setString(2, customer.getPhone());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbHelper.close(ps);
            DbHelper.close(con);
        }
    }

    public void update(Customer customer) {
        try {
            con = Database.getConnection();
            ps = con.prepareStatement("UPDATE customers set name = ?, phone = ? where id = ?");
            ps.setString(1, customer.getName());
            ps.setString(2, customer.getPhone());
            ps.setInt(3, customer.getId());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbHelper.close(ps);
            DbHelper.close(con);
        }
    }

    public void delete(Customer customer) {
        try {
            con = Database.getConnection();
            ps = con.prepareStatement("DELETE FROM customers WHERE id = ?");
            ps.setInt(1, customer.getId());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbHelper.close(ps);
            DbHelper.close(con);
        }
    }

    private Customer extractCustomer(ResultSet rs) {
        try {
            Customer customer = new Customer();
            customer.setId(rs.getInt("id"));
            customer.setName(rs.getString("name"));
            customer.setPhone(rs.getString("phone"));

            return customer;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}