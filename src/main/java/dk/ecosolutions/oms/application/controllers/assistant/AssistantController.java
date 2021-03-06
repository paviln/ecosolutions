package dk.ecosolutions.oms.application.controllers.assistant;

import dk.ecosolutions.oms.application.helpers.ViewHelper;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;

public class AssistantController {
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
