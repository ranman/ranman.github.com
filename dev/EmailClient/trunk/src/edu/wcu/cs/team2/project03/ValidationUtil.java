package edu.wcu.cs.team2.project03;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Class used to validate whether or not an inputted email or phone
 * is valid.
 * @author Joseph Randall Hunt
 * @author Brian Lenau
 * @author Joshua Davis
 * @version Nov 10, 2010
 */
public final class ValidationUtil {
    /**
     * Regular Expression for validating an email address.
     */
    public static final String EMAIL_REGEX = ".+@.+\\.[a-z]+";
    
    /**
     * Regualr Expression for validating a phone number.
     */
    public static final String PHONE_REGEX =
                                           "(\\d-)?(\\d{3}-)?\\d{3}-\\d{4}";

    /**
     * Returns a boolean based on whether an email address is valid.
     * @param str the inputted email address
     * @return whether the email address is valid
     */
    public static boolean isValidEmail(String str) {
        return str.matches(EMAIL_REGEX);
    }

    /**
     * Returns a boolean based on whether a phone number is valid.
     * @param str the inputted phone number
     * @return whether the phone number is valid.
     */
    public static boolean isValidPhone(String str) {
        return str.matches(PHONE_REGEX);
    }
    
    /**
     * Returns whether the smtp Host is valid.
     * @param the given smtp Host
     * @return whether the smtp Host is valid
     */
    public static boolean isValidHost(String str) {
        try {
            InetAddress.getByName(str);
            return true;
        } catch (UnknownHostException e) {
            return false;
        }
    }
}
