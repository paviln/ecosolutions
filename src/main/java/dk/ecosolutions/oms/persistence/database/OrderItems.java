package dk.ecosolutions.oms.persistence.database;

import dk.ecosolutions.oms.domain.Item;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Custom order items relation database operations.
 *
 * @author Jens Christensen
 * @version 1.0
 * @since 1.0
 */

public class OrderItems {
    public List<Item> all(int orderId) {
        try {
            Connection con = Database.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM items WHERE order_id = ?");
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();
            List<Item> items = new ArrayList<>();
            while (rs.next()) {
                Item item = extractItem(rs);
                items.add(item);
            }
            con.close();

            return items;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Get the id of the last order record.
     *
     * @return id
     */
    public int getId() {
        try {
            Connection con = Database.getConnection();
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery("SELECT TOP 1 * FROM orders ORDER BY ID DESC");

            if (rs.next()) {
                int id = rs.getInt("id");
                con.close();

                return id;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public int orderCount(Timestamp from, Timestamp to) {
        try {
            Connection con = Database.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM orders WHERE created_at BETWEEN ? AND ?");
            ps.setTimestamp(1, from);
            ps.setTimestamp(2, to);
            ResultSet rs = ps.executeQuery();
            int count = 0;
            while (rs.next()) {
                count++;
            }
            con.close();

            return count;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    private Item extractItem(ResultSet rs) {
        try {
            Item item = new Item();
            item.setId(rs.getInt("id"));
            item.setQuantity(rs.getInt("quantity"));
            item.setOrder_id(rs.getInt("order_id"));
            ClothesDao clothesDao = new ClothesDao();
            item.setClothe(clothesDao.get(rs.getInt("clothe_id")));

            return item;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
