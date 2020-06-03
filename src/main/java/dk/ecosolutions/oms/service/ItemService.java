package dk.ecosolutions.oms.service;
import dk.ecosolutions.oms.domain.Item;
import dk.ecosolutions.oms.persistence.database.ItemDao;

import java.util.List;

public class ItemService {
    public static void addItem(Item item) {
        ItemDao itemDao = new ItemDao();
        itemDao.save(item);
    }
    public static void addAll(List<Item> items) {
        ItemDao itemDao = new ItemDao();

        for (Item item : items) {
            itemDao.save(item);
        }
    }
    public static List<Item> allItem() {
        ItemDao itemDao = new ItemDao();
        return itemDao.all();
    }
}