package dk.ecosolutions.oms.application.controllers.owner;

import dk.ecosolutions.oms.application.helpers.DialogHelper;
import dk.ecosolutions.oms.application.helpers.ValidationHelper;
import dk.ecosolutions.oms.domain.Location;
import dk.ecosolutions.oms.domain.Role;
import dk.ecosolutions.oms.domain.User;
import dk.ecosolutions.oms.service.LocationService;
import dk.ecosolutions.oms.service.UserService;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.util.StringConverter;

import java.util.ArrayList;

/**
 * Handles management of users.
 *
 * @author Jens Christensen
 * @version 1.0.0
 */

public class UserController {
    @FXML
    private BorderPane userIndex, userCreate;
    @FXML
    private TableView<User> user;
    @FXML
    private TableColumn<User, String> nameColumn;
    @FXML
    private TableColumn<User, String> phoneColumn;
    @FXML
    private TableColumn<User, String> emailColumn;
    @FXML
    private TableColumn<User, String> passwordColumn;
    @FXML
    private TableColumn<User, String> roleColumn;
    @FXML
    private TableColumn<User, String> locationColumn;
    @FXML
    private TextField name, phone, email, password;
    @FXML
    private ChoiceBox<Role> role;
    @FXML
    private ChoiceBox<Location> locations;

    /**
     * Initialized after fxml is loaded.
     */
    @FXML
    public void initialize() {
        // Define the table view cell type
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        locationColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLocation().getName()));
        // Add choice box options
        role.getItems().add(Role.OWNER);
        role.getItems().add(Role.ASSISTANT);
        role.getItems().add(Role.DRIVER);
        role.getItems().addAll(Role.WORKER);

        locations.setConverter(new StringConverter<Location>() {
            @Override
            public String toString(Location object) {
                return object.getName();
            }

            @Override
            public Location fromString(String string) {

                return locations.getItems().stream().filter(ap ->
                        ap.getName().equals(string)).findFirst().orElse(null);
            }
        });

        locations.getItems().setAll(LocationService.allLocations());

        user.getItems().setAll(UserService.allUsers());
    }

    /**
     * Handle user button click save.
     */
    @FXML
    public void save() {
        if (validation()) {
            User user = new User();
            user.setName(name.getText().trim());
            user.setPhone(phone.getText().trim());
            user.setEmail(email.getText().trim());
            user.setPassword(password.getText().trim());
            user.setRole(role.getSelectionModel().getSelectedItem());
            user.setLocation(locations.getSelectionModel().getSelectedItem());
            UserService.createUser(user);
            this.user.getItems().add(user);
            name.clear();
            email.clear();
            password.clear();
            role.getSelectionModel().clearSelection();
            locations.getSelectionModel().clearSelection();
            viewToDisplay("index");
            DialogHelper.showInformationAlert("Delivery Point Created!");
        }
    }

    /**
     * Button action delete user and related orders.
     */
    @FXML
    public void delete() {
        if (DialogHelper.confirmAlert("All user related orders will be deleted aswell!")) {
            User selectedUser = user.getSelectionModel().getSelectedItem();
            if (UserService.deleteUser(selectedUser)) {
                user.getItems().remove(selectedUser);
            } else {
                DialogHelper.showErrorAlert("Could not be removed!");
            }
        }
    }

    /**
     * Check that the user input is valid location information.
     *
     * @return true if valid, false if not.
     */
    private boolean validation() {
        ArrayList<String> messages = new ArrayList<>();

        if (name.getText().trim().length() == 0) {
            messages.add("Name can not be empty!");
        }
        if (phone.getText().trim().length() < 8) {
            messages.add("Phone must be at least 8 digts!");
        }
        if (!ValidationHelper.isValidEmailAddress(email.getText().trim())) {
            messages.add("Email not valid!");
        }
        if (role.getSelectionModel().getSelectedItem() == null) {
            messages.add("Role must be selected!");
        }
        if (locations.getSelectionModel().getSelectedItem() == null) {
            messages.add("Location must be selected!");
        }
        if (messages.size() > 0) {
            DialogHelper.showErrorAlert(messages);
            return false;
        }

        return true;
    }

    /**
     * Button action changes view as desired.
     */
    @FXML
    public void changeDisplay(ActionEvent event) {
        Button btn = (Button) event.getSource();
        viewToDisplay(btn.getId());
    }

    /**
     * Change visibility of views.
     *
     * @param name to be displayed.
     */
    private void viewToDisplay(String name) {
        userIndex.setVisible(false);
        userCreate.setVisible(false);

        switch (name) {
            case "index":
                userIndex.setVisible(true);
                break;
            case "create":
                userCreate.setVisible(true);
                break;
        }
    }
}
