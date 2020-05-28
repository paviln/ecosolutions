package dk.ecosolutions.oms.persistence.database;

import dk.ecosolutions.oms.domain.Customer;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDao implements Dao<Customer> {
    public Customer get(int id) throws SQLException {

        return null;
    }

    public List<Customer> all() {
        try{
            Connection connection = Database.getConnection();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM customers");
            List<Customer> customers = new ArrayList<Customer>();
            while (rs.next()){
                Customer customer = extractLocation(rs);
                customers.add(customer);
            }
            connection.close();
            return customers;
        }catch (SQLException e){
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
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void update(Customer customer) {
        try{
            Connection connection = Database.getConnection();
            PreparedStatement ps = connection.prepareStatement("UPDATE customers set name = ?, phone = ? where id = ?");
            ps.setString(1, customer.getName());
            ps.setString(2, customer.getPhone());
            ps.setInt(3, customer.getId());
            ps.execute();
            connection.close();

        }catch (Exception e){

        }
    }

    public void delete(Customer customer) {
        try {
            Connection connection = Database.getConnection();
            PreparedStatement ps = connection.prepareStatement("DELETE FROM customers WHERE name =?");
            ps.setString(1, customer.getName());
            ps.execute();
            connection.close();
        }catch (Exception e){

        }
    }
    private Customer extractLocation(ResultSet rs) throws SQLException {
        Customer customer = new Customer();
        customer.setId(rs.getInt("id"));
        customer.setName(rs.getString("name"));
        customer.setPhone(rs.getString("phone"));
        return customer;
    }
}
