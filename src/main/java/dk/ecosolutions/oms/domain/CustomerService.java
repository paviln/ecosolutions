package dk.ecosolutions.oms.domain;

import dk.ecosolutions.oms.persistence.databse.CustomerDao;
import dk.ecosolutions.oms.persistence.databse.OrderDoa;

import java.util.List;

public class CustomerService {
    public static void addCustomer(Customer customer) {
        CustomerDao customerDao = new CustomerDao();
        customerDao.save(customer);
    }
    public static  void addOrder(Order order) {
        OrderDoa orderDoa = new OrderDoa();
        orderDoa.save(order);
    }
    public static List<Order> allOrder() {
        OrderDoa orderDoa = new OrderDoa();
        return orderDoa.all();
    }
}
