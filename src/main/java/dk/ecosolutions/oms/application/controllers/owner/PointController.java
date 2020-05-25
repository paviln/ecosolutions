package dk.ecosolutions.oms.application.controllers.owner;

import dk.ecosolutions.oms.domain.Address;
import dk.ecosolutions.oms.domain.Location;
import dk.ecosolutions.oms.service.LocationService;
import dk.ecosolutions.oms.service.helpers.AlertHelper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.util.ArrayList;

public class PointController {
    @FXML
    private TableView<Location> deliveryPoints;
    @FXML
    private TableColumn<Location, String> nameColumn;
    @FXML
    private TableColumn<Address, String> streetColumn;
    @FXML
    private TableColumn<Address, String> numberColumn;
    @FXML
    private TableColumn<Address, String> cityColumn;
    @FXML
    private TableColumn<Address, String> zipColumn;
    @FXML
    private BorderPane pointIndex;
    @FXML
    private AnchorPane pointCreate;
    @FXML
    private TextField name, street, number, city, zip;

    @FXML
    public void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<Location, String>("name"));
        streetColumn.setCellValueFactory(new PropertyValueFactory<Address, String>("address"));
        numberColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getStreet()));
        cityColumn.setCellValueFactory(new PropertyValueFactory<Address, String>("city"));
        zipColumn.setCellValueFactory(new PropertyValueFactory<Address, String>("zip"));
        //actionsColumn.setCellValueFactory(new PropertyValueFactory<>("actions"));

        deliveryPoints.getItems().setAll(LocationService.allDeliveryPoints());
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
        location.setAddress(address);
        location.setTypes_id(1);

        if (validation(location)) {
            LocationService.createLocation(location);
            name.clear();
            street.clear();
            number.clear();
            city.clear();
            zip.clear();
            AlertHelper.showInformationAlert("Delivery Point Created!");
        }
    }

    /**
     * Check that the user input is valid location information.
     *
     * @param location to be validated.
     * @return true if valid, false if not.
     */
    private boolean validation(Location location) {
        ArrayList<String> messages = new ArrayList<String>();

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
        pointIndex.setVisible(false);
        pointCreate.setVisible(false);

        switch (btn.getId()) {
            case "index":
                pointIndex.setVisible(true);
                break;
            case "create":
                pointCreate.setVisible(true);
                break;
        }
    }
}
