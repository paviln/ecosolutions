package dk.ecosolutions.oms.service.helpers;

import dk.ecosolutions.oms.domain.Order;
import dk.ecosolutions.oms.persistence.database.OrderDoa;

import java.sql.SQLException;
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
    public static void deleteOrder(Order order) throws SQLException {
        OrderDoa orderDoa = new OrderDoa();
        orderDoa.delete(order);
    }
    public static void updateOrder(Order order) throws SQLException {
        OrderDoa orderDoa = new OrderDoa();
        orderDoa.update(order);
    }
}
