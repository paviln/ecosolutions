package dk.ecosolutions.oms.application.controllers.driver;

import dk.ecosolutions.oms.domain.Item;
import dk.ecosolutions.oms.domain.Location;
import dk.ecosolutions.oms.domain.Order;
import dk.ecosolutions.oms.domain.Route;
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
    private TableColumn<Location, String> nameColumn;
    @FXML
    private TableView<Order> orders;
    @FXML
    private TableColumn<Route, String> orderIdColumn;
    @FXML
    private TableView<Item> items;
    @FXML
    private TableColumn<Item, String> itemIdColumn;

    @FXML
    public void initialize() {
        // Define the table view cell type
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        orderIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        locations.getItems().addAll(LocationService.allLocations());
    }

    @FXML
    public void start() {
        Location location = locations.getSelectionModel().getSelectedItem();
        orders.getItems().clear();
        orders.getItems().addAll(OrderService.allOrder(1, location));
        viewToDisplay("view");
    }

    @FXML
    public void startOrder() {
        Order order = orders.getSelectionModel().getSelectedItem();
        items.getItems().clear();
        for (Item item : order.getItems()) {
            items.getItems().add(item);
        }
        viewToDisplay("process");
    }

    @FXML
    public void scan() {
        Order order = orders.getSelectionModel().getSelectedItem();
        DialogHelper.inputDialog("Scan", "Insert item ID", "ID:");
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
