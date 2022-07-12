package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexHelper {
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    public static final Pattern VALID_RECIPIENT_NAME_REGEX =
            Pattern.compile("^[A-Z][a-z^0-9]$");

    public static boolean isValidRecipientName(String myRecipientName) {

        Matcher matcher = VALID_RECIPIENT_NAME_REGEX.matcher(myRecipientName);
        return matcher.matches();
    }

    public static boolean isValidEmailAddress(String myAccountEmail) {

        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(myAccountEmail);
        return matcher.matches();
    }
}
