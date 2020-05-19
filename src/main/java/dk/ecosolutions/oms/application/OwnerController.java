package dk.ecosolutions.oms.application;

import dk.ecosolutions.oms.domain.Address;
import dk.ecosolutions.oms.domain.Location;
import dk.ecosolutions.oms.domain.LocationService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class OwnerController {
    @FXML
    TextField name, street, number, city, zip;

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
        location.getAddresses_id();

        if (validation(location)) {
            LocationService.createLocation(location);
        }
    }

    private boolean validation(Location location) {
        ArrayList<String> messages = new ArrayList<String>();

        String name = location.getName();
        String street = location.getAddress().getStreet();
        String number = location.getAddress().getNumber();
        String city = location.getAddress().getCity();
        String zip = location.getAddress().getZip();

        if (name.isEmpty()) {
            messages.add("The name can not be empty!");
        }
        if (street.isEmpty()) {
            messages.add("The address  can not be empty!");
        }
        if (number.isEmpty()) {
            messages.add("The number can not be empty!");
        }
        if (city.isEmpty()) {
            messages.add("The city can not be empty!");
        }
        if (zip.isEmpty()) {
            messages.add("The zip can not be empty!");
        } else if (zip.length() < 4) {
            messages.add("The zip code must be at least 4 digits!");
        }
        if (messages.size() > 0) {
            showErrorAlert(messages);
            return false;
        }
        return true;
    }

    private void showErrorAlert(ArrayList<String> messages) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid information");
        alert.setHeaderText(null);
        StringBuilder alertMessage = new StringBuilder();
        for (String message : messages) {
            alertMessage.append("\n").append(message);
        }
        alert.setContentText(alertMessage.toString());
        alert.showAndWait();
    }
}