package dk.ecosolutions.oms.service.helpers;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Sample dialog alerts helper class.
 *
 * @author Jens Christensen
 * @version 1.0
 * @since 1.0
 */

public class DialogHelper {
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

    public static void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static boolean confirmAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(message);
        alert.setContentText("Are you ok with this?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK)
            return true;

        return false;
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

    /**
     * @return
     */
    public static String inputDialog(String title, String header, String content) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(title);
        dialog.setHeaderText(header);
        dialog.setContentText(content);

        Optional<String> result = dialog.showAndWait();
        return result.orElse(null);
    }

    public static int productDialog() {
        int id;
        try {
            id = Integer.parseInt(DialogHelper.inputDialog("Scan Product", "Please enter order ID", "ID"));
        } catch (NumberFormatException e) {
            id = 0;
        }
        return id;
    }

    public static int itemDialog() {
        int id;
        try {
            id = Integer.parseInt(DialogHelper.inputDialog("Scan Item", "Please enter item ID", "ID"));
        } catch (NumberFormatException e) {
            id = 0;
        }
        return id;
    }
}
