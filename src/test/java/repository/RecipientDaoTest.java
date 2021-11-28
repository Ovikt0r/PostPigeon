package repository;

import entity.Recipient;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@Slf4j
public class RecipientDaoTest {

    Recipient recipient;
    Recipient recipient2;
    RecipientDao recipientDAO = new RecipientDao();
    RecipientDao recipientDAO2 = new RecipientDao();
    Recipient expectedRecipient;
    Recipient actualRecipient;
    List<Recipient> actualRecipients;
    List<Recipient> expectedRecipients = new ArrayList<>();

    @Test
    public void add() throws SQLException {

        recipient2 = new Recipient();
        Recipient nullRecipient = recipientDAO.getById(5L);
        assertNull(nullRecipient);

        recipient = new Recipient();
        recipient.setId(5);
        recipient.setEmail("jooma@gamil.com");
        recipient.setName("Eliut");
        recipient.setSurname("Kepchoge");
        recipient.setPatronymic("Valerians");
        recipientDAO.add(recipient);

        expectedRecipient = recipient;
        actualRecipient = recipientDAO.getById(5L);
        assertThat(expectedRecipient).usingRecursiveComparison().isEqualTo(actualRecipient);

    }

    @Test
    public void getAll() throws SQLException {

        recipient = new Recipient();
        recipient.setId(1);
        recipient.setEmail("sdfdf@sdfds.com");
        recipient.setName("Viktor");
        recipient.setSurname("Lol");
        recipient.setPatronymic("Kek");

        Recipient recipient2 = new Recipient();
        recipient2.setId(2);
        recipient2.setEmail("oiut@brt.com");
        recipient2.setName("Yra");
        recipient2.setSurname("Ret");
        recipient2.setPatronymic("Cot");

        Recipient recipient3 = new Recipient();
        recipient3.setId(3);
        recipient3.setEmail("twat@mail.com");
        recipient3.setName("Taras");
        recipient3.setSurname("Peter");
        recipient3.setPatronymic("Vertep");

        Recipient recipient4 = new Recipient();
        recipient4.setId(4);
        recipient4.setEmail("jonson@google.com");
        recipient4.setName("Vasya");
        recipient4.setSurname("Frolov");
        recipient4.setPatronymic("Petrovich");

        expectedRecipients = new ArrayList<>(Arrays.asList(recipient, recipient2, recipient3, recipient4));
        actualRecipients = recipientDAO2.getAll();

        assertThat(expectedRecipients).usingRecursiveComparison().isEqualTo(actualRecipients);
    }

    @Test
    public void getById() throws SQLException {

        actualRecipient = recipientDAO.getById(1L);
        expectedRecipient = new Recipient();
        expectedRecipient.setId(1);
        expectedRecipient.setEmail("sdfdf@sdfds.com");
        expectedRecipient.setName("Viktor");
        expectedRecipient.setSurname("Lol");
        expectedRecipient.setPatronymic("Kek");
        assertThat(expectedRecipient).usingRecursiveComparison().isEqualTo(actualRecipient);

    }

    @Test
    public void update() throws SQLException {
        recipient = new Recipient();
        recipient.setId(5);
        recipient.setEmail("jooma@gamil.com");
        recipient.setName("Eliut");
        recipient.setSurname("Kepchoge");
        recipient.setPatronymic("Valerians");

        expectedRecipient = recipient;
        recipientDAO.add(recipient);
        actualRecipient = recipientDAO.getById(5L);
        assertThat(expectedRecipient).usingRecursiveComparison().isEqualTo(actualRecipient);

        recipient.setName("Oskar");
        recipientDAO.update(recipient);
        actualRecipient = recipientDAO.getById(5L);
        assertThat(expectedRecipient).usingRecursiveComparison().isEqualTo(actualRecipient);
    }

    @Test
    public void removeById() throws SQLException {

        recipient = new Recipient();
        recipient.setId(5);
        recipient.setEmail("jooma@gamil.com");
        recipient.setName("Eliut");
        recipient.setSurname("Kepchoge");
        recipient.setPatronymic("Valerians");

        recipientDAO.add(recipient);
        recipient = recipientDAO.getById(5L);

        assertEquals(5, recipient.getId());
        assertEquals("jooma@gamil.com", recipient.getEmail());
        assertEquals("Eliut", recipient.getName());
        assertEquals("Kepchoge", recipient.getSurname());
        assertEquals("Valerians", recipient.getPatronymic());

        recipientDAO.removeById(5L);
        assertNull(recipientDAO.getById(5L));
    }

    @AfterEach
    void cleanUp() throws SQLException, NullPointerException {

        recipient = recipientDAO.getById(5L);
        if (Objects.isNull(recipient)) {
            log.info("CleanUp is not required");
        } else {
            recipientDAO.removeById(5L);
            log.info("CleanUp 5th id was done successful");
        }
    }

}
