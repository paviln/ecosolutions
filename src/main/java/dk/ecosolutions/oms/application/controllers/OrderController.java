package dk.ecosolutions.oms.application.controllers;

import dk.ecosolutions.oms.domain.Clothes;
import dk.ecosolutions.oms.domain.Item;
import dk.ecosolutions.oms.domain.Order;
import dk.ecosolutions.oms.service.ItemService;
import dk.ecosolutions.oms.service.OrderService;
import dk.ecosolutions.oms.service.helpers.ClothesService;
import dk.ecosolutions.oms.service.helpers.DialogHelper;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.sql.Timestamp;

public class OrderController {
    @FXML
    private AnchorPane orderIndex, orderCreate;
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
    private ChoiceBox<Clothes> clothes;
    @FXML
    private TableView<Item> itemsTable;
    @FXML
    private TableColumn<Item, String> col_clothe;
    @FXML
    private TableColumn<Item, Integer> col_quantity;
    @FXML
    private TextField customerId;
    @FXML
    private TextField quantity;


    @FXML
    public void initialize() {
        col_id.setCellValueFactory(new PropertyValueFactory<Order, Integer>("id"));
        col_status.setCellValueFactory(new PropertyValueFactory<Order, Integer>("status"));
        col_dateTime.setCellValueFactory(new PropertyValueFactory<Order, Timestamp>("created_at"));
        col_userID.setCellValueFactory(new PropertyValueFactory<Order, Integer>("user_id"));
        col_customerID.setCellValueFactory(new PropertyValueFactory<Order, Integer>("customer_id"));
        orderTable.getItems().addAll(OrderService.allOrder());
        clothes.getItems().addAll(ClothesService.allClothes());

        col_clothe.setCellValueFactory(new PropertyValueFactory<Item, String>("clothe_id"));
        col_quantity.setCellValueFactory(new PropertyValueFactory<Item, Integer>("Quantity"));
        itemsTable.getItems().addAll(ItemService.allItem());
    }

    @FXML
    public void save() {
        Order order = new Order();
        Item item = new Item();
        if (status.getText().isEmpty() || customerID.getText().isEmpty()) {
            DialogHelper.showInformationAlert("One of the field is empty");
        } else {
            order.setStatus(Integer.parseInt(status.getText()));
            order.setLocation(WelcomeController.getAuthenticatedUser().getLocation());
            order.setCreated_at(new Timestamp(System.currentTimeMillis()));
            /* NEW */
            order.setUser_id(WelcomeController.getAuthenticatedUser().getId());
            order.setCustomer_id(Integer.parseInt(customerID.getText()));
            OrderService.addOrder(order);
            orderTable.getItems().add(order);
            /* NEW FOR ITEM */
            item.setQuantity(Integer.parseInt(quantity.getText()));

            DialogHelper.showInformationAlert("Saved Successfully");
        }
        status.clear();
        customerID.clear();
    }

    @FXML
    public void delete() {
        Order order = orderTable.getSelectionModel().getSelectedItem();
        if (order != null) {

            if (DialogHelper.confirmAlert("Confirm if You want to delete")) {
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
            DialogHelper.showInformationAlert("updated");
            OrderService.updateOrder(order);
        }
    }


    @FXML
    public void changeDisplay(ActionEvent event) {
        Button btn = (Button) event.getSource();
        viewToDisplay(btn.getId());
    }

    private void viewToDisplay(String name) {
        orderIndex.setVisible(false);
        orderCreate.setVisible(false);

        switch (name) {
            case "index":
                orderIndex.setVisible(true);
                break;
            case "create":
                orderCreate.setVisible(true);
                break;
        }
    }

    public void addClothes() {
    }
}
