package dk.ecosolutions.oms.service;
import dk.ecosolutions.oms.domain.Item;
import dk.ecosolutions.oms.persistence.database.ItemDao;

import java.sql.SQLException;
import java.util.List;

public class ItemService{
    public static void addItem(Item item) throws SQLException {
        ItemDao itemDao = new ItemDao();
        itemDao.save(item);
    }
    public static List<Item> allItem(){
        ItemDao itemDao = new ItemDao();
        return itemDao.all();
    }
}