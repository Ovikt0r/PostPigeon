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
    private String recipientName;
    private String recipientSurname;
    private String recipientPatronymic;
    private String theme;
    private String text;
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    public static final Pattern VALID_RECIPIENT_NAME_REGEX =
            Pattern.compile("^[A-Z][a-z^0-9]$");


    public static long chooseRecipient() throws SQLException {
        RecipientDao recipientDao = new RecipientDao();
        log.info("This program sends email to a selected recipient. You should decide on the recipient");
        List<Recipient> recipientDaoList = recipientDao.getAll();
        for (Recipient r : recipientDaoList) {
            log.info(r.toString());
        }
        log.info("DO you want to choose the recipient from the table ? Choose one and enter ID ");
        log.info("DO you want to send the letter to new recipient? In this case type 'new'");

            try (Scanner scanner = new Scanner(System.in)) {
                while (true) {
                if (scanner.hasNextLong()) {
                    return scanner.nextLong();
                } else {
                    String newRecipient = scanner.next();
                    if (newRecipient.equalsIgnoreCase("new")) {
                        readFromConsole(null);
                        break;
                    } else
                        log.info("You input no data. Please try again");
                    scanner.nextLine();
                }
            }
        }
        return 1;
    }

    public static Recipient getRecipientFromTable() throws SQLException {
        RecipientDao recipientDao = new RecipientDao();
        Recipient recipient;

        recipient = recipientDao.getById(chooseRecipient());
        return recipient;
    }

    public static InputData readFromConsole(Recipient recipient) throws SQLException, NoSuchElementException {

        InputData inputFields = new InputData();

        if (recipient == null) {
            try (Scanner scanner = new Scanner(System.in)) {
                log.info("Input recipient's email :");
                inputFields.setRecipientEmail(scanner.nextLine());
                while (!isValidEmailAddress(inputFields.getRecipientEmail())) {
                    log.info("Type email in the right format");
                    inputFields.setRecipientEmail(scanner.nextLine());
                }
                log.info("Input recipient's name :");
                inputFields.setRecipientName(scanner.nextLine());
                while (!isValidRecipientName(inputFields.getRecipientEmail())) {
                    log.info("Type recipient's name in the right format");
                    inputFields.setRecipientName(scanner.nextLine());
                }
                log.info("Input recipient's surname :");
                inputFields.setRecipientSurname(scanner.nextLine());
                while (!isValidRecipientName(inputFields.getRecipientSurname())) {
                    log.info("Type recipient's surname in the right format");
                    inputFields.setRecipientSurname(scanner.nextLine());
                }
                log.info("Input recipient's patronymic :");
                inputFields.setRecipientPatronymic(scanner.nextLine());
                while (!isValidRecipientName(inputFields.getRecipientPatronymic())) {
                    log.info("Type recipient's patronymic in the right format");
                    inputFields.setRecipientPatronymic(scanner.nextLine());
                }

                log.info("Theme of the letter:");
                inputFields.setTheme(scanner.nextLine());

                log.info("Main text of the letter:");
                inputFields.setText(scanner.nextLine());

            }
        } else if (recipient != null) {
            log.info("Do you want to send the letter to " + recipient.getEmail() + "? yes/no");
            try (Scanner scanner = new Scanner(System.in)) {
                while (true) {
                    if (scanner.hasNext()) {
                        String s = scanner.nextLine();
                        if (s.equalsIgnoreCase("yes")) {
                            inputFields.setRecipientEmail(recipient.getEmail());
                            inputFields.setRecipientName(recipient.getName());
                            inputFields.setRecipientSurname(recipient.getSurname());
                            inputFields.setRecipientPatronymic(recipient.getPatronymic());
                            return inputFields;
                        }

                        if (s.equalsIgnoreCase("no")) {
                            chooseRecipient();
                            break;
                        } else {
                            log.info("Make the right choice !");
                            log.info("Type 'Yes' or 'No'");
                            scanner.nextLine();
                        }
                    }
                }
            }


        }

        return null;
    }

    public static void addRecipient(InputData inputField) throws SQLException {
        RecipientDao recipientDao = new RecipientDao();
        List<Recipient> recipientList = recipientDao.getAll();
        Recipient recipient = new Recipient();
        log.info("Do you want to add new recipient to the table ? yes/no");
        try (Scanner scanner = new Scanner(System.in)) {
            String s = scanner.nextLine();
            while (true) {
                if (s.equalsIgnoreCase("yes")) {
                    recipient.setId(recipientList.size() + 1);
                    recipient.setEmail(inputField.getRecipientEmail());
                    recipient.setName(inputField.getRecipientName());
                    recipient.setSurname(inputField.getRecipientSurname());
                    recipient.setPatronymic(inputField.getRecipientPatronymic());
                    recipientDao.add(recipient);
                    break;
                }
                if (s.equalsIgnoreCase("no")) {
                    break;
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

        Recipient recipientFromTable = InputData.getRecipientFromTable();
        Message message = prepareMessage(session, InputData.readFromConsole(recipientFromTable));
        Transport.send(message);
        log.info("Message sent successfully");
        if (recipientFromTable == null)
            InputData.addRecipient(InputData.readFromConsole(recipientFromTable));
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
                    InputData.chooseRecipient();
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
