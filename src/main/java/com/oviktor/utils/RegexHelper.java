package com.oviktor.utils;

import java.util.regex.Pattern;

public class RegexHelper {

    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    private static final Pattern VALID_RECIPIENT_NAME_REGEX =
            Pattern.compile("^[A-Z][a-z^0-9]$");

    public static boolean isValidEmailAddress(String email) {
        return RegexHelper.VALID_EMAIL_ADDRESS_REGEX.matcher(email).matches();
    }

    public static boolean isValidRecipientName(String email) {
        return RegexHelper.VALID_RECIPIENT_NAME_REGEX.matcher(email).matches();
    }
}
