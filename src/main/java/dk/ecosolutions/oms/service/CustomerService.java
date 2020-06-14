package dk.ecosolutions.oms.service;

import dk.ecosolutions.oms.domain.Customer;
import dk.ecosolutions.oms.persistence.database.CustomerDao;

import java.util.List;

/**
 * Customer service.
 *
 * @author Jens Christensen
 * @version 1.0
 * @since 1.0
 */

public class CustomerService {
    public static void getCustomer(int id) {
        CustomerDao customerDao = new CustomerDao();
        customerDao.get(id);
    }

    public static Customer getCustomerByPhone(String phone) {
        CustomerDao customerDao = new CustomerDao();
        for (Customer customer : customerDao.all()) {
            if (customer.getPhone().equals(phone)) {
                return customer;
            }
        }
        return new Customer();
    }

    public static boolean validCustomer(int id) {
        CustomerDao customerDao = new CustomerDao();
        Customer customer = customerDao.get(id);
        if (customer != null) {
            return true;
        }
        return false;
    }

    public static void addCustomer(Customer customer) {
        CustomerDao customerDao = new CustomerDao();
        customerDao.save(customer);
    }

    public static List<Customer> allCustomer() {
        CustomerDao customerDao = new CustomerDao();
        return customerDao.all();
    }

    public static void updateCustomer(Customer customer) {
        CustomerDao customerDao = new CustomerDao();
        customerDao.update(customer);
    }

    public static void deleteCustomer(Customer customer) {
        CustomerDao customerDao = new CustomerDao();
        customerDao.delete(customer);
    }


    public static Boolean customerExists(String phone) {
        CustomerDao customerDao = new CustomerDao();

        for (Customer customer : customerDao.all()) {
            if (customer.getPhone().equals(phone)) {
                return true;
            }
        }
        return false;
    }
}
