package com.tony.bday.greeting.processor;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.tony.bday.greeting.model.Employee;

/**
 * The processor is used to get the employee whose birthday is today.
 * 
 * @author lei.du
 *
 */
@Component
public class GetTargetRecords implements Processor {
    private static final Log LOGGER = LogFactory.getLog(GetTargetRecords.class);

    @Override
    public void process(Exchange exchange) throws Exception {
        String payload = exchange.getIn().getBody(String.class);
        LOGGER.info("2-1 " + "Employee details are : " + payload);

        Employee employee = (Employee)exchange.getIn().getBody();
        Employee targetEmployee = null;
        if (isBirthdayToday(employee)) {
            LOGGER.info("2-2 " + employee.getName() + " is birthday today");
            targetEmployee = employee;
        } else {
            LOGGER.info("2-3 " + employee.getName() + " isn't birthday today");
        }
        exchange.getOut().setBody(targetEmployee, Employee.class);
    }

    /**
     * Check if today is employee's birthday
     * 
     * @param employee
     * @return
     * @throws ParseException
     */
    private boolean isBirthdayToday(Employee employee) throws ParseException {
        Calendar cal = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        Date birthday = df.parse(employee.getBirthday());
        cal.setTime(new Date());
        cal2.setTime(birthday);
        cal2.set(cal.get(Calendar.YEAR), cal2.get(Calendar.MONTH), cal2.get(Calendar.DAY_OF_MONTH));
        return DateUtils.isSameDay(cal, cal2);
    }
}
