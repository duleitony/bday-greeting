package com.tony.bday.greeting.unitTest;

import org.apache.camel.CamelContext;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.camel.test.spring.UseAdviceWith;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(CamelSpringBootRunner.class)
@UseAdviceWith
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@SpringBootTest(classes = GetTargetRecordsTest.class)
public class GetTargetRecordsTest {
    @Produce(uri = "direct:a")
    private ProducerTemplate producerTemplate;
    
    @Autowired
    private CamelContext camelContext;
    
   // DataFormat bindy = new BindyCsvDataFormat(Employee.class);
    @Test
    public void shouldMockEndpoints() throws Exception {
        // context should not be started because we enabled @UseAdviceWith
//        assertFalse(camelContext.getStatus().isStarted());
        camelContext.getRouteDefinitions().get(0).adviceWith(camelContext, new AdviceWithRouteBuilder() {
            @Override
            public void configure() throws Exception {
                replaceFromWith("direct:a");
                weaveAddLast().to("mock:result");
            }
        });
        camelContext.start();
        MockEndpoint mock = camelContext.getEndpoint("mock:result", MockEndpoint.class);
        // Given
        String msg = "Lei, Du, 1975/11/30, lei.du@foobar.com";
        mock.expectedBodiesReceived("msg");
        mock.expectedMessageCount(1);
        // When
        producerTemplate.sendBody("direct:a", msg);
        // Then
        mock.assertIsSatisfied();
    }
}
