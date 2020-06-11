package dk.ecosolutions.oms.service.helpers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Helper class for changing between views.
 *
 * @author Jens Christensen
 * @version 1.0
 * @since 1.0
 */

public class ViewHelper {
    public static void changeView(String name, Node context) {
        try {
            Parent root = FXMLLoader.load(ViewHelper.class.getClassLoader().getResource("dk/ecosolutions/oms/presentation/views/" + name));
            Stage stage = (Stage) context.getScene().getWindow();
            stage.setScene(new Scene(root, context.getScene().getWidth(), context.getScene().getHeight()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
