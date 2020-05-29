package dk.ecosolutions.oms.application.controllers.driver;

import dk.ecosolutions.oms.domain.Location;
import dk.ecosolutions.oms.domain.Order;
import dk.ecosolutions.oms.domain.Route;
import dk.ecosolutions.oms.service.LocationService;
import dk.ecosolutions.oms.service.OrderService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import java.util.List;

/**
 * Handles the order transport steps, from start to delivery.
 *
 * @author Jens Christensen
 * @version 1.0.0
 */
public class RouteController {
    @FXML
    private TableView<Route> routes;

    @FXML
    private BorderPane routesIndex, routesView, routesProcess;
    @FXML
    private TableColumn<Route, String> locationColumn;
    @FXML
    private TableColumn<Route, Integer> orderColumn;
    @FXML
    public void initialize() {
        // Define the table view cell type
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        orderColumn.setCellValueFactory(new PropertyValueFactory<>("orders"));

        List<Location> locations = LocationService.allLocations();
        if (locations != null) {
            for (Location location : locations) {
                List<Order> order = OrderService.allOrder(1, location);

                if (order != null && order.size() > 0) {
                    Route route = new Route();
                    route.setLocation(location.getName());
                    route.setOrders(order.size());
                    routes.getItems().add(route);
                }
            }
        }
    }

    @FXML
    public void start() {
        Route route = routes.getSelectionModel().getSelectedItem();

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
