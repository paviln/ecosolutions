package dk.ecosolutions.oms.application.controllers;

import dk.ecosolutions.oms.domain.Customer;
import dk.ecosolutions.oms.domain.Order;
import dk.ecosolutions.oms.service.CustomerService;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.sql.Timestamp;

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
        if (name.getText().isEmpty() || phone.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "One of the field is empty");
        } else {
            customer.setName(name.getText());
            customer.setPhone(phone.getText());

            CustomerService.addCustomer(customer);
            JOptionPane.showMessageDialog(null, "Saved Successfully");
        }
    }
    public void orderSaveHandleButton() {
        Order order = new Order();
        if (status.getText().isEmpty() || userID.getText().isEmpty() || customerID.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "One of the field is empty");
        } else {
            order.setStatus(Integer.parseInt(status.getText()));
            order.setCreated_at(new Timestamp(System.currentTimeMillis()));
            order.setUser_id(Integer.parseInt(userID.getText()));
            order.setCustomer_id(Integer.parseInt(customerID.getText()));

            CustomerService.addOrder(order);
            JOptionPane.showMessageDialog(null, "Saved Successfully");
        }
    }
    public void viewOrderHandler() {
        col_id.setCellValueFactory(new PropertyValueFactory<Order, Integer>("id"));
        col_status.setCellValueFactory(new PropertyValueFactory<Order, Integer>("status"));
        col_dateTime.setCellValueFactory(new PropertyValueFactory<Order, Timestamp>("created_at"));
        col_userID.setCellValueFactory(new PropertyValueFactory<Order, Integer>("user_id"));
        col_customerID.setCellValueFactory(new PropertyValueFactory<Order, Integer>("customer_id"));
        orderTable.getItems().addAll(CustomerService.allOrder());
    }

    public void deleteOrderHandler() throws SQLException {
        Order order = orderTable.getSelectionModel().getSelectedItem();

        JOptionPane frame = new JOptionPane();
        if (JOptionPane.showConfirmDialog(frame, "Confirm if You want to delete", "print System",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION) {
            JOptionPane.showMessageDialog(null, "Deleted successfully");

        CustomerService.deleteOrder(order);
        Order selectedOrder = orderTable.getSelectionModel().getSelectedItem();
        orderTable.getItems().remove(selectedOrder);
        }
    }
    @FXML
    public void showOrder() {
        Order order = orderTable.getSelectionModel().getSelectedItem();
        status.setText(String.valueOf(order.getStatus()));
        userID.setText(String.valueOf(order.getUser_id()));
        customerID.setText(String.valueOf(order.getCustomer_id()));
    }
}