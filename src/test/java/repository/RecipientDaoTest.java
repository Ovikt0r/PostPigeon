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

import static org.junit.jupiter.api.Assertions.*;


@Slf4j
public class RecipientDaoTest {


    @Test
    public void add() throws SQLException {

        assertNull(new RecipientDao().getById(5L));

        Recipient expectedRecipient = new Recipient.Builder()
                .withId(5)
                .withEmail("jooma@gamil.com")
                .withName("Eliut")
                .withSurname("Kepchoge")
                .withPatronymic("Valerians")
                .build();

        RecipientDao recipientDao = new RecipientDao();
        recipientDao.add(expectedRecipient);

        Recipient actualRecipient = recipientDao.getById(5L);
        assertEquals(expectedRecipient, actualRecipient);

    }

    @Test
    public void getAll() throws SQLException {

        List<Recipient> expectedRecipients = new ArrayList<>(Arrays.asList(new Recipient.Builder()
                        .withId(1)
                        .withEmail("sdfdf@sdfds.com")
                        .withName("Viktor")
                        .withSurname("Lol")
                        .withPatronymic("Kek")
                        .build(),
                new Recipient.Builder()
                        .withId(2)
                        .withEmail("oiut@brt.com")
                        .withName("Yra")
                        .withSurname("Ret")
                        .withPatronymic("Cot")
                        .build(),
                new Recipient.Builder()
                        .withId(3)
                        .withEmail("twat@mail.com")
                        .withName("Taras")
                        .withSurname("Peter")
                        .withPatronymic("Vertep")
                        .build(),
                new Recipient.Builder()
                        .withId(4)
                        .withEmail("jonson@google.com")
                        .withName("Vasya")
                        .withSurname("Frolov")
                        .withPatronymic("Petrovich")
                        .build()));

        List<Recipient> actualRecipients = new RecipientDao().getAll();
        assertEquals(expectedRecipients, actualRecipients);
    }

    @Test
    public void getById() throws SQLException {

        Recipient actualRecipient = new RecipientDao().getById(1L);
        Recipient expectedRecipient = new Recipient.Builder()
                .withId(1)
                .withEmail("sdfdf@sdfds.com")
                .withName("Viktor")
                .withSurname("Lol")
                .withPatronymic("Kek")
                .build();
        assertEquals(expectedRecipient,actualRecipient);
    }

    @Test
    public void update() throws SQLException {

        RecipientDao recipientDao = new RecipientDao();
        Recipient expectedRecipient = new Recipient.Builder()
                .withId(5)
                .withEmail("jooma@gamil.com")
                .withName("Eliut")
                .withSurname("Kepchoge")
                .withPatronymic("Valerians")
                .build();

        recipientDao.add(expectedRecipient);
        Recipient actualRecipient = recipientDao.getById(5L);
        assertEquals(expectedRecipient,actualRecipient);

        expectedRecipient.setName("Oskar");
        recipientDao.update(expectedRecipient);
        actualRecipient = recipientDao.getById(5L);
        assertEquals(expectedRecipient,actualRecipient);
    }

    @Test
    public void removeById() throws SQLException {

        RecipientDao recipientDao = new RecipientDao();
        Recipient expectedRecipient = new Recipient.Builder()
                .withId(5)
                .withEmail("jooma@gamil.com")
                .withName("Eliut")
                .withSurname("Kepchoge")
                .withPatronymic("Valerians")
                .build();

        recipientDao.add(expectedRecipient);
        Recipient actualRecipient = recipientDao.getById(5L);
        assertEquals(expectedRecipient, actualRecipient);

        recipientDao.removeById(5L);
        assertNull(recipientDao.getById(5L));
    }

    @AfterEach
    void cleanUp() throws SQLException, NullPointerException {

        RecipientDao recipientDao = new RecipientDao();
        Recipient recipient = recipientDao.getById(5L);
        if (Objects.isNull(recipient)) {
            log.info("CleanUp is not required");
        } else {
            recipientDao.removeById(5L);
            log.info("CleanUp 5th id was done successful");
        }

    }
}