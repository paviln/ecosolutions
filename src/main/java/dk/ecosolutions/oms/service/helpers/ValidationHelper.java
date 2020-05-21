package dk.ecosolutions.oms.service.helpers;

import org.apache.commons.validator.routines.EmailValidator;

public class ValidationHelper {
    public static boolean isValidEmailAddress(String email) {
        return EmailValidator.getInstance().isValid(email);
    }
}
