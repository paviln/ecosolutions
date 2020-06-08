package dk.ecosolutions.oms.application.controllers.owner;

import dk.ecosolutions.oms.service.helpers.ViewHelper;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;

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
