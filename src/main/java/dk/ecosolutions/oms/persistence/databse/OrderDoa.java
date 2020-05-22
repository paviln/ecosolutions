package dk.ecosolutions.oms.persistence.databse;

import dk.ecosolutions.oms.domain.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDoa implements Dao<Order>{
    public Object get(int id) throws SQLException {
        return null;
    }

    public List<Order> all() {
        try {
            Connection connection = Database.getConnection();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM orders");
            List<Order> orders = new ArrayList<Order>();
            while (rs.next()) {
                Order order = extractLocation(rs);
                orders.add(order);
            }
            connection.close();
            return orders;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void save(Order order) {
        try {
            Connection connection = Database.getConnection();
            PreparedStatement ps = connection.prepareStatement("INSERT INTO orders (status,created_at, users_id, customer_id)  VALUES (?,?,?,?)");
            ps.setInt(1, order.getStatus());
            ps.setTimestamp(2,order.getCreated_at());
            ps.setInt(3, order.getUser_id());
            ps.setInt(4, order.getCustomer_id());
            ps.execute();
            connection.close();
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
    }

    public void update(Order order) {

    }

    public void delete(Order order) {

    }
    private Order extractLocation(ResultSet rs) throws SQLException {
        Order order = new Order();
        order.setId(rs.getInt("id"));
        order.setStatus(rs.getInt("status"));
        order.setCreated_at(rs.getTimestamp("created_at"));
        order.setUser_id(rs.getInt("user_id"));
        order.setCustomer_id(rs.getInt("customer_id"));
        return order;
    }
}
