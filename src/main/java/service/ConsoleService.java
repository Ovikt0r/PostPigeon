package service;

import entity.Recipient;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import repository.RecipientDao;
import static service.RecipientService.newRecipient;



import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

@Slf4j
@Getter
@Setter
public class ConsoleService {

    private String recipientEmail;
    private String theme;
    private String text;
    static ConsoleService inputFields = new ConsoleService();



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

    public static ConsoleService readFromConsole(Recipient recipient ) throws SQLException, NoSuchElementException {
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

    public static ConsoleService writeEmail() {

        try (Scanner scanner1 = new Scanner(System.in)) {

            log.info("Theme of the letter:");
            if(scanner1.hasNextLine()) {
                String theme = scanner1.nextLine();
                inputFields.setTheme(theme);
            }
            log.info("Main text of the letter:");
            inputFields.setText(scanner1.nextLine());

        }
        return inputFields;
    }

    public static void sendAgain() throws SQLException {
        log.info("Do you want to send one more email ?");
        try (Scanner scanner = new Scanner(System.in)) {
            String s = scanner.nextLine();
            while (true) {
                if (s.equalsIgnoreCase("yes")) {
                    ConsoleService.makeChoose();
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
