import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Slf4j
@Getter
@Setter
class InputData {

    private String recipient;
    private String theme;
    private String text;
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);


    public static InputData readFromConsole() {

        InputData inputFields = new InputData();
        try (Scanner scanner = new Scanner(System.in)) {

            log.info("Type recipient:");
            inputFields.setRecipient(scanner.nextLine());
            while (!isValidEmailAddress(inputFields.getRecipient())) {
                log.info("Type email in the right format");
                inputFields.setRecipient(scanner.nextLine());
            }

            log.info("Theme of the letter:");
            inputFields.setTheme(scanner.nextLine());

            log.info("Main text of the letter:");
            inputFields.setText(scanner.nextLine());
        }

        return inputFields;
    }

    static boolean isValidEmailAddress(String myAccountEmail) {

        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(myAccountEmail);
        return matcher.matches();
    }

}

@Slf4j
public class JavaMailUtil {

    static final String myAccountEmail = "post.dove.22@hotmail.com";
    static final String password = "Impostdove22";

     public static void sendEmail() throws MessagingException {
        Properties properties = new Properties();

        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp-mail.outlook.com");
        properties.put("mail.smtp.port", "587");


        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccountEmail, password);
            }
        });

        Message message = prepareMessage(session, InputData.readFromConsole());
        Transport.send(message);
        log.info("Message sent successfully");

    }

    private static Message prepareMessage(Session session, InputData inputFields) throws MessagingException {

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(myAccountEmail));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(inputFields.getRecipient()));
        message.setSubject(inputFields.getTheme());
        message.setText(inputFields.getText());
        return message;

    }
}