package dk.ecosolutions.oms.persistence.databse;

import dk.ecosolutions.oms.domain.Order;

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
            PreparedStatement ps = connection.prepareStatement("INSERT INTO orders (status, users_id, customer_id)  VALUES (?,?,?)");
            ps.setString(1, order.getStatus());
            ps.setInt(2, order.getUserID());
            ps.setInt(3, order.getCustomerID());
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
//    public void getDate(Date date) throws SQLException {
//        Connection connection = Database.getConnection();
//        Statement stmt = connection.createStatement();
//        ResultSet rs = stmt.executeQuery("SELECT * FROM orders WHERE created_at=" + date);
//        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//      while (rs.next())
//        {
//        date = rs.getTimestamp(3);
//            System.out.println(sdf.format(date));
//        }
//
//    }

}
