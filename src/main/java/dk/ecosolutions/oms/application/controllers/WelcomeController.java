package dk.ecosolutions.oms.application.controllers;

import dk.ecosolutions.oms.domain.User;
import dk.ecosolutions.oms.service.AuthService;
import dk.ecosolutions.oms.service.helpers.ValidationHelper;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.Collections;

public class WelcomeController {
    @FXML
    BorderPane welcome;

    @FXML
    TextField email, password;

    @FXML
    public void initialize() {
        email.setOnMouseClicked(event -> {
            ObservableList<String> styleClass = email.getStyleClass();
            if (styleClass.contains("error")) {
                styleClass.removeAll(Collections.singleton("error"));
            }
        });

        password.setOnMouseClicked(event -> {
            ObservableList<String> styleClass = password.getStyleClass();
            if (styleClass.contains("error")) {
                styleClass.removeAll(Collections.singleton("error"));
            }
        });

        Tooltip tooltipEmail = new Tooltip();
        tooltipEmail.setText("Your email must be format e@web.com");
        email.setTooltip(tooltipEmail);
        Tooltip tooltipPassword = new Tooltip();
        tooltipPassword.setText("Your password must be at least 8 characters in length");
        password.setTooltip(tooltipPassword);
    }

    @FXML
    public void login() {

        if (inputValidation(email, password)) {
            User user = AuthService.userLogin(email.getText().trim(), password.getText().trim());

            if (user != null) {
                switch (user.getRole_id()) {
                    case 1:
                        changeView("presentation/views/owner/dashboard.fxml");
                        break;
                    case 2:
                        changeView("presentation/views/assistant.fxml");
                        break;
                    default:
                        break;
                }
            } else {
                errorMessage();
            }
        }
    }

    /**
     * Check that the user input is valid a user.
     *
     * @return true if valid, false if not.
     */
    private boolean inputValidation(TextField email, TextField password) {
        boolean isValid = true;

        if (!ValidationHelper.isValidEmailAddress(email.getText().trim())) {
            isValid = false;
            ObservableList<String> styleClass = email.getStyleClass();
            if (!styleClass.contains("error")) {
                styleClass.add("error");
            }
        }

        if (password.getText().trim().length() == 0) {
            isValid = false;
            ObservableList<String> styleClass = password.getStyleClass();
            if (!styleClass.contains("error")) {
                styleClass.add("error");
            }
        }

        return isValid;
    }

    private void changeView(String name) {
        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("dk/ecosolutions/oms/" + name));
            Stage stage = (Stage) welcome.getScene().getWindow();
            stage.setScene(new Scene(root, 800, 500));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void errorMessage() {
        Label error = new Label("Wrong email or password!");
        error.setMaxWidth(Double.MAX_VALUE);
        Button close = new Button("X");
        close.setFocusTraversable(false);
        AnchorPane topbar = new AnchorPane(error, close);
        AnchorPane.setTopAnchor(error, 10.0);
        AnchorPane.setLeftAnchor(error, 10.0);
        AnchorPane.setRightAnchor(error, 10.0);
        AnchorPane.setTopAnchor(close, 20.0);
        AnchorPane.setRightAnchor(close, 20.0);
        error.setAlignment(Pos.CENTER);
        topbar.getStyleClass().add("alert");
        welcome.setTop(topbar);

        close.setOnMouseClicked(event -> {
            welcome.setTop(null);
        });
    }
}