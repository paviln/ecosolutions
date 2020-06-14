package dk.ecosolutions.oms.service;

import dk.ecosolutions.oms.domain.Item;
import dk.ecosolutions.oms.domain.Location;
import dk.ecosolutions.oms.domain.Order;
import dk.ecosolutions.oms.persistence.database.ItemDao;
import dk.ecosolutions.oms.persistence.database.OrderDoa;
import dk.ecosolutions.oms.persistence.database.OrderItems;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * Order service.
 *
 * @author Jens Christensen
 * @version 1.0
 * @since 1.0
 */


public class OrderService {
    public static Order getOrder(int id) {
        OrderDoa orderDoa = new OrderDoa();
        return orderDoa.get(id);
    }

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

    public static List<Order> allOrder(Location location) {
        List<Order> orders = new ArrayList<>();
        OrderDoa orderDoa = new OrderDoa();
        List<Order> ordersDao = orderDoa.all();
        if (ordersDao != null) {
            for (Order order : ordersDao) {
                if (order != null && order.getLocation().getId() == location.getId()) {
                    orders.add(order);
                }
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

    public static HashMap<String, Integer> getWeekOrders(int week) {
        Timestamp start;
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        c.set(Calendar.WEEK_OF_YEAR, week);
        c.set(Calendar.HOUR, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        c.set(Calendar.HOUR_OF_DAY, 0);

        OrderItems orderItems = new OrderItems();
        HashMap<String, Integer> orders = new HashMap<>();

        start = new Timestamp(c.getTime().getTime());
        c.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
        orders.put("MONDAY", orderItems.orderCount(start, new Timestamp(c.getTime().getTime())));
        start = new Timestamp(c.getTime().getTime());
        c.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
        orders.put("TUESDAY", orderItems.orderCount(start, new Timestamp(c.getTime().getTime())));
        start = new Timestamp(c.getTime().getTime());
        c.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
        orders.put("WEDNESDAY", orderItems.orderCount(start, new Timestamp(c.getTime().getTime())));
        start = new Timestamp(c.getTime().getTime());
        c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        orders.put("THURSDAY", orderItems.orderCount(start, new Timestamp(c.getTime().getTime())));
        start = new Timestamp(c.getTime().getTime());
        c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        orders.put("FRIDAY", orderItems.orderCount(start, new Timestamp(c.getTime().getTime())));
        start = new Timestamp(c.getTime().getTime());
        c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        orders.put("SATURDAY", orderItems.orderCount(start, new Timestamp(c.getTime().getTime())));
        start = new Timestamp(c.getTime().getTime());
        c.set(Calendar.WEEK_OF_YEAR, week + 1);
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        orders.put("SUNDAY", orderItems.orderCount(start, new Timestamp(c.getTime().getTime())));

        return orders;
    }
}
