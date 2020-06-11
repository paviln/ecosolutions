package dk.ecosolutions.oms.application.controllers;

import dk.ecosolutions.oms.application.helpers.DialogHelper;
import dk.ecosolutions.oms.domain.Clothe;
import dk.ecosolutions.oms.domain.Item;
import dk.ecosolutions.oms.domain.Order;
import dk.ecosolutions.oms.service.ClothesService;
import dk.ecosolutions.oms.service.CustomerService;
import dk.ecosolutions.oms.service.ItemService;
import dk.ecosolutions.oms.service.OrderService;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import java.sql.Timestamp;

/**
 * Handles the management of orders.
 *
 * @author Chamling Ram Rai
 * @version 1.0
 * @since 1.0
 */


public class OrderController {
    @FXML
    private AnchorPane orderIndex, orderCreate;
    @FXML
    private TableView<Order> orderTable;
    @FXML
    private TableColumn<Order, Integer> col_id, col_userID, col_customerID;
    @FXML
    private TableColumn<Order, Integer> col_status;
    @FXML
    private TableColumn<Order, Timestamp> col_dateTime;
    @FXML
    private ChoiceBox<Clothe> clothes;
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

    /**
     * Initialized after fxml is loaded
     */
    @FXML
    public void initialize() {
        col_id.setCellValueFactory(new PropertyValueFactory<Order, Integer>("id"));
        col_status.setCellValueFactory(new PropertyValueFactory<Order, Integer>("status"));
        col_dateTime.setCellValueFactory(new PropertyValueFactory<Order, Timestamp>("created_at"));
        col_userID.setCellValueFactory(new PropertyValueFactory<Order, Integer>("user_id"));
        col_customerID.setCellValueFactory(new PropertyValueFactory<Order, Integer>("customer_id"));
        orderTable.getItems().addAll(OrderService.allOrder());

        clothes.getItems().addAll(ClothesService.allClothes());

        col_clothe.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClothe().getName()));
        col_quantity.setCellValueFactory(new PropertyValueFactory<Item, Integer>("Quantity"));
    }

    @FXML
    public void save() {
        Order order = new Order();
        if (customerId.getText().trim().length() != 0 && itemsTable.getItems().size() > 0) {
            if (CustomerService.validCustomer(Integer.parseInt(customerId.getText()))) {
                order.setStatus(1);
                order.setItems(itemsTable.getItems());
                order.setLocation(WelcomeController.getAuthenticatedUser().getLocation());
                order.setCreated_at(new Timestamp(System.currentTimeMillis()));
                order.setUser_id(WelcomeController.getAuthenticatedUser().getId());
                order.setCustomer_id(Integer.parseInt(customerId.getText()));
                OrderService.addOrder(order);
                orderTable.getItems().add(order);
                DialogHelper.showInformationAlert("Saved Successfully");
                viewToDisplay("index");
            }
        } else {
            DialogHelper.showInformationAlert("One of the field is empty");
        }
    }

    @FXML
    public void delete() {

        Item item = itemsTable.getSelectionModel().getSelectedItem();
        if (item != null) {

            if (DialogHelper.confirmAlert("Confirm if You want to delete")) {
                ItemService.deleteItems(item);
                Item selectedItem = itemsTable.getSelectionModel().getSelectedItem();
                itemsTable.getItems().remove(selectedItem);

                quantity.clear();
            }
        }
    }

    /**
     * Button action get view to be changed.
     *
     * @param event button click event.
     */
    @FXML
    public void changeDisplay(ActionEvent event) {
        Button btn = (Button) event.getSource();
        viewToDisplay(btn.getId());
    }

    /**
     * Change displayed view to corresponding view.
     *
     * @param name view to be displayed.
     */
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

    public void addItem() {
        Item item = new Item();
        if (clothes.getItems().isEmpty() || quantity.getText().isEmpty()) {
            DialogHelper.showErrorAlert("One of the field is empty");
        } else {
            item.setClothe(clothes.getSelectionModel().getSelectedItem());
            item.setQuantity(Integer.parseInt(quantity.getText()));
            itemsTable.getItems().add(item);
        }
    }

    /**
     * This function allows only numeric input in the customerID and quantity text field
     * of Order
     *
     * @param keyEvent
     */

    public void numberValidation(KeyEvent keyEvent) {
        quantity.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                quantity.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        customerId.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                customerId.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    public void handOut() {
        Order selectedOrder = orderTable.getSelectionModel().getSelectedItem();
        if (selectedOrder != null && selectedOrder.getStatus() == 7) {
            selectedOrder.setStatus(selectedOrder.getStatus() + 1);
            OrderService.updateOrder(selectedOrder);
        }

    }
}