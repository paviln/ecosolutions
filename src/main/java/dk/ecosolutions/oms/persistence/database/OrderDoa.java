package dk.ecosolutions.oms.persistence.database;

import dk.ecosolutions.oms.application.helpers.DbHelper;
import dk.ecosolutions.oms.domain.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Order model implementation of DAO interface.
 *
 * @author Jens Christensen
 * @version 1.0
 * @since 1.0
 */

public class OrderDoa implements Dao<Order> {
    private Connection con;
    private Statement stmt;
    private PreparedStatement ps;
    private ResultSet rs;

    public Order get(int id) {
        try {
            con = Database.getConnection();
            ps = con.prepareStatement("SELECT * FROM orders WHERE id=?");
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                Order order = extractOrder(rs);

                return order;
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

    public List<Order> all() {
        try {
            con = Database.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM orders");
            List<Order> orders = new ArrayList<Order>();
            while (rs.next()) {
                Order order = extractOrder(rs);
                orders.add(order);
            }

            return orders;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbHelper.close(rs);
            DbHelper.close(stmt);
            DbHelper.close(con);
        }

        return null;
    }

    public void save(Order order) {
        try {
            con = Database.getConnection();
            ps = con.prepareStatement("INSERT INTO orders (status, location_id, created_at, user_id, customer_id)  VALUES (?,?,?,?,?)");
            ps.setInt(1, order.getStatus());
            ps.setInt(2, order.getLocation().getId());
            ps.setTimestamp(3, order.getCreated_at());
            ps.setInt(4, order.getUser_id());
            ps.setInt(5, order.getCustomer_id());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbHelper.close(ps);
            DbHelper.close(con);
        }
    }

    public void update(Order order) {
        try {
            con = Database.getConnection();
            ps = con.prepareStatement("UPDATE orders set status = ? where id = ?");
            ps.setInt(1, order.getStatus());
            ps.setInt(2, order.getId());
            ps.execute();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbHelper.close(ps);
            DbHelper.close(con);
        }
    }

    public void delete(Order order) {
        try {
            con = Database.getConnection();
            ps = con.prepareStatement("DELETE FROM orders WHERE status = ?");
            ps.setInt(1, order.getStatus());
            ps.execute();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbHelper.close(ps);
            DbHelper.close(con);
        }
    }

    private Order extractOrder(ResultSet rs) {
        try {
            Order order = new Order();
            order.setId(rs.getInt("id"));
            order.setStatus(rs.getInt("status"));
            OrderItems orderItems = new OrderItems();
            order.setItems(orderItems.all(order.getId()));
            LocationDao locationDao = new LocationDao();
            order.setLocation(locationDao.get(rs.getInt("location_id")));
            order.setCreated_at(rs.getTimestamp("created_at"));
            order.setUser_id(rs.getInt("user_id"));
            order.setCustomer_id(rs.getInt("customer_id"));
            return order;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
