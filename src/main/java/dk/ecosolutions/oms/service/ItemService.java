package dk.ecosolutions.oms.service;

import dk.ecosolutions.oms.domain.Item;
import dk.ecosolutions.oms.domain.Order;
import dk.ecosolutions.oms.persistence.database.ItemDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Item service.
 *
 * @author Chamling Ram Rai
 * @version 1.0
 * @since 1.0
 */

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

    public static void deleteItems(Item item) {
        ItemDao itemDao = new ItemDao();
        itemDao.delete(item);
    }

    public static List<Item> allItem(Order order) {
        ItemDao itemDao = new ItemDao();
        List<Item> items = new ArrayList<>();
        for (Item item : itemDao.all()) {
            if (item.getOrder_id() == order.getId()) {
                items.add(item);
            }
        }
        return items;
    }
}