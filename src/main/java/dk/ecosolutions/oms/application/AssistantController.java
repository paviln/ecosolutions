package dk.ecosolutions.oms.application;

import dk.ecosolutions.oms.domain.Customer;
import dk.ecosolutions.oms.domain.CustomerService;
import dk.ecosolutions.oms.domain.Order;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AssistantController {
    @FXML
    private TextField name, phone;

    @FXML
    private TextField status;

    @FXML
    private TextField userID;

    @FXML
    private TextField customerID;


    public void saveHandleButton() {
        Customer customer = new Customer();

        customer.setName(name.getText());
        customer.setPhone(phone.getText());

        CustomerService.addCustomer(customer);
    }

    public void orderSaveHandleButton(){
        Order order = new Order();

        order.setStatus(status.getText());
        order.setDate(new Timestamp(System.currentTimeMillis()));
        order.setUserID(Integer.parseInt(userID.getText()));
        order.setCustomerID(Integer.parseInt(customerID.getText()));

        CustomerService.addOrder(order);
    }
}
