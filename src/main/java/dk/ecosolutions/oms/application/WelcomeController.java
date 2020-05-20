package dk.ecosolutions.oms.application;

import dk.ecosolutions.oms.domain.AuthService;
import dk.ecosolutions.oms.domain.User;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.Collections;

public class WelcomeController {
    @FXML
    BorderPane auth;

    @FXML
    TextField email, password;

    @FXML
    public void initialize() {
    }

    @FXML
    public void login() {

        if (textFieldValidation(email, password)) {
            User user = AuthService.userLogin(email.getText().trim(), password.getText().trim());

            if (user != null) {
                switch (user.getRole_id()) {
                    case 1:
                        changeView("presentation/views/owner.fxml");
                        break;
                    case 2:
                        changeView("presentation/views/assistant.fxml");
                        break;
                    default:
                        break;
                }
            }
        }
    }

    private boolean textFieldValidation(TextField... textField) {
        boolean idValid = true;

        for (TextField tf : textField) {
            ObservableList<String> styleClass = tf.getStyleClass();
            if (tf.getText().trim().length() == 0) {
                if (!styleClass.contains("error")) {
                    styleClass.add("error");
                }
                idValid = false;
            } else {
                styleClass.removeAll(Collections.singleton("error"));
            }
            tf.setOnMouseClicked(event -> {
                if (styleClass.contains("error")) {
                    styleClass.removeAll(Collections.singleton("error"));
                }
            });
        }

        return idValid;
    }

    private void changeView(String name) {
        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("dk/ecosolutions/oms/" + name));
            Stage stage = (Stage) auth.getScene().getWindow();
            stage.setScene(new Scene(root, 800, 500));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}