package dk.ecosolutions.oms.application;

import dk.ecosolutions.oms.domain.User;
import dk.ecosolutions.oms.persistence.UserDao;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.util.List;

public class AuthController {

    @FXML
    TextField email, password;

    @FXML
    public void login() {
        UserDao userDao = new UserDao();

        List<User> users = userDao.all();

        if (users != null) {
            for (User user : users) {
                if (user.getEmail().equals(email.getText().trim()) && user.getPassword().equals(password.getText().trim())) {
                    System.out.println("test");
                }
            }
        }
    }
}
