import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SendEmail {
    public static void main(String[] args) throws Exception{
        log.info("You should choose the recipient from the list by ID:");
        JavaMailUtil.sendEmail();

    }

}