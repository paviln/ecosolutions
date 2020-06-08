package dk.ecosolutions.oms.application.controllers.driver;

import dk.ecosolutions.oms.domain.Location;
import dk.ecosolutions.oms.domain.Order;
import dk.ecosolutions.oms.service.LocationService;
import dk.ecosolutions.oms.service.OrderService;
import dk.ecosolutions.oms.service.helpers.DialogHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

/**
 * Handles the order transport steps, from start to delivery.
 *
 * @author Jens Christensen
 * @version 1.0.0
 */
public class RouteController {
    @FXML
    private BorderPane routesIndex, routesOrders;
    @FXML
    private TableView<Location> pickup, delivery;
    @FXML
    private TableColumn<Location, String> pickupName, deliveryName;
    @FXML
    private TableView<Order> orders;
    @FXML
    private TableColumn<Order, String> orderId;

    /**
     * Called after fxml is loaded
     */
    @FXML
    public void initialize() {
        // Pickup table view
        pickupName.setCellValueFactory(new PropertyValueFactory<>("name"));

        // Delivery table view
        deliveryName.setCellValueFactory(new PropertyValueFactory<>("name"));

        // Orders table view
        orderId.setCellValueFactory(new PropertyValueFactory<>("id"));

        reloadLocations();
    }

    private void reloadLocations() {
        pickup.getItems().clear();
        delivery.getItems().clear();
        if (LocationService.allLocationsWithOrders(5).size() > 0) {
            Location location = new Location();
            location.setId(0);
            location.setName("Central");
            location.setPriority(0);
            pickup.getItems().add(location);
        }
        pickup.getItems().addAll(LocationService.allLocationsWithOrders(1));

        if (LocationService.allLocationsWithOrders(2).size() > 0) {
            Location location = new Location();
            location.setId(0);
            location.setName("Central");
            location.setPriority(0);
            delivery.getItems().add(location);
        }
        delivery.getItems().addAll(LocationService.allLocationsWithOrders(6));
    }

    /**
     * View selected location orders.
     */
    @FXML
    public void startLocation() {
        if (pickup.getSelectionModel().getSelectedItem() != null) {
            Location location = pickup.getSelectionModel().getSelectedItem();
            orders.getItems().clear();
            if (location.getId() == 0) {
                orders.getItems().addAll(OrderService.allOrder(1));
                orders.getItems().addAll(OrderService.allOrder(5));
            } else {
                orders.getItems().addAll(OrderService.allOrder(1, location));
                orders.getItems().addAll(OrderService.allOrder(5, location));
            }
            viewToDisplay("orders");

        } else if (delivery.getSelectionModel().getSelectedItem() != null) {
            Location location = delivery.getSelectionModel().getSelectedItem();
            orders.getItems().clear();
            if (location.getId() == 0) {
                orders.getItems().addAll(OrderService.allOrder(2));
                orders.getItems().addAll(OrderService.allOrder(6));
            } else {
                orders.getItems().addAll(OrderService.allOrder(2, location));
                orders.getItems().addAll(OrderService.allOrder(6, location));
            }
            viewToDisplay("orders");
        }
    }

    /**
     * View selected order items.
     */
    @FXML
    public void startOrder() {
        int orderId = DialogHelper.productDialog();
        Order selectedOrder = orders.getSelectionModel().getSelectedItem();

        if (selectedOrder != null && orderId == selectedOrder.getId()) {
            selectedOrder.setStatus(selectedOrder.getStatus() + 1);
            OrderService.updateOrder(selectedOrder);
            orders.getItems().remove(selectedOrder);
            DialogHelper.showInformationAlert("Order has been delivered!");
        } else {
            DialogHelper.showErrorAlert("Wrong order id!");
        }
        if (orders.getItems().size() == 0) {
            DialogHelper.showInformationAlert("Location orders complete!");
            reloadLocations();
            viewToDisplay("index");
        }
    }

    @FXML
    public void refresh() {
        reloadLocations();
    }

    @FXML
    public void back() {
        reloadLocations();
        viewToDisplay("index");
    }

    /**
     * Get view to be changed to.
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
        routesIndex.setVisible(false);
        routesOrders.setVisible(false);

        switch (name) {
            case "index":
                routesIndex.setVisible(true);
                break;
            case "orders":
                routesOrders.setVisible(true);
                break;
        }
    }
}
