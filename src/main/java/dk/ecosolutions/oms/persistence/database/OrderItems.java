package dk.ecosolutions.oms.persistence.database;

import dk.ecosolutions.oms.domain.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

    private Item extractItem(ResultSet rs) {
        try {
            Item item = new Item();
            item.setId(rs.getInt("id"));
            item.setQuantity(rs.getInt("quantity"));
            item.setOrder_id(rs.getInt("order_id"));
            item.setClothe_id(rs.getInt("clothe_id"));
            return item;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
