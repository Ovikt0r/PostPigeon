package service;

import entity.Recipient;
import lombok.extern.slf4j.Slf4j;
import repository.RecipientDao;

import java.sql.SQLException;
import java.util.Scanner;

import static util.RegexHelper.isValidEmailAddress;
import static util.RegexHelper.isValidRecipientName;
@Slf4j
public class RecipientService {

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
}
