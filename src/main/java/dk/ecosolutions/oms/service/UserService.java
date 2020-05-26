package dk.ecosolutions.oms.service;

import dk.ecosolutions.oms.application.controllers.WelcomeController;
import dk.ecosolutions.oms.domain.User;
import dk.ecosolutions.oms.persistence.database.UserDao;

import java.util.List;

public class UserService {
    public static void createUser(User user) {
        UserDao userDao = new UserDao();
        userDao.save(user);
    }

    public static List<User> allUsers() {
        UserDao userDao = new UserDao();
        return userDao.all();
    }

    public static Boolean deleteUser(User user) {
        if (user != null && WelcomeController.getAuthenticatedUser().getId() != user.getId()) {
            UserDao userDao = new UserDao();
            userDao.delete(user);
            return true;
        }
        return false;
    }
}
