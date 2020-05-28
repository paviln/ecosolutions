package dk.ecosolutions.oms.service;

import dk.ecosolutions.oms.domain.Customer;
import dk.ecosolutions.oms.persistence.database.CustomerDao;
import java.util.List;

public class CustomerService {
    public static void addCustomer(Customer customer) {
        CustomerDao customerDao = new CustomerDao();
        customerDao.save(customer);
    }
    public static List<Customer> allCustomer(){
        CustomerDao customerDao = new CustomerDao();
        return customerDao.all();
    }
    public static void updateCustomer(Customer customer){
     CustomerDao customerDao = new CustomerDao();
     customerDao.update(customer);
    }
    public static void deleteCustomer(Customer customer) {
        CustomerDao customerDao = new CustomerDao();
        customerDao.delete(customer);
    }
}
