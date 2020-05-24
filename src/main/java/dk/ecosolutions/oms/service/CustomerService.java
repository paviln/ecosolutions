package dk.ecosolutions.oms.service;

import dk.ecosolutions.oms.domain.Customer;
import dk.ecosolutions.oms.domain.Order;
import dk.ecosolutions.oms.persistence.database.CustomerDao;
import dk.ecosolutions.oms.persistence.database.OrderDoa;

import java.sql.SQLException;
import java.util.List;

public class CustomerService {
    public static void addCustomer(Customer customer) {
        CustomerDao customerDao = new CustomerDao();
        customerDao.save(customer);
    }

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
}
