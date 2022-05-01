import entity.Recipient;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import repository.RecipientDao;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Slf4j
@Getter
@Setter
class InputData {


    private String recipientEmail;
    private String theme;
    private String text;
    static InputData inputFields = new InputData();
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    public static final Pattern VALID_RECIPIENT_NAME_REGEX =
            Pattern.compile("^[A-Z][a-z^0-9]$");


    public static Recipient makeChoose() throws SQLException {
        RecipientDao recipientDao = new RecipientDao();

        log.info("Do you want to send the letter to recipient from table or to a new recipient ? type 'table' or 'new'");
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                String response = scanner.nextLine();
                if (response.equalsIgnoreCase("table")) {
                    List<Recipient> recipientDaoList = recipientDao.getAll();
                    for (Recipient r : recipientDaoList) {
                        log.info(r.toString());
                    }
                    log.info("Enter ID: ");
                    while (true) {
                        if (scanner.hasNextLong()) {
                            long id = scanner.nextLong();
                            if (id > 0 && id < recipientDaoList.size()) {
                                return recipientDao.getById(id);
                            } else {
                                log.info("ID is not valid! Try again ");
                                scanner.nextLong();
                            }
                        }
                    }
                }
                if (response.equalsIgnoreCase("new")) {
                    return newRecipient();
                } else {
                    log.info("Try again! Enter 'table' or 'new'.");
                    scanner.nextLine();
                }
            }
        }
    }

    public static Recipient newRecipient() {
        Recipient newRecipient = new Recipient();

        try (Scanner scanner = new Scanner(System.in)) {
            log.info("Enter recipient email: ");
            newRecipient.setEmail(scanner.nextLine());
            while (!isValidEmailAddress(newRecipient.getEmail())) {
                log.info("Type email in the right format");
                newRecipient.setEmail(scanner.nextLine());
            }
            log.info("Input recipient's name :");
            newRecipient.setName(scanner.nextLine());
            while (!isValidRecipientName(newRecipient.getName())) {
                log.info("Type recipient's name in the right format");
                newRecipient.setName(scanner.nextLine());
            }
            log.info("Input recipient's surname :");
            newRecipient.setSurname(scanner.nextLine());
            while (!isValidRecipientName(newRecipient.getSurname())) {
                log.info("Type recipient's surname in the right format");
                newRecipient.setSurname(scanner.nextLine());
            }
            log.info("Input recipient's patronymic :");
            newRecipient.setPatronymic(scanner.nextLine());
            while (!isValidRecipientName(newRecipient.getPatronymic())) {
                log.info("Type recipient's patronymic in the right format");
                newRecipient.setPatronymic(scanner.nextLine());
            }
        }
        return newRecipient;
    }

    public static InputData readFromConsole(Recipient recipient ) throws SQLException, NoSuchElementException {
        RecipientDao recipientDao = new RecipientDao();
        List<Recipient> recipientDaoList = recipientDao.getAll();


        if (recipient.getId() > 0 && recipient.getId() < recipientDaoList.size()) {
            log.info("You're going to send a letter to " + recipient.getEmail());
            inputFields.setRecipientEmail(recipient.getEmail());
            return writeEmail();
        }
        if (recipient.getId() == 0) {
            recipient.setId(recipientDaoList.size() + 1);
            inputFields.setRecipientEmail(recipient.getEmail());
            return writeEmail();
        }

        return null;
    }

    public static InputData writeEmail() {

        try (Scanner scanner = new Scanner(System.in)) {
            while (scanner.hasNextLine()) {
                log.info("Theme of the letter:");
                inputFields.setTheme(scanner.nextLine());

                log.info("Main text of the letter:");
                inputFields.setText(scanner.nextLine());

            }
        }
        return inputFields;
    }

    public static void addRecipient(Recipient newRecipient) throws SQLException {
        RecipientDao recipientDao = new RecipientDao();

        log.info("Do you want to add new recipient to the table ? yes/no");
        try (Scanner scanner = new Scanner(System.in)) {
            String s = scanner.nextLine();
            while (true) {
                if (s.equalsIgnoreCase("yes")) {
                    recipientDao.add(newRecipient);
                }
                if (s.equalsIgnoreCase("no")) {
                    break;
                } else {
                    log.info("Try again! Enter 'yes' or 'no'");
                    scanner.nextLine();
                }
            }

        }
    }

    static boolean isValidEmailAddress(String myAccountEmail) {

        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(myAccountEmail);
        return matcher.matches();
    }

    static boolean isValidRecipientName(String myRecipientName) {

        Matcher matcher = VALID_RECIPIENT_NAME_REGEX.matcher(myRecipientName);
        return matcher.matches();
    }


}


@Slf4j
public class JavaMailUtil {

    static final String myAccountEmail = "post.dove.22@hotmail.com";
    static final String password = "Impostdove22";

    public static void sendEmail() throws MessagingException, SQLException {
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


        InputData inputFields = InputData.readFromConsole(InputData.makeChoose());
        Message message = prepareMessage(session, inputFields);
        Transport.send(message);
        log.info("Message sent successfully");
        InputData.addRecipient(InputData.newRecipient());
        sendAgain();

    }


    private static Message prepareMessage(Session session, InputData inputFields) throws MessagingException {

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(myAccountEmail));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(inputFields.getRecipientEmail()));
        message.setSubject(inputFields.getTheme());
        message.setText(inputFields.getText());
        return message;

    }


    public static void sendAgain() throws SQLException {
        log.info("Do you want to send one more email ?");
        try (Scanner scanner = new Scanner(System.in)) {
            String s = scanner.nextLine();
            while (true) {
                if (s.equalsIgnoreCase("yes")) {
                    InputData.makeChoose();
                    break;
                } else if (s.equalsIgnoreCase("no")) {
                    break;
                } else {
                    log.info("Please type right word: 'yes' or 'no'");
                    scanner.nextLine();
                }
            }
        }

    }
}
