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
import java.util.Objects;
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


    public static InputData readFromConsole() throws SQLException {

        InputData inputFields = new InputData();
        RecipientDao recipientDao = new RecipientDao();
        Recipient recipientFromTable;
        Recipient newRecipient;
        Recipient recipientId = null;
        List<Recipient> recipientDaoList;
        recipientDaoList = recipientDao.getAll();
        log.info(recipientDaoList.toString());
        try (Scanner scanner = new Scanner(System.in)) {


            recipientFromTable = recipientDao.getById(Long.valueOf(scanner.nextLine()));
            log.info("Do you want to sent a letter to : " + recipientFromTable.getEmail() + " ?");
            log.info("Y/N");
            String answer = scanner.nextLine();
            if (answer.equals("Y")) {
                inputFields.setRecipient(recipientFromTable.getEmail());
                while (!isValidEmailAddress(inputFields.getRecipient())) {
                    log.info("Type email in the right format");
                    inputFields.setRecipient(scanner.nextLine());
                }
            }
            else {
                log.info("Do you want to pick another one from the table? type 'another' ");
                log.info("Or do you want to send the letter to new recipient and add it to the table ? type 'new'");
                answer = scanner.nextLine();
                if (Objects.equals(answer, "another")) {
                    readFromConsole();
                }
                if (Objects.equals(answer, "new")) {
                    log.info("Enter recipient's email : ");
                    String newEmail = scanner.nextLine();
                    while (!isValidEmailAddress(newEmail)) {
                        log.info("Type email in the right format");
                        newEmail = scanner.nextLine();
                    }
                    log.info("Enter recipient's name : ");
                    String newName = scanner.nextLine();
                    log.info("Enter recipient's surname : ");
                    String newSurname = scanner.nextLine();
                    log.info("Enter recipient's patronymic : ");
                    String newPatronymic = scanner.nextLine();
                    recipientId = recipientDaoList.get(recipientDaoList.size()-1);
                    newRecipient = new Recipient.Builder()
                            .withId(recipientId.getId() + 1)
                            .withEmail(newEmail)
                            .withName(newName)
                            .withSurname(newSurname)
                            .withPatronymic(newPatronymic)
                            .build();
                    recipientDao.add(newRecipient);
                    recipientDaoList = recipientDao.getAll();
                    log.info(recipientDaoList.toString());
                    inputFields.setRecipient(newRecipient.getEmail());

                }
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