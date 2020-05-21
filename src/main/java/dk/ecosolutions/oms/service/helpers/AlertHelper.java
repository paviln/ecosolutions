package dk.ecosolutions.oms.service.helpers;

import javafx.scene.control.Alert;

import java.util.ArrayList;

public class AlertHelper {
    /**
     * Display error alert box.
     *
     * @param messages to be displayed.
     */
    public static void showErrorAlert(ArrayList<String> messages) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        StringBuilder alertMessage = new StringBuilder();
        for (String message : messages) {
            alertMessage.append("\n").append(message);
        }
        alert.setContentText(alertMessage.toString());
        alert.showAndWait();
    }

    /**
     * Display information alert box.
     *
     * @param message to be displayed.
     */
    public static void showInformationAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Message");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
