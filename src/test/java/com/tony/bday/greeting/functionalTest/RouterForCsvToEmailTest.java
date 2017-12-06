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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(CamelSpringBootRunner.class)
@UseAdviceWith
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@SpringBootTest(classes = RouterForCsvToEmail.class)
public class RouterForCsvToEmailTest {
    @Produce(uri = "direct:a")
    private ProducerTemplate producerTemplate;
    @Autowired
    private CamelContext camelContext;

    DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
    String today = df.format(new Date());

    @Test
    public void someBodyIsBirthday()throws Exception  {
        String msg = "Tony, Li," + today + ", lei.du@foobar.com";
        camelContext.getRouteDefinition("router-1").adviceWith(camelContext, new AdviceWithRouteBuilder() {
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
    public void morePeopleAreBirthday()throws Exception  {
        String msg = "Tony, Li," + today + ", lei.du@foobar.com" + "\n" + "Mei, Han, " + today + ", mei.han@foobar.com";
        camelContext.getRouteDefinition("router-1").adviceWith(camelContext, new AdviceWithRouteBuilder() {
            @Override
            public void configure() throws Exception {
                replaceFromWith("direct:a");
                weaveAddLast().to("mock:morePeopleAreBirthday");
            }
        });
        // manual start camel
        camelContext.start();
        MockEndpoint mock = camelContext.getEndpoint("mock:morePeopleAreBirthday", MockEndpoint.class);
        mock.expectedBodiesReceived(msg);
        mock.expectedMessageCount(1);
        assertNotNull(mock.getReceivedExchanges());
        producerTemplate.sendBody("direct:a", msg);
        mock.assertIsSatisfied();
    }

    @Test
    public void nobodyIsBirthday() throws Exception {
        camelContext.getRouteDefinition("router-1").adviceWith(camelContext, new AdviceWithRouteBuilder() {
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
        camelContext.getRouteDefinition("router-1").adviceWith(camelContext, new AdviceWithRouteBuilder() {
            @Override
            public void configure() throws Exception {
                replaceFromWith("direct:a");
                weaveAddLast().to("mock:mutlipleRecords");
            }
        });
        camelContext.start();
        MockEndpoint mock = camelContext.getEndpoint("mock:mutlipleRecords", MockEndpoint.class);
        String msg = "Jun, Wang, 1975/11/30, jun.wang@foobar.com" + "\n" + "Suke, Zhao," + today + ", suke.zhao@foobar.com";
        mock.expectedBodiesReceived(msg);
        mock.expectedMessageCount(1);
        assertNotNull(mock.getReceivedExchanges());
        producerTemplate.sendBody("direct:a", msg);
        mock.assertIsSatisfied();
    }

    @Test(expected = RuntimeException.class)
    public void noEmail() throws Exception {
        camelContext.getRouteDefinition("router-1").adviceWith(camelContext, new AdviceWithRouteBuilder() {
            @Override
            public void configure() throws Exception {
                replaceFromWith("direct:a");
                weaveAddLast().to("mock:noEmail");
            }
        });
        camelContext.start();
        MockEndpoint mock = camelContext.getEndpoint("mock:noEmail", MockEndpoint.class);
        String msg = "Suke, Zhao, " + today + ",";
        mock.expectedBodiesReceived(msg);
        mock.expectedMessageCount(1);
        assertNotNull(mock.getReceivedExchanges());
        producerTemplate.sendBody("direct:a", msg);
        mock.assertIsSatisfied();
    }

    @Test(expected = RuntimeException.class)
    public void noBirthday() throws Exception {
        camelContext.getRouteDefinition("router-1").adviceWith(camelContext, new AdviceWithRouteBuilder() {
            @Override
            public void configure() throws Exception {
                replaceFromWith("direct:a");
                weaveAddLast().to("mock:noBirthday");
            }
        });
        camelContext.start();
        MockEndpoint mock = camelContext.getEndpoint("mock:noBirthday", MockEndpoint.class);
        String msg = "Jun, Want,,jun.wang@foobar.com";
        mock.expectedBodiesReceived(msg);
        mock.expectedMessageCount(1);
        assertNotNull(mock.getReceivedExchanges());
        producerTemplate.sendBody("direct:a", msg);
        mock.assertIsSatisfied();
    }

    @Test(expected = RuntimeException.class)
    public void noName() throws Exception {
        camelContext.getRouteDefinition("router-1").adviceWith(camelContext, new AdviceWithRouteBuilder() {
            @Override
            public void configure() throws Exception {
                replaceFromWith("direct:a");
                weaveAddLast().to("mock:noName");
            }
        });
        camelContext.start();
        MockEndpoint mock = camelContext.getEndpoint("mock:noName", MockEndpoint.class);
        String msg = ",Jun, 1975/11/30, jun.wang@foobar.com";
        mock.expectedBodiesReceived(msg);
        mock.expectedMessageCount(1);
        assertNotNull(mock.getReceivedExchanges());
        producerTemplate.sendBody("direct:a", msg);
        mock.assertIsSatisfied();
    }

    @Test
    public void noSurName() throws Exception {
        camelContext.getRouteDefinition("router-1").adviceWith(camelContext, new AdviceWithRouteBuilder() {
            @Override
            public void configure() throws Exception {
                replaceFromWith("direct:a");
                weaveAddLast().to("mock:noSurName");
            }
        });
        camelContext.start();
        MockEndpoint mock = camelContext.getEndpoint("mock:noSurName", MockEndpoint.class);
        String msg = "Jun,," + today + ",jun.wang@foobar.com";
        mock.expectedBodiesReceived(msg);
        mock.expectedMessageCount(1);
        assertNotNull(mock.getReceivedExchanges());
        producerTemplate.sendBody("direct:a", msg);
        mock.assertIsSatisfied();
    }

    @Test(expected = RuntimeException.class)
    public void noAllFields() throws Exception {
        camelContext.getRouteDefinition("router-1").adviceWith(camelContext, new AdviceWithRouteBuilder() {
            @Override
            public void configure() throws Exception {
                replaceFromWith("direct:a");
                weaveAddLast().to("mock:noAllFields");
            }
        });
        camelContext.start();
        MockEndpoint mock = camelContext.getEndpoint("mock:noAllFields", MockEndpoint.class);
        String msg = ",,,";
        mock.expectedBodiesReceived(msg);
        mock.expectedMessageCount(1);
        assertNotNull(mock.getReceivedExchanges());
        producerTemplate.sendBody("direct:a", msg);
        mock.assertIsSatisfied();
    }

    @Test
    public void emptyFile() throws Exception {
         camelContext.getRouteDefinition("router-1").adviceWith(camelContext, new AdviceWithRouteBuilder() {
            @Override
            public void configure() throws Exception {
                replaceFromWith("direct:a");
                weaveAddLast().to("mock:emptyFile");
            }
        });
        camelContext.start();
        MockEndpoint mock = camelContext.getEndpoint("mock:emptyFile", MockEndpoint.class);
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