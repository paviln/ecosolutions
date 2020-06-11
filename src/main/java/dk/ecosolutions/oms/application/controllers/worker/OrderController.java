package dk.ecosolutions.oms.application.controllers.worker;

import dk.ecosolutions.oms.application.helpers.DialogHelper;
import dk.ecosolutions.oms.domain.Item;
import dk.ecosolutions.oms.domain.Order;
import dk.ecosolutions.oms.persistence.database.OrderDoa;
import dk.ecosolutions.oms.service.ItemService;
import dk.ecosolutions.oms.service.OrderService;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import java.sql.Timestamp;

/**
 * Handles transport of orders.
 *
 * @author Jens Christensen
 * @version 1.0.0
 */

public class OrderController {
    @FXML
    BorderPane orderIndex, orderShow, orderItems;
    @FXML
    TableView<Order> rOrders, wOrders;
    @FXML
    TableView<Item> rItems, wItems;
    @FXML
    TableColumn<Order, Integer> rId, wOrderId, wItemId;
    @FXML
    TableColumn<Order, Timestamp> rCreated, wCreated;
    @FXML
    TableColumn<Item, String> rClothe, wClothe;
    @FXML
    TableColumn<Item, Integer> rQuantity, wQuantity;

    /**
     * Initialized after fxml is loaded
     */
    @FXML
    public void initialize() {
        rId.setCellValueFactory(new PropertyValueFactory<>("id"));
        rCreated.setCellValueFactory(new PropertyValueFactory<>("created_at"));
        rOrders.getItems().addAll(OrderService.allOrder(3));
        wOrderId.setCellValueFactory(new PropertyValueFactory<>("id"));
        wCreated.setCellValueFactory(new PropertyValueFactory<>("created_at"));
        wOrders.getItems().addAll(OrderService.allOrder(4));

        rClothe.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClothe().getName()));
        rQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        wItemId.setCellValueFactory(new PropertyValueFactory<>("id"));
        wClothe.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClothe().getName()));
        wQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
    }

    /**
     * Button action start processing selected order.
     */
    @FXML
    public void start() {
        Order selectedOrder = rOrders.getSelectionModel().getSelectedItem();

        if (selectedOrder != null) {
            if (DialogHelper.productDialog() == selectedOrder.getId()) {
                selectedOrder.setStatus(selectedOrder.getStatus() + 1);
                OrderService.updateOrder(selectedOrder);
                rOrders.getItems().remove(selectedOrder);
                System.out.println("Print labels");
                wOrders.getItems().add(selectedOrder);
            } else {
                DialogHelper.showErrorAlert("Wrong order id!");
            }
        }
    }

    /**
     * Button action show selected order.
     */
    @FXML
    public void show() {
        Order order = rOrders.getSelectionModel().getSelectedItem();
        if (order != null) {
            rItems.getItems().clear();
            rItems.getItems().addAll(ItemService.allItem(order));
            viewToDisplay("show");
        }
    }

    @FXML
    public void finish() {
        Order order = wOrders.getSelectionModel().getSelectedItem();
        if (order != null) {
            wItems.getItems().clear();
            wItems.getItems().addAll(ItemService.allItem(order));
            viewToDisplay("items");
        }
    }

    @FXML
    public void scan() {
        Item item = wItems.getSelectionModel().getSelectedItem();
        if (item != null) {
            if (DialogHelper.itemDialog() == item.getId()) {
                wItems.getItems().remove(item);
                if (wItems.getItems().size() == 0) {
                    Order order = wOrders.getSelectionModel().getSelectedItem();
                    order.setStatus(order.getStatus() + 1);
                    OrderDoa orderDoa = new OrderDoa();
                    orderDoa.update(order);
                    wOrders.getItems().remove(order);
                    viewToDisplay("index");
                }
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
        orderShow.setVisible(false);
        orderItems.setVisible(false);

        switch (name) {
            case "index":
                orderIndex.setVisible(true);
                break;
            case "show":
                orderShow.setVisible(true);
                break;
            case "items":
                orderItems.setVisible(true);
                break;
        }
    }
}
