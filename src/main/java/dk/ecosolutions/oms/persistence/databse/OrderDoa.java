package dk.ecosolutions.oms.persistence.databse;

import dk.ecosolutions.oms.domain.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.Date;
import java.util.List;

public class OrderDoa implements Dao<Order>{
    public Object get(int id) throws SQLException {
        return null;
    }

    public List<Order> all() {
        return null;
    }

    public void save(Order order) {
        try {
            Connection connection = Database.getConnection();
            PreparedStatement ps = connection.prepareStatement("INSERT INTO orders (status,created_at, users_id, customer_id)  VALUES (?,?,?,?)");
            ps.setString(1, order.getStatus());
            ps.setTimestamp(2,order.getDate());
            ps.setInt(3, order.getUserID());
            ps.setInt(4, order.getCustomerID());
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

    public void view(Order order){
        try {
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            ObservableList<Order> data = FXCollections.observableArrayList();
            ResultSet rs = statement.executeQuery("SELECT  * FROM orders ");
            while (rs.next()) {
                data.add(new Order(rs.getInt(1), rs.getString(2), rs.getTimestamp(3), rs.getInt(4), rs.getInt(5)));

            }
        } catch (SQLException ex) {
            System.out.println("Error" + ex);
        }


    }


}
