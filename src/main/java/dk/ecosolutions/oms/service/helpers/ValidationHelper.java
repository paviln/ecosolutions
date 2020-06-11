package dk.ecosolutions.oms.service.helpers;

import org.apache.commons.validator.routines.EmailValidator;

/**
 * Validation helper class.
 *
 * @author Jens Christensen
 * @version 1.0
 * @since 1.0
 */

public class ValidationHelper {
    public static boolean isValidEmailAddress(String email) {
        return EmailValidator.getInstance().isValid(email);
    }
}
