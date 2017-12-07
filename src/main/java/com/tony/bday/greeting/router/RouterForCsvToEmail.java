package com.tony.bday.greeting.router;

import org.apache.camel.LoggingLevel;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.apache.camel.spi.DataFormat;
import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.tony.bday.greeting.model.Employee;
import com.tony.bday.greeting.processor.GetTargetRecords;
import com.tony.bday.greeting.processor.SendGreetingMessage;

/**
 * 
 * 
 * @author lei.du
 *
 */
@Component
@PropertySource("classpath:application.properties")
public class RouterForCsvToEmail extends SpringRouteBuilder {
    @Value("${target.file.path}")
    private String filePath;

    @Value("${scheduler}")
    private String scheduler;

    @Override
    public void configure() throws Exception {
        DataFormat bindy = new BindyCsvDataFormat(Employee.class);
        getContext().setTypeConverterStatisticsEnabled(true);

        from(filePath + "?include=.*.csv&sendEmptyMessageWhenIdle=true&noop=true&" + scheduler).routeId("router-1")
        .split().tokenize("\n", 1)
        .log(LoggingLevel.INFO, "1-1", "==========[START] The body is : ${body}==========" )
        .unmarshal(bindy).id("unmarshal-1")
        .process(new GetTargetRecords()).id("processor-2")
        .choice().when(body().isNotNull())
            .log(LoggingLevel.INFO, "1-2", "Target employee details are ${body}")
            .process(new SendGreetingMessage()).id("processor-3")
//          .to("imap://host?username=user2&password=pass2")
        .endChoice()
        .end()
        .log(LoggingLevel.INFO, "1-3", "====================END====================");
    }
}