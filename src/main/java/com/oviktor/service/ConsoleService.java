package com.oviktor.service;

import com.oviktor.utils.MessageData;
import com.oviktor.entity.Recipient;
import com.oviktor.utils.Choice;
import com.oviktor.utils.RegexHelper;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Scanner;

@Slf4j
public class ConsoleService {

    private final RecipientService recipientService = new RecipientService();

    public MessageData readMessageData() throws SQLException, NoSuchElementException {
        Recipient recipient = getRecipient();
        List<Recipient> recipientDaoList = recipientService.getAllRecipients();

        if (recipient.getId() > 0 && recipient.getId() < recipientDaoList.size()) {
            log.info("You're going to send a letter to " + recipient.getEmail());
            return prepareMessageData(recipient);
        }
        if (recipient.getId() == 0) {
            recipient.setId((long) (recipientDaoList.size() + 1));
            return prepareMessageData(recipient);
        }

        throw new RuntimeException();
    }

    private Recipient getRecipient() throws SQLException {
        log.info("Do you want to send the letter to recipient from the table or to a new recipient ? type 'table' or 'new'");
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                String response = scanner.nextLine();
                Choice choice = Choice.valueOf(response);
                switch (choice) {
                    case TABLE -> {
                        printTable();
                        return chooseRecipient(scanner);
                    }
                    case NEW -> {
                        Recipient newRecipient = readNewRecipientFromConsole(scanner);

                        saveRecipientIfNecessary(scanner, newRecipient);
                        return newRecipient;
                    }
                    default -> {
                        log.info("Try again! Enter 'table' or 'new'.");
                        scanner.nextLine();
                    }
                }
            }
        }
    }

    private void saveRecipientIfNecessary(Scanner scanner, Recipient newRecipient) throws SQLException {
        log.info("Do you want to add new recipient to the table ? yes/no");
        String s = scanner.nextLine();
        while (true) {
            if (s.equalsIgnoreCase("yes")) {
                recipientService.addRecipient(newRecipient);
            }
            if (s.equalsIgnoreCase("no")) {
                break;
            } else {
                log.info("Try again! Enter 'yes' or 'no'");
                scanner.nextLine();
            }
        }
    }

    private void printTable() throws SQLException {
        recipientService.getAllRecipients().forEach(System.out::println);
    }

    private Recipient chooseRecipient(Scanner scanner) throws SQLException {
        log.info("Enter ID: ");
        if (scanner.hasNextLong()) {
            long id = scanner.nextLong();

            List<Recipient> possibleRecipients = recipientService.getAllRecipients();
            boolean containsId = possibleRecipients.stream()
                    .anyMatch((recipient) -> Objects.equals(recipient.getId(), id));

            if (containsId) {
                return recipientService.getRecipientById(id);
            } else {
                log.info("ID is not valid! Try again");
                return chooseRecipient(scanner);
            }
        } else {
            log.info("This can't be interpreted as a long value. Please, enter another one.");
            return chooseRecipient(scanner);
        }
    }

    private MessageData prepareMessageData(Recipient recipient) {
        MessageData messageData = new MessageData();
        messageData.setRecipientEmail(recipient.getEmail());
        try (Scanner scanner = new Scanner(System.in)) {
            while (scanner.hasNextLine()) {
                log.info("Theme of the letter:");
                messageData.setTheme(scanner.nextLine());

                log.info("Main text of the letter:");
                messageData.setText(scanner.nextLine());
            }
        }
        return messageData;
    }

    public Recipient readNewRecipientFromConsole(Scanner scanner) {
        Recipient newRecipient = new Recipient();

        log.info("Enter recipient email: ");
        newRecipient.setEmail(scanner.nextLine());
        while (!RegexHelper.isValidEmailAddress(newRecipient.getEmail())) {
            log.info("Type email in the right format");
            newRecipient.setEmail(scanner.nextLine());
        }
        log.info("Input recipient's name :");
        newRecipient.setName(scanner.nextLine());
        while (!RegexHelper.isValidRecipientName(newRecipient.getName())) {
            log.info("Type recipient's name in the right format");
            newRecipient.setName(scanner.nextLine());
        }
        log.info("Input recipient's surname :");
        newRecipient.setSurname(scanner.nextLine());
        while (!RegexHelper.isValidRecipientName(newRecipient.getSurname())) {
            log.info("Type recipient's surname in the right format");
            newRecipient.setSurname(scanner.nextLine());
        }
        log.info("Input recipient's patronymic :");
        newRecipient.setPatronymic(scanner.nextLine());
        while (!RegexHelper.isValidRecipientName(newRecipient.getPatronymic())) {
            log.info("Type recipient's patronymic in the right format");
            newRecipient.setPatronymic(scanner.nextLine());
        }
        return newRecipient;
    }
}
