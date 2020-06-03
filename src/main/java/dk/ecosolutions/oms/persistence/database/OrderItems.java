package dk.ecosolutions.oms.persistence.database;

import dk.ecosolutions.oms.domain.Item;
import dk.ecosolutions.oms.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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

    public void removeOrderItems(User user) {
        try {
            Connection con = Database.getConnection();
            PreparedStatement ps = con.prepareStatement("DELETE FROM items WHERE order_id = ?");
            ps.setInt(1, user.getId());
            ps.execute();
            ps = con.prepareStatement("DELETE FROM orders WHERE user_id = ?");
            ps.setInt(1, user.getId());
            ps.execute();

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
