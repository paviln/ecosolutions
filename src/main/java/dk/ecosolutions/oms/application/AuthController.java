package dk.ecosolutions.oms.application;

import dk.ecosolutions.oms.domain.User;
import dk.ecosolutions.oms.persistence.databse.UserDao;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.List;

public class AuthController {
    @FXML
    BorderPane auth;

    @FXML
    TextField email, password;

    @FXML
    public void login() {
        UserDao userDao = new UserDao();

        List<User> users = userDao.all();

        if (users != null) {
            for (User user : users) {
                if (user.getEmail().equals(email.getText().trim()) && user.getPassword().equals(password.getText().trim())) {
                    switch (user.getRole_id()) {
                        case 1:
                            changeView("presentation/owner.fxml");
                            break;
                        case 2:
                            changeView("presentation/assistant.fxml");
                            break;
                        default:
                            break;
                    }
                }
            }
        }
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
