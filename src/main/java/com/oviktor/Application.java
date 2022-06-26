package com.oviktor;

import com.oviktor.utils.EmailSender;
import com.oviktor.utils.MessageData;
import lombok.extern.slf4j.Slf4j;

import javax.mail.MessagingException;
import java.sql.SQLException;
import java.util.NoSuchElementException;

@Slf4j
public class Application {

    private static final MessageData messageData = new MessageData();
    private static final int i = 0;

    private static final EmailSender emailSender = new EmailSender();

    public static void main(String[] args) throws NoSuchElementException, MessagingException, SQLException {
//        emailSender.sendEmail();
        new Application().kek(messageData, i);
        System.out.println("kek");
    }

    public void kek(MessageData messageData, int i) {
//        messageData = new MessageData();
        messageData.setText("");
        i = 1;
    }

}