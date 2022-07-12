package util;


import lombok.extern.slf4j.Slf4j;
import service.ConsoleService;
import service.RecipientService;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.sql.SQLException;
import java.util.Properties;


import static service.ConsoleService.sendAgain;


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


        ConsoleService inputFields = ConsoleService.readFromConsole(ConsoleService.makeChoose());
        Message message = prepareMessage(session, inputFields);
        Transport.send(message);
        log.info("Message sent successfully");
        RecipientService.addRecipient(RecipientService.newRecipient());
        sendAgain();

    }

    private static Message prepareMessage(Session session, ConsoleService inputFields) throws MessagingException {

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(myAccountEmail));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(inputFields.getRecipientEmail()));
        message.setSubject(inputFields.getTheme());
        message.setText(inputFields.getText());
        return message;

    }
}