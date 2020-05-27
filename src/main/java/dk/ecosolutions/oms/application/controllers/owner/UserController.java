package dk.ecosolutions.oms.application.controllers.owner;

import dk.ecosolutions.oms.application.enums.Role;
import dk.ecosolutions.oms.domain.Location;
import dk.ecosolutions.oms.domain.User;
import dk.ecosolutions.oms.service.LocationService;
import dk.ecosolutions.oms.service.UserService;
import dk.ecosolutions.oms.service.helpers.AlertHelper;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.util.StringConverter;

import java.util.ArrayList;

public class UserController {
    @FXML
    private BorderPane userIndex, userCreate;
    @FXML
    private TableView<User> users;
    @FXML
    private TableColumn<User, String> nameColumn;
    @FXML
    private TableColumn<User, String> emailColumn;
    @FXML
    private TableColumn<User, String> passwordColumn;
    @FXML
    private TableColumn<User, String> roleColumn;
    @FXML
    private TableColumn<User, String> locationColumn;
    @FXML
    private TextField name, email, password;
    @FXML
    private ChoiceBox<Role> role;
    @FXML
    private ChoiceBox<Location> locations;

    @FXML
    public void initialize() {
        // Define the table view cell type
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        locationColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLocation().getName()));
        // Add choice box options
        role.getItems().add(Role.OWNER);
        role.getItems().add(Role.ASSISTENT);

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

        for (Location loc : LocationService.allLocations()) {
            locations.getItems().add(loc);
        }

        users.getItems().setAll(UserService.allUsers());
    }

    /**
     * Handle user button click save.
     */
    @FXML
    public void save() {
        if (validation()) {
            User user = new User();
            user.setName(name.getText().trim());
            user.setEmail(email.getText().trim());
            user.setPassword(password.getText().trim());
            user.setRole(role.getSelectionModel().getSelectedItem());
            user.setLocation(locations.getSelectionModel().getSelectedItem());
            UserService.createUser(user);
            users.getItems().add(user);
            name.clear();
            email.clear();
            password.clear();
            role.getSelectionModel().clearSelection();
            locations.getSelectionModel().clearSelection();
            viewToDisplay("index");
            AlertHelper.showInformationAlert("Delivery Point Created!");
        }
    }

    @FXML
    public void delete() {
        if (AlertHelper.confirmAlert("Are you sure you want to delete the user?")) {
            User selectedUser = users.getSelectionModel().getSelectedItem();
            if (UserService.deleteUser(selectedUser)) {
                users.getItems().remove(selectedUser);
            } else {
                AlertHelper.showErrorAlert("Could not be removed!");
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
        if (email.getText().trim().length() == 0) {
            messages.add("Email can not be empty!");
        }
        if (role.getSelectionModel().getSelectedItem() == null) {
            messages.add("Role must be selected!");
        }
        if (locations.getSelectionModel().getSelectedItem() == null) {
            messages.add("Location must be selected!");
        }
        if (messages.size() > 0) {
            AlertHelper.showErrorAlert(messages);
            return false;
        }
        return true;
    }

    @FXML
    public void changeDisplay(ActionEvent event) {
        Button btn = (Button) event.getSource();
        viewToDisplay(btn.getId());
    }

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
