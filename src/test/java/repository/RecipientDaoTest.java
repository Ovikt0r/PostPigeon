package repository;

import entity.Recipient;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@Slf4j
public class RecipientDaoTest {


    @Test
    public void add() throws SQLException {
        RecipientDao recipientDAO = new RecipientDao();
        Recipient recipient1;
        Recipient recipient2 = new Recipient();
        recipient1 = recipientDAO.getById(5L);
        assertNull(recipient1);


        recipient2.setId(5);
        recipient2.setEmail("jooma@gamil.com");
        recipient2.setName("Eliut");
        recipient2.setSurname("Kepchoge");
        recipient2.setPatronymic("Valerians");
        recipientDAO.add(recipient2);


        assertEquals(5, recipient2.getId());
        assertEquals("jooma@gamil.com", recipient2.getEmail());
        assertEquals("Eliut", recipient2.getName());
        assertEquals("Kepchoge", recipient2.getSurname());
        assertEquals("Valerians", recipient2.getPatronymic());

    }


    @Test
    public void getAll() throws SQLException {

        RecipientDao recipientDao2 = new RecipientDao();
        List<Recipient> recipientList = new ArrayList<>();

        Recipient recipient1 = new Recipient();
        recipient1.setId(1);
        recipient1.setEmail("sdfdf@sdfds.com");
        recipient1.setName("Viktor");
        recipient1.setSurname("Lol");
        recipient1.setPatronymic("Kek");
        recipientList.add(recipient1);


        Recipient recipient2 = new Recipient();
        recipient2.setId(2);
        recipient2.setEmail("oiut@brt.com");
        recipient2.setName("Yra");
        recipient2.setSurname("Ret");
        recipient2.setPatronymic("Cot");
        recipientList.add(recipient2);

        Recipient recipient3 = new Recipient();
        recipient3.setId(3);
        recipient3.setEmail("twat@mail.com");
        recipient3.setName("Taras");
        recipient3.setSurname("Peter");
        recipient3.setPatronymic("Vertep");
        recipientList.add(recipient3);


        Recipient recipient4 = new Recipient();
        recipient4.setId(4);
        recipient4.setEmail("jonson@google.com");
        recipient4.setName("Vasya");
        recipient4.setSurname("Frolov");
        recipient4.setPatronymic("Petrovich");
        recipientList.add(recipient4);

        List<Recipient> testRecipientList = recipientDao2.getAll();

       assertThat(recipientList).usingRecursiveComparison().isEqualTo(testRecipientList);
    }

    @Test
    public void getById() throws SQLException {
        RecipientDao recipientDAO = new RecipientDao();
        Recipient recipient;

        recipient = recipientDAO.getById(1L);

        assertEquals(1, recipient.getId());
        assertEquals("sdfdf@sdfds.com", recipient.getEmail());
        assertEquals("Viktor", recipient.getName());
        assertEquals("Lol", recipient.getSurname());
        assertEquals("Kek", recipient.getPatronymic());

    }

    @Test
    public void update() throws SQLException {



            RecipientDao recipientDAO = new RecipientDao();
            Recipient recipient1;
            Recipient recipient2 = new Recipient();
            Recipient recipient3;

            recipient2.setId(5);
            recipient2.setEmail("jooma@gamil.com");
            recipient2.setName("Eliut");
            recipient2.setSurname("Kepchoge");
            recipient2.setPatronymic("Valerians");
            recipientDAO.add(recipient2);

            recipient1 = recipient2;
            assertEquals("Eliut", recipient1.getName());

            recipient2.setName("Oskar");
            recipientDAO.update(recipient2);
            recipient3 = recipientDAO.getById(5L);

            assertEquals("Oskar", recipient3.getName());
        }

    @Test
    public void remove() throws SQLException {

        RecipientDao recipientDAO = new RecipientDao();
        Recipient recipient1 = new Recipient();
        Recipient recipient2;

        recipient1.setId(5);
        recipient1.setEmail("jooma@gamil.com");
        recipient1.setName("Eliut");
        recipient1.setSurname("Kepchoge");
        recipient1.setPatronymic("Valerians");

        recipientDAO.add(recipient1);

        recipient2 = recipientDAO.getById(5L);

        assertEquals(5, recipient2.getId());
        assertEquals("jooma@gamil.com", recipient2.getEmail());
        assertEquals("Eliut", recipient2.getName());
        assertEquals("Kepchoge", recipient2.getSurname());
        assertEquals("Valerians", recipient2.getPatronymic());


        recipientDAO.remove(recipient2);

        assertNull(recipientDAO.getById(6L));
    }

    @AfterEach
    void cleanUp() throws SQLException, NullPointerException {
        RecipientDao recipientDAO = new RecipientDao();
        Recipient recipient = recipientDAO.getById(5L);
        if (Objects.isNull(recipient)) {
            System.out.println("CleanUp is not required");
        } else {
            recipientDAO.remove(recipient);
            System.out.println("CleanUp 5th id was done successful");
        }
    }

}
