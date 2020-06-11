package dk.ecosolutions.oms.service;

import dk.ecosolutions.oms.domain.Item;
import dk.ecosolutions.oms.domain.Location;
import dk.ecosolutions.oms.domain.Order;
import dk.ecosolutions.oms.persistence.database.ItemDao;
import dk.ecosolutions.oms.persistence.database.OrderDoa;
import dk.ecosolutions.oms.persistence.database.OrderItems;

import java.util.ArrayList;
import java.util.List;

/**
 * Order service.
 *
 * @author Jens Christensen
 * @version 1.0
 * @since 1.0
 */


public class OrderService {
    public static void addOrder(Order order) {
        OrderDoa orderDoa = new OrderDoa();
        orderDoa.save(order);

        ItemDao itemDao = new ItemDao();
        OrderItems orderItems = new OrderItems();
        int id = orderItems.getId();
        for (Item item : order.getItems()) {
            item.setOrder_id(id);
            itemDao.save(item);
        }
    }

    public static List<Order> allOrder() {
        List<Order> orders = new ArrayList<>();
        OrderDoa orderDoa = new OrderDoa();
        List<Order> ordersDao = orderDoa.all();
        if (ordersDao != null) {
            orders.addAll(ordersDao);
        }
        return orders;
    }

    public static List<Order> allOrder(int status) {
        List<Order> orders = new ArrayList<>();
        OrderDoa orderDoa = new OrderDoa();
        for (Order order : orderDoa.all()) {
            if (order.getStatus() == status) {
                orders.add(order);
            }
        }
        return orders;
    }

    public static List<Order> allOrder(int status, Location location) {
        List<Order> orders = new ArrayList<>();
        OrderDoa orderDoa = new OrderDoa();
        List<Order> ordersDao = orderDoa.all();
        if (ordersDao != null) {
            for (Order order : ordersDao) {
                if (order != null && order.getStatus() == status && order.getLocation().getId() == location.getId()) {
                    orders.add(order);
                }
            }
        }
        return orders;
    }

    public static void deleteOrder(Order order) {
        OrderDoa orderDoa = new OrderDoa();
        orderDoa.delete(order);
    }

    public static void updateOrder(Order order) {
        OrderDoa orderDoa = new OrderDoa();
        orderDoa.update(order);
    }
}
