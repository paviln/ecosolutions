package dk.ecosolutions.oms.application.controllers;

import dk.ecosolutions.oms.application.helpers.ValidationHelper;
import dk.ecosolutions.oms.application.helpers.ViewHelper;
import dk.ecosolutions.oms.domain.User;
import dk.ecosolutions.oms.service.AuthService;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.util.Collections;

/**
 * Handles authentication of users.
 *
 * @author Jens Christensen
 * @version 1.0
 * @since 1.0
 */

public class WelcomeController {
    private static User authenticatedUser;
    @FXML
    BorderPane welcome;
    @FXML
    TextField email, password;

    /**
     * Initialized after fxml is loaded
     */
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

    /**
     * Button action authenticate user.
     */
    @FXML
    public void login() {
        if (inputValidation(email, password)) {
            authenticatedUser = AuthService.userLogin(email.getText().trim(), password.getText().trim());

            if (authenticatedUser != null) {
                switch (authenticatedUser.getRole()) {
                    case OWNER:
                        ViewHelper.changeView("owner/base.fxml", welcome);
                        break;
                    case ASSISTANT:
                        ViewHelper.changeView("assistant/base.fxml", welcome);
                        break;
                    case DRIVER:
                        ViewHelper.changeView("driver/base.fxml", welcome);
                        break;
                    case WORKER:
                        ViewHelper.changeView("worker/base.fxml", welcome);
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

    /**
     * Displays error message.
     */
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

    /**
     * Get authenticated user.
     *
     * @return user
     */
    public static User getAuthenticatedUser() {
        return authenticatedUser;
    }
}