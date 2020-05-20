package dk.ecosolutions.oms.domain;

import dk.ecosolutions.oms.persistence.databse.UserDao;

import java.util.List;

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
