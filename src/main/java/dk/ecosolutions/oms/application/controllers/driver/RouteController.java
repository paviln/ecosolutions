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
    private TableView<Location> locations;
    @FXML
    private TableColumn<Location, String> locationName;
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


    @FXML
    public void initialize() {
        // Locations table view
        locationName.setCellValueFactory(new PropertyValueFactory<>("name"));

        // Orders table view
        orderId.setCellValueFactory(new PropertyValueFactory<>("id"));

        // Items table view
        itemId.setCellValueFactory(new PropertyValueFactory<>("id"));
        itemCloth.setCellValueFactory(new PropertyValueFactory<>("cloth"));
        itemQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));


        locations.getItems().addAll(LocationService.allLocationsWithOrders(1));
    }

    @FXML
    public void startLocation() {
        if (locations.getSelectionModel().getSelectedItem() != null) {
            Location location = locations.getSelectionModel().getSelectedItem();
            orders.getItems().clear();
            orders.getItems().addAll(OrderService.allOrder(1, location));
            viewToDisplay("view");
        }
    }

    @FXML
    public void startOrder() {
        if (orders.getSelectionModel().getSelectedItem() != null) {
            Order order = orders.getSelectionModel().getSelectedItem();
            items.getItems().clear();
            items.getItems().addAll(order.getItems());
            viewToDisplay("process");
        }
    }

    @FXML
    public void scan() {
        if (items.getSelectionModel().getSelectedItem() != null) {
            Order order = orders.getSelectionModel().getSelectedItem();
            String value = DialogHelper.inputDialog("Scan", "Insert item ID", "ID:");
            if (value != null) {
                for (Item item : order.getItems()) {
                    if (item.getId() == Integer.parseInt(value)) {
                        items.getItems().remove(item);
                        DialogHelper.showInformationAlert("Item complete");
                    } else {
                        DialogHelper.showErrorAlert("Wrong item id");
                    }
                }
            }
            if (items.getItems().size() == 0) {
                order.setStatus(order.getStatus() + 1);
                OrderService.updateOrder(order);
                DialogHelper.showInformationAlert("Order complete");
                if (orders.getItems().size() == 1) {
                    locations.getItems().remove(locations.getSelectionModel().getSelectedItem());
                    viewToDisplay("index");
                } else {
                    orders.getItems().remove(order);
                    viewToDisplay("view");
                }
            }
        }
    }

    @FXML
    public void changeDisplay(ActionEvent event) {
        Button btn = (Button) event.getSource();
        viewToDisplay(btn.getId());
    }

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
