package dk.ecosolutions.oms.application.controllers.owner;

import dk.ecosolutions.oms.application.enums.Type;
import dk.ecosolutions.oms.domain.Address;
import dk.ecosolutions.oms.domain.Location;
import dk.ecosolutions.oms.service.LocationService;
import dk.ecosolutions.oms.service.helpers.AlertHelper;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import java.util.ArrayList;

public class LocationController {
    @FXML
    private TableView<Location> deliveryPoints;
    @FXML
    private TableColumn<Location, String> nameColumn;
    @FXML
    private TableColumn<Location, String> streetColumn;
    @FXML
    private TableColumn<Location, String> numberColumn;
    @FXML
    private TableColumn<Location, String> cityColumn;
    @FXML
    private TableColumn<Location, String> zipColumn;
    @FXML
    private TableColumn<Location, Type> typeColumn;
    @FXML
    private BorderPane pointIndex, pointCreate;
    @FXML
    private TextField name, street, number, city, zip;
    @FXML
    private ChoiceBox<Type> type;

    @FXML
    public void initialize() {
        // Define the table view cell type
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        streetColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress().getStreet()));
        numberColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress().getNumber()));
        cityColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress().getCity()));
        zipColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress().getZip()));

        // Add choice box options
        type.getItems().add(Type.CleaningCentral);
        type.getItems().add(Type.DeliveryPoint);

        deliveryPoints.getItems().setAll(LocationService.allLocations());
    }

    /**
     * Handle user button click save.
     */
    @FXML
    public void saveDeliveryPoint() {
        Address address = new Address();
        address.setStreet(street.getText());
        address.setNumber(number.getText());
        address.setCity(city.getText());
        address.setZip(zip.getText());
        Location location = new Location();
        location.setName(name.getText());
        location.setType(type.getSelectionModel().getSelectedItem());
        location.setAddress(address);

        if (validation(location)) {
            LocationService.createLocation(location);
            deliveryPoints.getItems().add(location);
            name.clear();
            type.getSelectionModel().clearSelection();
            street.clear();
            number.clear();
            city.clear();
            zip.clear();
            viewToDisplay("index");
            AlertHelper.showInformationAlert("Delivery Point Created!");
        }
    }

    @FXML
    public void removeLocation() {
        if (AlertHelper.confirmAlert("This will remove all users belonging to this location!")) {
            Location selectedDeliveryPoint = deliveryPoints.getSelectionModel().getSelectedItem();
            if (LocationService.removeLocation(selectedDeliveryPoint)) {
                deliveryPoints.getItems().remove(selectedDeliveryPoint);
            } else {
                AlertHelper.showErrorAlert("Could not be removed!");
            }
        }
    }

    /**
     * Check that the user input is valid location information.
     *
     * @param location to be validated.
     * @return true if valid, false if not.
     */
    private boolean validation(Location location) {
        ArrayList<String> messages = new ArrayList<>();

        String name = location.getName();
        String street = location.getAddress().getStreet();
        String number = location.getAddress().getNumber();
        String city = location.getAddress().getCity();
        String zip = location.getAddress().getZip();

        if (name.trim().length() == 0) {
            messages.add("The name can not be empty!");
        }
        if (street.trim().length() == 0) {
            messages.add("The address  can not be empty!");
        }
        if (number.trim().length() == 0) {
            messages.add("The number can not be empty!");
        }
        if (city.trim().length() == 0) {
            messages.add("The city can not be empty!");
        }
        if (zip.trim().length() != 4) {
            messages.add("The zip code must be at 4 digits!");
        }
        if (messages.size() > 0) {
            AlertHelper.showErrorAlert(messages);
            return false;
        }
        return true;
    }

    @FXML
    public void changePointDisplay(ActionEvent event) {
        Button btn = (Button) event.getSource();
        viewToDisplay(btn.getId());
    }

    private void viewToDisplay(String name) {
        pointIndex.setVisible(false);
        pointCreate.setVisible(false);

        switch (name) {
            case "index":
                pointIndex.setVisible(true);
                break;
            case "create":
                pointCreate.setVisible(true);
                break;
        }
    }
}
