package dk.ecosolutions.oms.application;

import dk.ecosolutions.oms.domain.Customer;
import dk.ecosolutions.oms.domain.CustomerService;
import dk.ecosolutions.oms.domain.Order;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.swing.*;
import java.sql.*;

public class AssistantController {
    @FXML
    private TextField name, phone;

    @FXML
    private TextField status;

    @FXML
    private TextField userID;

    @FXML
    private TextField customerID;

    @FXML
    private TableView<Order> orderTable;

    @FXML
    private TableColumn<Order, Integer> col_id, col_userID, col_customerID;

    @FXML
    private TableColumn<Order, Integer> col_status;

    @FXML
    private TableColumn<Order, Timestamp> col_dateTime;

    private ObservableList<Order> data;


    public void saveHandleButton() {
        Customer customer = new Customer();

        customer.setName(name.getText());
        customer.setPhone(phone.getText());

        CustomerService.addCustomer(customer);
    }

    public void orderSaveHandleButton() {
        Order order = new Order();

        order.setStatus(Integer.parseInt(status.getText()));
        order.setCreated_at(new Timestamp(System.currentTimeMillis()));
        order.setUser_id(Integer.parseInt(userID.getText()));
        order.setCustomer_id(Integer.parseInt(customerID.getText()));

        CustomerService.addOrder(order);
        JOptionPane.showMessageDialog(null, "Saved Successfully");
    }

    public void viewOrderHandler()  {
        col_id.setCellValueFactory(new PropertyValueFactory<Order, Integer>("id"));
        col_status.setCellValueFactory(new PropertyValueFactory<Order, Integer>("status"));
        col_dateTime.setCellValueFactory(new PropertyValueFactory<Order, Timestamp>("created_at"));
        col_userID.setCellValueFactory(new PropertyValueFactory<Order, Integer>("user_id"));
        col_customerID.setCellValueFactory(new PropertyValueFactory<Order, Integer>("customer_id"));
        orderTable.getItems().addAll(CustomerService.allOrder());
    }
}