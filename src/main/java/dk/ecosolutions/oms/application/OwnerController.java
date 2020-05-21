package dk.ecosolutions.oms.application;

import dk.ecosolutions.oms.domain.Address;
import dk.ecosolutions.oms.domain.Location;
import dk.ecosolutions.oms.service.LocationService;
import dk.ecosolutions.oms.service.helpers.AlertHelper;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class OwnerController {
    @FXML
    TextField name, street, number, city, zip;

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
}