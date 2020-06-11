package dk.ecosolutions.oms.service;

import dk.ecosolutions.oms.domain.User;
import dk.ecosolutions.oms.persistence.database.UserDao;

import java.util.List;

/**
 * Validation of user login credentials.
 *
 * @author Jens Christensen
 * @version 1.0
 * @since 1.0
 */

public class AuthService {
    public static User userLogin(String email, String password) {
        UserDao userDao = new UserDao();
        List<User> users = userDao.all();

        if (users != null) {
            for (User user : users) {
                if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                    return user;
                }
            }
        }
        return null;
    }
}
