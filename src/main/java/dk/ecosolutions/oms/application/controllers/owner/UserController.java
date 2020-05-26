package dk.ecosolutions.oms.application.controllers.owner;

import dk.ecosolutions.oms.application.enums.Role;
import dk.ecosolutions.oms.domain.User;
import dk.ecosolutions.oms.service.UserService;
import dk.ecosolutions.oms.service.helpers.AlertHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

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
    private TextField name, email, password, location_id;
    @FXML
    private ChoiceBox<Role> role;

    @FXML
    public void initialize() {
        // Define the table view cell type
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location_id"));

        // Add choice box options
        role.getItems().add(Role.OWNER);
        role.getItems().add(Role.ASSISTENT);

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
            user.setLocation_id(Integer.parseInt(location_id.getText().trim()));
            UserService.createUser(user);
            users.getItems().add(user);
            name.clear();
            email.clear();
            role.getSelectionModel().clearSelection();
            location_id.clear();
            viewToDisplay("index");
            AlertHelper.showInformationAlert("Delivery Point Created!");
        }
    }

    @FXML
    public void removeUser() {
        if (AlertHelper.confirmAlert("LUL")) {
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
        if (location_id.getText().trim().length() == 0) {
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
