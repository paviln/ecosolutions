package dk.ecosolutions.oms.application;

import dk.ecosolutions.oms.domain.Customer;
import dk.ecosolutions.oms.domain.CustomerService;
import dk.ecosolutions.oms.domain.Order;
import dk.ecosolutions.oms.persistence.databse.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.swing.*;
import java.sql.*;
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

    @FXML
    private TableView<Order> orderTable;

    @FXML
    private TableColumn<Order, Integer> col_id, col_userID, col_customerID;

    @FXML
    private TableColumn<Order, String> col_status;

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

        order.setStatus(status.getText());
        order.setDate(new Timestamp(System.currentTimeMillis()));
        order.setUserID(Integer.parseInt(userID.getText()));
        order.setCustomerID(Integer.parseInt(customerID.getText()));

        CustomerService.addOrder(order);
        JOptionPane.showMessageDialog(null, "Saved Successfully");
    }

    public void viewOrderHandler()  {
        col_id.setCellValueFactory(new PropertyValueFactory<Order, Integer>("Id"));
        col_status.setCellValueFactory(new PropertyValueFactory<Order, String>("Status"));
        col_dateTime.setCellValueFactory(new PropertyValueFactory<Order, Timestamp>("Date"));
        col_userID.setCellValueFactory(new PropertyValueFactory<Order, Integer>("UserID"));
        col_customerID.setCellValueFactory(new PropertyValueFactory<Order, Integer>("CustomerID"));

        orderTable.setItems(null);
        orderTable.setItems(data);

    }
}