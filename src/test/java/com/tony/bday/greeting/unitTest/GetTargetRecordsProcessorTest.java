package com.tony.bday.greeting.unitTest;

import static org.junit.Assert.assertNotNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.camel.CamelContext;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.camel.test.spring.UseAdviceWith;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import com.tony.bday.greeting.processor.GetTargetRecords;
import com.tony.bday.greeting.router.RouterForCsvToEmail;

@RunWith(CamelSpringBootRunner.class)
@UseAdviceWith
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@SpringBootTest(classes = { RouterForCsvToEmail.class, GetTargetRecords.class })
public class GetTargetRecordsProcessorTest {
    @Produce(uri = "direct:a")
    private ProducerTemplate producerTemplate;
    
    @Autowired
    private CamelContext camelContext;

    DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
    String today = df.format(new Date());

    @Test
    public void someBodyIsBirthdayWithoutSendingEmail()throws Exception  {
        String msg = "Anthony, Zhao," + today + ", anthony.zhao@foobar.com";

        camelContext.getRouteDefinition("router-1").adviceWith(camelContext, new AdviceWithRouteBuilder() {
            @Override
            public void configure() throws Exception {
                replaceFromWith("direct:a");
                weaveById("processor-3").before().to("mock:result");
            }
        });
        camelContext.start();
        MockEndpoint mock = camelContext.getEndpoint("mock:result", MockEndpoint.class);
        mock.expectedBodiesReceived("Employee [Name=Anthony, SurName= Zhao, Birthday="+ today +", Email= anthony.zhao@foobar.com]");
        mock.expectedMessageCount(1);
        assertNotNull(mock.getReceivedExchanges());
        producerTemplate.sendBody("direct:a", msg);
        mock.assertIsSatisfied();
    }

    @Test
    public void noBodyIsBirthdayWithSingleRecord()throws Exception  {
        String msg = "Lei, Du, 1975/11/30, lei.du@foobar.com";

        camelContext.getRouteDefinition("router-1").adviceWith(camelContext, new AdviceWithRouteBuilder() {
            @Override
            public void configure() throws Exception {
               replaceFromWith("direct:a");
//                weaveAddLast().to("mock:result");
                weaveById("processor-3").before().to("mock:result");
            }
        });
        camelContext.start();
        MockEndpoint mock = camelContext.getEndpoint("mock:result", MockEndpoint.class);
        mock.expectedMessageCount(0);
        producerTemplate.sendBody("direct:a", msg);
        mock.assertIsSatisfied();
    }

    @After
    public void finalize() throws Exception {
        camelContext.stop();
    }
}
