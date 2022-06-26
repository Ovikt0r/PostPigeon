package com.oviktor.repository;

import com.oviktor.entity.Recipient;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


@Slf4j
public class RecipientDaoTest {

    RecipientDao recipientDao = new RecipientDao();

    @BeforeEach
    void setUp() throws SQLException, NullPointerException {
        Recipient recipient1 =
                Recipient.builder()
                        .id(1L)
                        .email("sdfdf@sdfds.com")
                        .name("Viktor")
                        .surname("Lol")
                        .patronymic("Kek")
                        .build();
        Recipient recipient2 =
                Recipient.builder()
                        .id(2L)
                        .email("oiut@brt.com")
                        .name("Yra")
                        .surname("Ret")
                        .patronymic("Cot")
                        .build();
        Recipient recipient3 =
                Recipient.builder()
                        .id(3L)
                        .email("twat@mail.com")
                        .name("Taras")
                        .surname("Peter")
                        .patronymic("Vertep")
                        .build();
        Recipient recipient4 =
                Recipient.builder()
                        .id(4L)
                        .email("jonson@google.com")
                        .name("Vasya")
                        .surname("Frolov")
                        .patronymic("Petrovich")
                        .build();
        Recipient recipient5 =
                Recipient.builder()
                        .id(5L)
                        .email("jooma@gamil.com")
                        .name("Eliut")
                        .surname("Kepchoge")
                        .patronymic("Valerians")
                        .build();
        recipientDao.add(recipient1);
        recipientDao.add(recipient2);
        recipientDao.add(recipient3);
        recipientDao.add(recipient4);
        recipientDao.add(recipient5);
        log.info("5 recipients were added in the table successes");
    }

    @Test
    public void add() throws SQLException {
        assertNull(recipientDao.getById(6L));

        Recipient expectedRecipient = Recipient.builder()
                .id(6L)
                .email("jogging@agmail.com")
                .name("Frida")
                .surname("Karlo")
                .patronymic("Stoner")
                .build();

        recipientDao.add(expectedRecipient);
        Recipient actualRecipient = recipientDao.getById(6L);
        assertEquals(expectedRecipient, actualRecipient);

    }

    @Test
    public void getAll() throws SQLException {

        int expectedRecipientsNumber = 5;
        int actualRecipientsNumber;

        List<Recipient> actualRecipientsList = recipientDao.getAll();
        actualRecipientsNumber = actualRecipientsList.size();
        assertEquals(expectedRecipientsNumber, actualRecipientsNumber);
    }

    @Test
    public void getById() throws SQLException {
        Recipient expectedRecipient1 =
                Recipient.builder()
                        .id(1L)
                        .email("sdfdf@sdfds.com")
                        .name("Viktor")
                        .surname("Lol")
                        .patronymic("Kek")
                        .build();
        Recipient expectedRecipient2 =
                Recipient.builder()
                        .id(2L)
                        .email("oiut@brt.com")
                        .name("Yra")
                        .surname("Ret")
                        .patronymic("Cot")
                        .build();
        Recipient expectedRecipient3 =
                Recipient.builder()
                        .id(3L)
                        .email("twat@mail.com")
                        .name("Taras")
                        .surname("Peter")
                        .patronymic("Vertep")
                        .build();
        Recipient expectedRecipient4 =
                Recipient.builder()
                        .id(4L)
                        .email("jonson@google.com")
                        .name("Vasya")
                        .surname("Frolov")
                        .patronymic("Petrovich")
                        .build();
        Recipient expectedRecipient5 =
                Recipient.builder()
                        .id(5L)
                        .email("jooma@gamil.com")
                        .name("Eliut")
                        .surname("Kepchoge")
                        .patronymic("Valerians")
                        .build();

        Recipient actualRecipient1 = new RecipientDao().getById(1L);
        Recipient actualRecipient2 = new RecipientDao().getById(2L);
        Recipient actualRecipient3 = new RecipientDao().getById(3L);
        Recipient actualRecipient4 = new RecipientDao().getById(4L);
        Recipient actualRecipient5 = new RecipientDao().getById(5L);


        assertEquals(expectedRecipient1, actualRecipient1);
        assertEquals(expectedRecipient2, actualRecipient2);
        assertEquals(expectedRecipient3, actualRecipient3);
        assertEquals(expectedRecipient4, actualRecipient4);
        assertEquals(expectedRecipient5, actualRecipient5);
    }

    @Test
    public void update() throws SQLException {
        Recipient expectedRecipient = Recipient.builder()
                .id(5L)
                .email("jooma@gamil.com")
                .name("Eliut")
                .surname("Kepchoge")
                .patronymic("Valerians")
                .build();

        Recipient actualRecipient = recipientDao.getById(5L);
        assertEquals(expectedRecipient, actualRecipient);

        expectedRecipient.setName("Oskar");
        recipientDao.update(expectedRecipient);
        actualRecipient = recipientDao.getById(5L);
        assertEquals(expectedRecipient, actualRecipient);
    }

    @Test
    public void removeById() throws SQLException {
        Recipient expectedRecipient = Recipient.builder()
                .id(5L)
                .email("jooma@gamil.com")
                .name("Eliut")
                .surname("Kepchoge")
                .patronymic("Valerians")
                .build();

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
        Recipient recipient6 = recipientDao.getById(6L);
        if (Objects.isNull(recipient1) &&
                Objects.isNull(recipient2) &&
                Objects.isNull(recipient3) &&
                Objects.isNull(recipient4) &&
                Objects.isNull(recipient5) &&
                Objects.isNull(recipient6)) {
            log.info("CleanUp is not required");
        } else {
            recipientDao.removeById(0L);
            recipientDao.removeById(1L);
            recipientDao.removeById(2L);
            recipientDao.removeById(3L);
            recipientDao.removeById(4L);
            recipientDao.removeById(5L);
            recipientDao.removeById(6L);
            log.info("CleanUp the table was done successful");
        }
    }
}