package dk.ecosolutions.oms.persistence.database;

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
            PreparedStatement ps = connection.prepareStatement("INSERT INTO items (id, order_id, clothe_id) VALUES (?, ?, ?)");
            ps.setInt(1, item.getId());
            ps.setInt(2, item.getClothe_id());

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void update(Item item) {

    }


    public void delete(Item item) {

    }

    private Item extractItem(ResultSet rs) {
        try {
            Item item = new Item();
            item.setId(rs.getInt("id"));
            item.setQuantity(rs.getInt("quantity"));
            item.setClothe_id(rs.getInt("clothe_id"));
            return item;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
