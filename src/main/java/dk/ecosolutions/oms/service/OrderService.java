package dk.ecosolutions.oms.service;

import dk.ecosolutions.oms.domain.Location;
import dk.ecosolutions.oms.domain.Order;
import dk.ecosolutions.oms.persistence.database.OrderDoa;

import java.util.ArrayList;
import java.util.List;

public class OrderService {
    public static void addOrder(Order order) {
        OrderDoa orderDoa = new OrderDoa();
        orderDoa.save(order);
    }

    public static List<Order> allOrder() {
        OrderDoa orderDoa = new OrderDoa();
        return orderDoa.all();
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
