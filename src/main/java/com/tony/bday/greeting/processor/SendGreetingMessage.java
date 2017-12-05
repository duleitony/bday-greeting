package com.tony.bday.greeting.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.tony.bday.greeting.model.Employee;
import com.tony.bday.greeting.util.EmailSender;

/**
 * The SendGreetingMessage processor is used to send a greeting message via email.
 * 
 * @author lei.du
 *
 */
@Component
public class SendGreetingMessage implements Processor{
    private static final Log LOGGER = LogFactory.getLog(SendGreetingMessage.class);

    EmailSender emailSender = new EmailSender();

    @Override
    public void process(Exchange exchange) throws Exception {
        String payload = exchange.getIn().getBody(String.class);
        LOGGER.info("3-1 " + "Send greeting message to : " + payload);

        Employee employee = (Employee)exchange.getIn().getBody();
        emailSender.sender(employee.getEmail(), employee.getName());
        exchange.getOut().setBody(employee.toString());
    };
}
