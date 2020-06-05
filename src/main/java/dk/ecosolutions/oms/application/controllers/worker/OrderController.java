package dk.ecosolutions.oms.application.controllers.worker;

import dk.ecosolutions.oms.domain.Item;
import dk.ecosolutions.oms.domain.Order;
import dk.ecosolutions.oms.service.ItemService;
import dk.ecosolutions.oms.service.OrderService;
import dk.ecosolutions.oms.service.helpers.DialogHelper;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import java.sql.Timestamp;

public class OrderController {
    @FXML
    BorderPane orderIndex, orderShow;
    @FXML
    TableView<Order> rOrders, wOrders;
    @FXML
    TableView<Item> items;
    @FXML
    TableColumn<Order, Integer> rId, wId;
    @FXML
    TableColumn<Order, Timestamp> rCreated, wCreated;
    @FXML
    TableColumn<Item, String> clothe;
    @FXML
    TableColumn<Item, Integer> quantity;


    @FXML
    public void initialize() {
        rId.setCellValueFactory(new PropertyValueFactory<>("id"));
        rCreated.setCellValueFactory(new PropertyValueFactory<>("created_at"));
        rOrders.getItems().addAll(OrderService.allOrder(3));

        wId.setCellValueFactory(new PropertyValueFactory<>("id"));
        wCreated.setCellValueFactory(new PropertyValueFactory<>("created_at"));
        wOrders.getItems().addAll(OrderService.allOrder(4));

        clothe.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClothe().getName()));
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
    }

    @FXML
    public void start() {
        int orderId;
        try {
            orderId = Integer.parseInt(DialogHelper.inputDialog("Scan Product", "Please enter order ID", "ID"));
        } catch (NumberFormatException e) {
            orderId = 0;
        }
        Order selectedOrder = rOrders.getSelectionModel().getSelectedItem();

        if (selectedOrder != null && orderId == selectedOrder.getId()) {
            selectedOrder.setStatus(selectedOrder.getStatus() + 1);
            OrderService.updateOrder(selectedOrder);
            rOrders.getItems().remove(selectedOrder);
            System.out.println("Print labels");
            wOrders.getItems().add(selectedOrder);
        } else {
            DialogHelper.showErrorAlert("Wrong order id!");
        }
    }

    @FXML
    public void show() {
        Order order = rOrders.getSelectionModel().getSelectedItem();
        if (order != null) {
            items.getItems().clear();
            items.getItems().addAll(ItemService.allItem(order));
        }
        viewToDisplay("show");
    }

    @FXML
    public void changeDisplay(ActionEvent event) {
        Button btn = (Button) event.getSource();
        viewToDisplay(btn.getId());
    }

    private void viewToDisplay(String name) {
        orderIndex.setVisible(false);
        orderShow.setVisible(false);

        switch (name) {
            case "index":
                orderIndex.setVisible(true);
                break;
            case "show":
                orderShow.setVisible(true);
                break;
        }
    }
}
