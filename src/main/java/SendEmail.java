import lombok.extern.slf4j.Slf4j;
import util.JavaMailUtil;

import javax.mail.MessagingException;
import java.sql.SQLException;
import java.util.NoSuchElementException;

@Slf4j
public class SendEmail {
    public static void main(String[] args) throws NoSuchElementException, MessagingException, SQLException {
        JavaMailUtil.sendEmail();

    }

}