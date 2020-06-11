package dk.ecosolutions.oms.application.controllers.owner;

import dk.ecosolutions.oms.application.helpers.ViewHelper;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;

/**
 * Handles logout of user.
 *
 * @author Jens Christensen
 * @version 1.0.0
 */

public class OwnerController {
    @FXML
    private BorderPane base;

    /**
     * Button action to show the login view.
     */
    @FXML
    public void logout() {
        ViewHelper.changeView("welcome.fxml", base);
    }
}
