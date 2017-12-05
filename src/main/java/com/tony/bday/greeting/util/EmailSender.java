package com.tony.bday.greeting.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

/**
 * 
 * @author lei.du
 *
 */
@Component
public class EmailSender {
    private static final Log LOGGER = LogFactory.getLog(EmailSender.class);

    public void sender(String emailAddress, String name) {
        String subject = "Happy birthday!";
        String body = "Happy birthday, dear " + name;
        LOGGER.info("4-1 " + "Subject : " + subject + " Body : " + body);
    }
}
