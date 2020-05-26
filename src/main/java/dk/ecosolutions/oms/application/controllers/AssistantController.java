package dk.ecosolutions.oms.application.controllers;

import dk.ecosolutions.oms.domain.Customer;
import dk.ecosolutions.oms.domain.Order;
import dk.ecosolutions.oms.service.CustomerService;
import dk.ecosolutions.oms.service.helpers.OrderService;
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

    @FXML
    private TableView<Customer> customerTable;

    @FXML
    private TableColumn<Customer, Integer> col_cusID;

    @FXML
    private TableColumn<Customer, String> col_customerName, col_customerPhone;

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

            OrderService.addOrder(order);
            JOptionPane.showMessageDialog(null, "Saved Successfully");
        }
    }
    public void viewOrderHandler() {
            col_id.setCellValueFactory(new PropertyValueFactory<Order, Integer>("id"));
            col_status.setCellValueFactory(new PropertyValueFactory<Order, Integer>("status"));
            col_dateTime.setCellValueFactory(new PropertyValueFactory<Order, Timestamp>("created_at"));
            col_userID.setCellValueFactory(new PropertyValueFactory<Order, Integer>("user_id"));
            col_customerID.setCellValueFactory(new PropertyValueFactory<Order, Integer>("customer_id"));
            orderTable.getItems().addAll(OrderService.allOrder());
        }

    public void deleteOrderHandler() throws SQLException {
        Order order = orderTable.getSelectionModel().getSelectedItem();
        if (order != null) {
            JOptionPane frame = new JOptionPane();
            if (JOptionPane.showConfirmDialog(frame, "Confirm if You want to delete", "print System",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION) {
                JOptionPane.showMessageDialog(null, "Deleted successfully");

                OrderService.deleteOrder(order);
                Order selectedOrder = orderTable.getSelectionModel().getSelectedItem();
                orderTable.getItems().remove(selectedOrder);
            }
        }
    }

    /**
     * This function shows order details in the respect field when a particular field is clicked
     */
    @FXML
    public void showOrder() {
        Order order = orderTable.getSelectionModel().getSelectedItem();
        status.setText(String.valueOf(order.getStatus()));
        userID.setText(String.valueOf(order.getUser_id()));
        customerID.setText(String.valueOf(order.getCustomer_id()));
    }

    public void updateOrderHandler() throws SQLException {
        Order order = orderTable.getSelectionModel().getSelectedItem();
        if (order != null) {
            order.setStatus(Integer.parseInt(status.getText()));
            JOptionPane.showMessageDialog(null,"updated");
            OrderService.updateOrder(order);
        }
    }

    public void customerViewButton() {
        col_cusID.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("id"));
        col_customerName.setCellValueFactory(new PropertyValueFactory<Customer, String>("name"));
        col_customerPhone.setCellValueFactory(new PropertyValueFactory<Customer, String>("phone"));
        customerTable.getItems().addAll(CustomerService.allCustomer());
    }

    public void customerUpdateButton() throws SQLException {
        Customer customer = customerTable.getSelectionModel().getSelectedItem();
        if (customer != null){
            customer.setName(name.getText());
            customer.setPhone(phone.getText());
            JOptionPane.showMessageDialog(null,"updated");
            CustomerService.updateCustomer(customer);

        }
    }

    /**
     * This function shows details of the customer is shown in the
     * respective field when a particular row is clicked
     */
    @FXML
    public void showCustomer() {
            Customer customer = customerTable.getSelectionModel().getSelectedItem();
            name.setText(customer.getName());
            phone.setText(customer.getPhone());
        }

    /**
     * This function clear the fields
     * to make a new customer entry
     *in customer tab
     */
    public void customerClearButton() {
        name.clear();
        phone.clear();
    }

    /**
     * This function clear the fields to make a
     *  new order entry in order tab
     *
     */
    public void clearOrderButton() {
        status.clear();
        userID.clear();
        customerID.clear();
    }

    public void customerDeleteFunction() throws SQLException {
        Customer customer = customerTable.getSelectionModel().getSelectedItem();
        if (customer != null){
            JOptionPane frame = new JOptionPane();
            if (JOptionPane.showConfirmDialog(frame, "Confirm if You want to delete", "print System",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION) {
                JOptionPane.showMessageDialog(null, "Deleted successfully");

                CustomerService.deleteCustomer(customer);
                Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
                customerTable.getItems().remove(selectedCustomer);
            }

            }
    }
}