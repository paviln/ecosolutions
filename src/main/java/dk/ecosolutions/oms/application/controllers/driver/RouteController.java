package dk.ecosolutions.oms.application.controllers.driver;

import dk.ecosolutions.oms.domain.Item;
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
    private BorderPane routesIndex, routesView, routesProcess;
    @FXML
    private TableView<Location> pickup, delivery;
    @FXML
    private TableColumn<Location, String> pickupName, deliveryName;
    @FXML
    private TableView<Order> orders;
    @FXML
    private TableColumn<Order, String> orderId;
    @FXML
    private TableView<Item> items;
    @FXML
    private TableColumn<Item, String> itemId;
    @FXML
    private TableColumn<Item, String> itemCloth;
    @FXML
    private TableColumn<Item, String> itemQuantity;

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

        // Items table view
        itemId.setCellValueFactory(new PropertyValueFactory<>("id"));
        itemCloth.setCellValueFactory(new PropertyValueFactory<>("cloth"));
        itemQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        if (LocationService.allLocationsWithOrders(4).size() > 0) {
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
        delivery.getItems().addAll(LocationService.allLocationsWithOrders(5));
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
                orders.getItems().addAll(OrderService.allOrder(4));
            } else {
                orders.getItems().addAll(OrderService.allOrder(1, location));
                orders.getItems().addAll(OrderService.allOrder(4, location));
            }
            viewToDisplay("view");

        } else if (delivery.getSelectionModel().getSelectedItem() != null) {
            Location location = delivery.getSelectionModel().getSelectedItem();
            orders.getItems().clear();
            if (location.getId() == 0) {
                orders.getItems().addAll(OrderService.allOrder(2));
                orders.getItems().addAll(OrderService.allOrder(5));
            } else {
                orders.getItems().addAll(OrderService.allOrder(2, location));
                orders.getItems().addAll(OrderService.allOrder(5, location));
            }
            viewToDisplay("view");
        }
    }

    /**
     * View selected order items.
     */
    @FXML
    public void startOrder() {
        if (orders.getSelectionModel().getSelectedItem() != null) {
            Order order = orders.getSelectionModel().getSelectedItem();
            items.getItems().clear();
            items.getItems().addAll(order.getItems());
            viewToDisplay("process");
        }
    }

    /**
     * Update status of selected order.
     */
    @FXML
    public void scan() {
        if (items.getSelectionModel().getSelectedItem() != null) {
            Order order = orders.getSelectionModel().getSelectedItem();
            String value = DialogHelper.inputDialog("Scan", "Insert item ID", "ID:").trim();

            boolean isValid = false;
            if (!value.equals("")) {
                for (Item item : order.getItems()) {
                    if (item.getId() == Integer.parseInt(value)) {
                        items.getItems().remove(item);
                        isValid = true;
                        break;
                    }
                }
                if (isValid) {
                    DialogHelper.showInformationAlert("Item complete!");
                } else {
                    DialogHelper.showErrorAlert("Wrong item id!");
                }
                if (items.getItems().size() == 0) {
                    order.setStatus(order.getStatus() + 1);
                    OrderService.updateOrder(order);
                    DialogHelper.showInformationAlert("Order complete");
                    if (orders.getItems().size() == 1) {
                        if (pickup.getSelectionModel().getSelectedItem() != null) {
                            delivery.getItems().clear();
                            delivery.getItems().addAll(LocationService.allLocationsWithOrders(2));
                            delivery.getItems().addAll(LocationService.allLocationsWithOrders(5));

                            pickup.getItems().remove(pickup.getSelectionModel().getSelectedItem());
                        } else if (delivery.getSelectionModel().getSelectedItem() != null) {
                            delivery.getItems().remove(delivery.getSelectionModel().getSelectedItem());
                        }
                        viewToDisplay("index");
                    } else {
                        orders.getItems().remove(order);
                        viewToDisplay("view");
                    }
                }
            } else {
                DialogHelper.showErrorAlert("You most enter a id!");
            }
        }
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
        routesView.setVisible(false);
        routesProcess.setVisible(false);

        switch (name) {
            case "index":
                routesIndex.setVisible(true);
                break;
            case "view":
                routesView.setVisible(true);
                break;
            case "process":
                routesProcess.setVisible(true);
        }
    }
}
