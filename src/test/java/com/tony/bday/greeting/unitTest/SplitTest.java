package com.tony.bday.greeting.unitTest;

import com.tony.bday.greeting.router.RouterForCsvToEmail;
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

@RunWith(CamelSpringBootRunner.class)
@UseAdviceWith
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@SpringBootTest(classes = RouterForCsvToEmail.class)
public class SplitTest {
    @Produce(uri = "direct:a")
    private ProducerTemplate producerTemplate;
    
    @Autowired
    private CamelContext camelContext;

    @Test
    public void singleRecordTest()throws Exception  {
        String msg = "Lei, Du, 1975/11/30, lei.du@foobar.com";

        camelContext.getRouteDefinition("router-1").adviceWith(camelContext, new AdviceWithRouteBuilder() {
            @Override
            public void configure() throws Exception {
                replaceFromWith("direct:a");
                weaveById("unmarshal-1").before().to("mock:singleRecordTest");
            }
        });
        camelContext.start();
        MockEndpoint mock = camelContext.getEndpoint("mock:singleRecordTest", MockEndpoint.class);
        mock.expectedBodiesReceived(msg);
        mock.expectedMessageCount(1);

        producerTemplate.sendBody("direct:a", msg);

        mock.assertIsSatisfied();
    }

    @Test
    public void mutipleRecordTest()throws Exception  {
        String msg = "Lei, Du, 1975/11/30, lei.du@foobar.com" + "\n" + "Anthony, Zhao, 1975/11/20, anthony.zhao@foobar.com"
                + "\n" + "Lei, Du, 1975/11/30, lei.du@foobar.com";

        camelContext.getRouteDefinition("router-1").adviceWith(camelContext, new AdviceWithRouteBuilder() {
            @Override
            public void configure() throws Exception {
                replaceFromWith("direct:a");
                weaveById("unmarshal-1").before().to("mock:mutipleRecordTest");
            }
        });
        camelContext.start();
        MockEndpoint mock = camelContext.getEndpoint("mock:mutipleRecordTest", MockEndpoint.class);
        mock.expectedMessageCount(3);

        producerTemplate.sendBody("direct:a", msg);

        mock.assertIsSatisfied();
    }

    @Test
    public void emptyMessageTest()throws Exception  {
        String msg = "";

        camelContext.getRouteDefinition("router-1").adviceWith(camelContext, new AdviceWithRouteBuilder() {
            @Override
            public void configure() throws Exception {
                replaceFromWith("direct:a");
                weaveById("unmarshal-1").before().to("mock:emptyMessageTest");
            }
        });
        camelContext.start();
        MockEndpoint mock = camelContext.getEndpoint("mock:emptyMessageTest", MockEndpoint.class);
        mock.expectedMessageCount(0);

        producerTemplate.sendBody("direct:a", msg);

        mock.assertIsSatisfied();
    }

    @After
    public void finalize() throws Exception {
        camelContext.stop();
    }
}
