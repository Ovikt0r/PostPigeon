package com.oviktor.utils;

import com.oviktor.service.ConsoleService;
import lombok.extern.slf4j.Slf4j;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;


@Slf4j
public class EmailSender {

    static final String myAccountEmail = "post.dove.22@hotmail.com";
    static final String password = "Impostdove22";

    private static final Properties properties;
    private static final Authenticator authenticator;

    private final ConsoleService consoleService = new ConsoleService();

    static {
        properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp-mail.outlook.com");
        properties.put("mail.smtp.port", "587");

        authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccountEmail, password);
            }
        };
    }

    public void sendEmail() throws MessagingException, SQLException {
        Session session = Session.getInstance(properties, authenticator);
        MessageData messageData = consoleService.readMessageData();
        Message message = prepareMessage(session, messageData);
        Transport.send(message);
        log.info("The message to {} with the topic {} was sent successfully",
                messageData.getRecipientEmail(),
                messageData.getTheme());
        sendAgainOrQuit();
    }

    private Message prepareMessage(Session session, MessageData messageData) throws MessagingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(myAccountEmail));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(messageData.getRecipientEmail()));
        message.setSubject(messageData.getTheme());
        message.setText(messageData.getText());
        return message;
    }

    public void sendAgainOrQuit() throws SQLException {
        log.info("Do you want to send one more email?");
        try (Scanner scanner = new Scanner(System.in)) {
            String decision = scanner.nextLine();
            while (true) {
                if (decision.equalsIgnoreCase("yes")) {
                    sendEmail();
                    break;
                } else if (decision.equalsIgnoreCase("no")) {
                    break;
                } else {
                    log.info("Please type right word: 'yes' or 'no'");
                    scanner.nextLine();
                }
            }
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
