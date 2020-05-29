package dk.ecosolutions.oms.application.controllers.driver;

import dk.ecosolutions.oms.service.helpers.ViewHelper;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;

/**
 * Is used to handle driver base view.
 *
 * @author Jens Christensen
 * @version 1.0.0
 */

public class DriverController {
    @FXML
    private BorderPane base;

    @FXML
    public void logout() {
        ViewHelper.changeView("welcome.fxml", base);
    }
}
