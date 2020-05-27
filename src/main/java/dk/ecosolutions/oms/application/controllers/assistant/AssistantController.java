package dk.ecosolutions.oms.application.controllers.assistant;

import dk.ecosolutions.oms.service.helpers.ViewHelper;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;

public class AssistantController {
    @FXML
    private BorderPane base;

    @FXML
    public void logout() {
        ViewHelper.changeView("welcome.fxml", base);
    }
}
