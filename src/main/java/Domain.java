
import entity.Recipient;
import lombok.extern.slf4j.Slf4j;
import repository.RecipientDao;

import java.sql.SQLException;


@Slf4j
public class Domain {
    public static void main(String[] args) throws SQLException {

        RecipientDao recipientDAO = new RecipientDao();
        Recipient recipient;

        recipient = recipientDAO.getById(5L);
        recipientDAO.remove(recipient);







    }
}
