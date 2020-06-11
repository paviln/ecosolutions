package dk.ecosolutions.oms.persistence.database;

import dk.ecosolutions.oms.domain.Item;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Item model implementation of DAO interface.
 *
 * @author Chamling Ram Rai
 * @version 1.0
 * @since 1.0
 */

public class ItemDao implements Dao<Item> {
    @Override
    public Item get(int id) {
        return null;
    }

    @Override
    public List<Item> all() {
        try {
            Connection con = Database.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM items");
            List<Item> items = new ArrayList<>();
            while (rs.next()) {
                Item item = extractItem(rs);
                items.add(item);
            }
            con.close();

            return items;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void save(Item item) {
        try {
            Connection con = Database.getConnection();
            PreparedStatement ps = con.prepareStatement("INSERT INTO items (quantity, order_id, clothe_id) VALUES (?, ?, ?)");
            ps.setInt(1, item.getQuantity());
            ps.setInt(2, item.getOrder_id());
            ps.setInt(3, item.getClothe().getId());
            ps.execute();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Item item) {

    }

    public void delete(Item item) {
        try {
            Connection con = Database.getConnection();
            PreparedStatement ps = con.prepareStatement("DELETE FROM items WHERE quantity = ?");
            ps.setInt(1, item.getQuantity());
            ps.execute();
            con.close();
        } catch (SQLException e) {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}