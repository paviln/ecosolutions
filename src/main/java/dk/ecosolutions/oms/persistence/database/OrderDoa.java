package dk.ecosolutions.oms.persistence.database;

import dk.ecosolutions.oms.domain.Item;
import dk.ecosolutions.oms.domain.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDoa implements Dao<Order> {
    public Order get(int id) throws SQLException {
        return null;
    }

    public List<Order> all() {
        try {
            Connection connection = Database.getConnection();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM orders");
            List<Order> orders = new ArrayList<Order>();
            while (rs.next()) {
                Order order = extractOrder(rs);
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
            PreparedStatement ps = connection.prepareStatement("INSERT INTO orders (status, location_id, created_at, user_id, customer_id)  VALUES (?,?,?,?,?)");
            ps.setInt(1, order.getStatus());
            ps.setInt(2, order.getLocation().getId());
            ps.setTimestamp(3, order.getCreated_at());
            ps.setInt(4, order.getUser_id());
            ps.setInt(5, order.getCustomer_id());
            ps.execute();
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void update(Order order) {
        try {
            Connection connection = Database.getConnection();
            PreparedStatement ps = connection.prepareStatement("UPDATE orders set status = ? where id = ?");
            ps.setInt(1, order.getStatus());
            ps.setInt(2, order.getId());
            ps.execute();
            connection.close();
        } catch (Exception e) {

        }
    }

    public void delete(Order order) {
        try {
            Connection connection = Database.getConnection();
            PreparedStatement ps = connection.prepareStatement("DELETE FROM orders WHERE status = ?");
            ps.setInt(1, order.getStatus());
            ps.execute();
            connection.close();
        } catch (Exception e) {

        }
    }

    private Order extractOrder(ResultSet rs) {
        try {
            Order order = new Order();
            order.setId(rs.getInt("id"));
            order.setStatus(rs.getInt("status"));
            ItemDao itemDao = new ItemDao();
            List<Item> items = new ArrayList<>();
            for (Item item : itemDao.all()) {
                if (item.getOrder_id() == order.getId()) {
                    items.add(item);
                }
            }
            order.setItems(items);
            LocationDao locationDao = new LocationDao();
            order.setLocation(locationDao.get(rs.getInt("location_id")));
            order.setCreated_at(rs.getTimestamp("created_at"));
            order.setUser_id(rs.getInt("user_id"));
            order.setCustomer_id(rs.getInt("customer_id"));
            return order;
        } catch (Exception e) {

        }
        return null;
    }
}
