package repository;

import entity.Recipient;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;


@Slf4j
public class RecipientDaoTest {


    @Test
    public void add() throws SQLException {

        RecipientDao recipientDao = new RecipientDao();
        assertNull(recipientDao.getById(5L));

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

    }

    @Test
    public void getAll() throws SQLException {

        RecipientDao recipientDao = new RecipientDao();
        assertNull(recipientDao.getById(1L));
        assertNull(recipientDao.getById(2L));
        assertNull(recipientDao.getById(3L));
        assertNull(recipientDao.getById(4L));
        assertNull(recipientDao.getById(5L));

        Recipient recipient1 =
                new Recipient.Builder()
                        .withId(1)
                        .withEmail("sdfdf@sdfds.com")
                        .withName("Viktor")
                        .withSurname("Lol")
                        .withPatronymic("Kek")
                        .build();
        Recipient recipient2 =
                new Recipient.Builder()
                        .withId(2)
                        .withEmail("oiut@brt.com")
                        .withName("Yra")
                        .withSurname("Ret")
                        .withPatronymic("Cot")
                        .build();
        Recipient recipient3 =
                new Recipient.Builder()
                        .withId(3)
                        .withEmail("twat@mail.com")
                        .withName("Taras")
                        .withSurname("Peter")
                        .withPatronymic("Vertep")
                        .build();
        Recipient recipient4 =
                new Recipient.Builder()
                        .withId(4)
                        .withEmail("jonson@google.com")
                        .withName("Vasya")
                        .withSurname("Frolov")
                        .withPatronymic("Petrovich")
                        .build();
        Recipient recipient5 =
                new Recipient.Builder()
                        .withId(5)
                        .withEmail("jooma@gamil.com")
                        .withName("Eliut")
                        .withSurname("Kepchoge")
                        .withPatronymic("Valerians")
                        .build();

        recipientDao.add(recipient1);
        recipientDao.add(recipient2);
        recipientDao.add(recipient3);
        recipientDao.add(recipient4);
        recipientDao.add(recipient5);
        List<Recipient> expectedRecipients = Arrays.asList(
                new Recipient.Builder()
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
                        .build(),
                new Recipient.Builder()
                        .withId(5)
                        .withEmail("jooma@gamil.com")
                        .withName("Eliut")
                        .withSurname("Kepchoge")
                        .withPatronymic("Valerians")
                        .build());

        List<Recipient> actualRecipients = new RecipientDao().getAll();
        assertEquals(expectedRecipients, actualRecipients);
    }

    @Test
    public void getById() throws SQLException {

        RecipientDao recipientDao = new RecipientDao();
        assertNull(recipientDao.getById(1L));
        assertNull(recipientDao.getById(2L));
        assertNull(recipientDao.getById(3L));
        assertNull(recipientDao.getById(4L));
        assertNull(recipientDao.getById(5L));

        Recipient recipient1 =
                new Recipient.Builder()
                        .withId(1)
                        .withEmail("sdfdf@sdfds.com")
                        .withName("Viktor")
                        .withSurname("Lol")
                        .withPatronymic("Kek")
                        .build();
        Recipient recipient2 =
                new Recipient.Builder()
                        .withId(2)
                        .withEmail("oiut@brt.com")
                        .withName("Yra")
                        .withSurname("Ret")
                        .withPatronymic("Cot")
                        .build();
        Recipient recipient3 =
                new Recipient.Builder()
                        .withId(3)
                        .withEmail("twat@mail.com")
                        .withName("Taras")
                        .withSurname("Peter")
                        .withPatronymic("Vertep")
                        .build();
        Recipient recipient4 =
                new Recipient.Builder()
                        .withId(4)
                        .withEmail("jonson@google.com")
                        .withName("Vasya")
                        .withSurname("Frolov")
                        .withPatronymic("Petrovich")
                        .build();
        Recipient recipient5 =
                new Recipient.Builder()
                        .withId(5)
                        .withEmail("jooma@gamil.com")
                        .withName("Eliut")
                        .withSurname("Kepchoge")
                        .withPatronymic("Valerians")
                        .build();

        recipientDao.add(recipient1);
        recipientDao.add(recipient2);
        recipientDao.add(recipient3);
        recipientDao.add(recipient4);
        recipientDao.add(recipient5);

        Recipient actualRecipient1 = new RecipientDao().getById(1L);
        Recipient actualRecipient2 = new RecipientDao().getById(2L);
        Recipient actualRecipient3 = new RecipientDao().getById(3L);
        Recipient actualRecipient4 = new RecipientDao().getById(4L);
        Recipient actualRecipient5 = new RecipientDao().getById(5L);

        Recipient expectedRecipient1 =
                new Recipient.Builder()
                        .withId(1)
                        .withEmail("sdfdf@sdfds.com")
                        .withName("Viktor")
                        .withSurname("Lol")
                        .withPatronymic("Kek")
                        .build();
        Recipient expectedRecipient2 =
                new Recipient.Builder()
                        .withId(2)
                        .withEmail("oiut@brt.com")
                        .withName("Yra")
                        .withSurname("Ret")
                        .withPatronymic("Cot")
                        .build();
        Recipient expectedRecipient3 =
                new Recipient.Builder()
                        .withId(3)
                        .withEmail("twat@mail.com")
                        .withName("Taras")
                        .withSurname("Peter")
                        .withPatronymic("Vertep")
                        .build();
        Recipient expectedRecipient4 =
                new Recipient.Builder()
                        .withId(4)
                        .withEmail("jonson@google.com")
                        .withName("Vasya")
                        .withSurname("Frolov")
                        .withPatronymic("Petrovich")
                        .build();
        Recipient expectedRecipient5 =
                new Recipient.Builder()
                        .withId(5)
                        .withEmail("jooma@gamil.com")
                        .withName("Eliut")
                        .withSurname("Kepchoge")
                        .withPatronymic("Valerians")
                        .build();
        assertEquals(expectedRecipient1, actualRecipient1);
        assertEquals(expectedRecipient2, actualRecipient2);
        assertEquals(expectedRecipient3, actualRecipient3);
        assertEquals(expectedRecipient4, actualRecipient4);
        assertEquals(expectedRecipient5, actualRecipient5);
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
        assertEquals(expectedRecipient, actualRecipient);

        expectedRecipient.setName("Oskar");
        recipientDao.update(expectedRecipient);
        actualRecipient = recipientDao.getById(5L);
        assertEquals(expectedRecipient, actualRecipient);
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
        Recipient recipient1 = recipientDao.getById(1L);
        Recipient recipient2 = recipientDao.getById(2L);
        Recipient recipient3 = recipientDao.getById(3L);
        Recipient recipient4 = recipientDao.getById(4L);
        Recipient recipient5 = recipientDao.getById(5L);
        if (Objects.isNull(recipient1) &&
                Objects.isNull(recipient2) &&
                Objects.isNull(recipient3) &&
                Objects.isNull(recipient4) &&
                Objects.isNull(recipient5)) {
            log.info("CleanUp is not required");
        } else {
            recipientDao.removeById(1L);
            recipientDao.removeById(2L);
            recipientDao.removeById(3L);
            recipientDao.removeById(4L);
            recipientDao.removeById(5L);
            log.info("CleanUp the table was done successful");
        }

    }
}