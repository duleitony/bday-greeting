package com.tony.bday.greeting.unitTest;

import com.tony.bday.greeting.model.Employee;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import com.tony.bday.greeting.router.RouterForCsvToEmail;
import org.apache.camel.CamelContext;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.apache.camel.spi.DataFormat;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.camel.test.spring.UseAdviceWith;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(CamelSpringBootRunner.class)
@UseAdviceWith
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@SpringBootTest(classes = RouterForCsvToEmail.class)
public class UmshallTest {
    @Produce(uri = "direct:a")
    private ProducerTemplate producerTemplate;
    
    @Autowired
    private CamelContext camelContext;

    DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
    String today = df.format(new Date());

    @Test
    public void simpleRecordUnmarshal()throws Exception  {
        String msg = "Anthony, Zhao," + today + ", anthony.zhao@foobar.com";

        camelContext.getRouteDefinition("router-1").adviceWith(camelContext, new AdviceWithRouteBuilder() {
            @Override
            public void configure() throws Exception {
                replaceFromWith("direct:a");
                weaveById("processor-2").before().to("mock:result");
            }
        });
        camelContext.start();
        MockEndpoint mock = camelContext.getEndpoint("mock:result", MockEndpoint.class);
        mock.expectedBodiesReceived("Employee [Name=Anthony, SurName= Zhao, Birthday="+ today +", Email= anthony.zhao@foobar.com]");
        mock.expectedMessageCount(1);

        producerTemplate.sendBody("direct:a", msg);

        for(int i=0; i<mock.getReceivedExchanges().size();i++) {
            assert(mock.getReceivedExchanges().get(i).getIn().getBody() instanceof Employee);
        }
        mock.assertIsSatisfied();
    }

    @Test
    public void mutipleRecordsUnmarshal()throws Exception  {
        String msg = "Lei, Du, 1975/11/30, lei.du@foobar.com" + "\n" + "Anthony, Zhao," + today + ", anthony.zhao@foobar.com"
                + "\n" + "Lei, Du, 1975/11/30, lei.du@foobar.com";

        camelContext.getRouteDefinition("router-1").adviceWith(camelContext, new AdviceWithRouteBuilder() {
            @Override
            public void configure() throws Exception {
                replaceFromWith("direct:a");
                weaveById("processor-2").before().to("mock:mutipleRecordsUnmarshal");
            }
        });
        camelContext.start();
        MockEndpoint mock = camelContext.getEndpoint("mock:mutipleRecordsUnmarshal", MockEndpoint.class);
        mock.expectedMessageCount(3);

        producerTemplate.sendBody("direct:a", msg);

        for(int i=0; i<mock.getReceivedExchanges().size();i++) {
            assert(mock.getReceivedExchanges().get(i).getIn().getBody() instanceof Employee);
        }
        mock.assertIsSatisfied();
    }

    @Test
    public void isNotStringObject()throws Exception  {
        String msg = "Lei, Du, 1975/11/30, lei.du@foobar.com";

        camelContext.getRouteDefinition("router-1").adviceWith(camelContext, new AdviceWithRouteBuilder() {
            @Override
            public void configure() throws Exception {
                replaceFromWith("direct:a");
                weaveById("unmarshal-1").after().to("mock:isNotStringObject");
            }
        });
        camelContext.start();
        MockEndpoint mock = camelContext.getEndpoint("mock:isNotStringObject", MockEndpoint.class);
        mock.expectedMessageCount(1);

        producerTemplate.sendBody("direct:a", msg);

        for(int i=0; i<mock.getReceivedExchanges().size();i++) {
            assertFalse(mock.getReceivedExchanges().get(i).getIn().getBody() instanceof String);
        }
        mock.assertIsSatisfied();
    }

    @After
    public void finalize() throws Exception {
        camelContext.stop();
    }
}
