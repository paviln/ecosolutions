package dk.ecosolutions.oms.persistence.database;

import dk.ecosolutions.oms.domain.Clothe;
import dk.ecosolutions.oms.domain.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ItemDao implements Dao<Item> {
    @Override
    public Item get(int id) {
        return null;
    }

    @Override
    public List<Item> all() {
        try {
            Connection connection = Database.getConnection();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM items");
            List<Item> items = new ArrayList<>();
            while (rs.next()) {
                Item item = extractItem(rs);
                items.add(item);
            }
            connection.close();
            return items;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public void save(Item item) {
        try {
            Connection connection = Database.getConnection();
            PreparedStatement ps = connection.prepareStatement("INSERT INTO items (quantity, order_id, clothe_id) VALUES (?, ?, ?)");
            ps.setInt(1, item.getQuantity());
            ps.setInt(2, item.getOrder_id());
            ps.setInt(3, item.getClothe().getId());
            ps.execute();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void update(Item item) {

    }


    public void delete(Item item) {
        try {
            Connection connection = Database.getConnection();
            PreparedStatement ps = connection.prepareStatement("DELETE * FROM items WHERE quantity = ?");
            ps.setInt(1, item.getQuantity());
            ps.execute();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Item extractItem(ResultSet rs) {
        try {
            Item item = new Item();
            item.setId(rs.getInt("id"));
            item.setQuantity(rs.getInt("quantity"));
            ClothesDao clothesDao = new ClothesDao();
            item.setClothe(clothesDao.get(rs.getInt("clothe_id")));
            return item;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
