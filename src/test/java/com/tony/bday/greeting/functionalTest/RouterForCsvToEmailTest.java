package com.tony.bday.greeting.functionalTest;

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
import com.tony.bday.greeting.router.RouterForCsvToEmail;

import static org.junit.Assert.assertNotNull;

@RunWith(CamelSpringBootRunner.class)
@UseAdviceWith
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@SpringBootTest(classes = RouterForCsvToEmail.class)
public class RouterForCsvToEmailTest {
    @Produce(uri = "direct:a")
    private ProducerTemplate producerTemplate;
    @Autowired
    private CamelContext camelContext;

    @Test
    public void someBodyIsBirthday()throws Exception  {
        String msg = "Tony, Li, 1975/12/05, lei.du@foobar.com";
        camelContext.getRouteDefinitions().get(0).adviceWith(camelContext, new AdviceWithRouteBuilder() {
            @Override
            public void configure() throws Exception {
                replaceFromWith("direct:a");
                weaveAddLast().to("mock:someBodyIsBirthday");
            }
        });
        // manual start camel
        camelContext.start();
        MockEndpoint mock = camelContext.getEndpoint("mock:someBodyIsBirthday", MockEndpoint.class);
        mock.expectedBodiesReceived(msg);
        mock.expectedMessageCount(1);
        assertNotNull(mock.getReceivedExchanges());
        producerTemplate.sendBody("direct:a", msg);
        mock.assertIsSatisfied();
    }

    @Test
    public void nobodyIsBirthday() throws Exception {
        camelContext.getRouteDefinitions().get(0).adviceWith(camelContext, new AdviceWithRouteBuilder() {
            @Override
            public void configure() throws Exception {
                replaceFromWith("direct:a");
                weaveAddLast().to("mock:resultWithoutTarget");
            }
        });
        camelContext.start();
        MockEndpoint mock = camelContext.getEndpoint("mock:resultWithoutTarget", MockEndpoint.class);
        String msg = "Lei, Du, 1975/11/30, lei.du@foobar.com";
        mock.expectedBodiesReceived(msg);
        mock.expectedMessageCount(1);
        assertNotNull(mock.getReceivedExchanges());
        producerTemplate.sendBody("direct:a", msg);
        mock.assertIsSatisfied();
    }

    @Test
    public void mutlipleRecords() throws Exception {
        camelContext.getRouteDefinitions().get(0).adviceWith(camelContext, new AdviceWithRouteBuilder() {
            @Override
            public void configure() throws Exception {
                replaceFromWith("direct:a");
                weaveAddLast().to("mock:mutlipleRecords");
            }
        });
        camelContext.start();
        MockEndpoint mock = camelContext.getEndpoint("mock:mutlipleRecords", MockEndpoint.class);
        String msg = "Jun, Wang, 1975/11/30, jun.wang@foobar.com" + "\n" + "Suke, Zhao, 1975/12/05, suke.zhao@foobar.com";
        mock.expectedBodiesReceived(msg);
        mock.expectedMessageCount(1);
        assertNotNull(mock.getReceivedExchanges());
        producerTemplate.sendBody("direct:a", msg);
        mock.assertIsSatisfied();
    }

    @Test(expected = RuntimeException.class)
    public void noEmail() throws Exception {
        camelContext.getRouteDefinitions().get(0).adviceWith(camelContext, new AdviceWithRouteBuilder() {
            @Override
            public void configure() throws Exception {
                replaceFromWith("direct:a");
                weaveAddLast().to("mock:noEmail");
            }
        });
        camelContext.start();
        MockEndpoint mock = camelContext.getEndpoint("mock:noEmail", MockEndpoint.class);
        String msg = "Jun, Wang, 1975/11/30, jun.wang@foobar.com" + "\n" + "Suke, Zhao, 1975/12/05";
        mock.expectedBodiesReceived(msg);
        mock.expectedMessageCount(1);
        assertNotNull(mock.getReceivedExchanges());
        producerTemplate.sendBody("direct:a", msg);
        mock.assertIsSatisfied();
    }

    @Test
    public void noRecords() throws Exception {
        camelContext.getRouteDefinitions().get(0).adviceWith(camelContext, new AdviceWithRouteBuilder() {
            @Override
            public void configure() throws Exception {
                replaceFromWith("direct:a");
                weaveAddLast().to("mock:noRecords");
            }
        });
        camelContext.start();
        MockEndpoint mock = camelContext.getEndpoint("mock:noRecords", MockEndpoint.class);
        String msg = "";
        mock.expectedBodiesReceived(msg);
        mock.expectedMessageCount(1);
        assertNotNull(mock.getReceivedExchanges());
        producerTemplate.sendBody("direct:a", msg);
        mock.assertIsSatisfied();
    }

    @After
    public void finalize() throws Exception {
        camelContext.stop();
    }
}