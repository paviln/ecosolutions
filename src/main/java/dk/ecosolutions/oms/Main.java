package dk.ecosolutions.oms;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("presentation/views/welcome.fxml"));
        primaryStage.setTitle("EcoSolutionsOMS");
        primaryStage.minWidthProperty().setValue(1012);
        primaryStage.minHeightProperty().setValue(600);
        primaryStage.setScene(new Scene(root, 1012, 600));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
