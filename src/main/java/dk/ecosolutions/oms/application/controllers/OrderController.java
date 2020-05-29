package dk.ecosolutions.oms.application.controllers;

import dk.ecosolutions.oms.domain.Order;
import dk.ecosolutions.oms.service.OrderService;
import dk.ecosolutions.oms.service.helpers.AlertHelper;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Timestamp;

public class OrderController {
    @FXML
    private TextField status;

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
    public void initialize() {
        col_id.setCellValueFactory(new PropertyValueFactory<Order, Integer>("id"));
        col_status.setCellValueFactory(new PropertyValueFactory<Order, Integer>("status"));
        col_dateTime.setCellValueFactory(new PropertyValueFactory<Order, Timestamp>("created_at"));
        col_userID.setCellValueFactory(new PropertyValueFactory<Order, Integer>("user_id"));
        col_customerID.setCellValueFactory(new PropertyValueFactory<Order, Integer>("customer_id"));
        orderTable.getItems().addAll(OrderService.allOrder());
    }

    @FXML
    public void save() {
        Order order = new Order();
        if (status.getText().isEmpty() || customerID.getText().isEmpty()) {
            AlertHelper.showInformationAlert("One of the field is empty");
        } else {
            order.setStatus(Integer.parseInt(status.getText()));
            order.setLocation(WelcomeController.getAuthenticatedUser().getLocation());
            order.setCreated_at(new Timestamp(System.currentTimeMillis()));
            /* NEW */order.setUser_id(WelcomeController.getAuthenticatedUser().getId());
            order.setCustomer_id(Integer.parseInt(customerID.getText()));
            OrderService.addOrder(order);
            orderTable.getItems().add(order);
            AlertHelper.showInformationAlert("Saved Successfully");
        }
        status.clear();
        customerID.clear();
    }

    @FXML
    public void delete() {
        Order order = orderTable.getSelectionModel().getSelectedItem();
        if (order != null) {

            if (AlertHelper.confirmAlert("Confirm if You want to delete")) {
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
    public void show() {
        Order order = orderTable.getSelectionModel().getSelectedItem();
        status.setText(String.valueOf(order.getStatus()));
        customerID.setText(String.valueOf(order.getCustomer_id()));
    }

    @FXML
    public void update() {
        Order order = orderTable.getSelectionModel().getSelectedItem();
        if (order != null) {
            order.setStatus(Integer.parseInt(status.getText()));
            AlertHelper.showInformationAlert("updated");
            OrderService.updateOrder(order);
        }
    }
}
