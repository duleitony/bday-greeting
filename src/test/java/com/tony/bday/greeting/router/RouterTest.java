package com.tony.bday.greeting.router;

import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

public class RouterTest extends CamelTestSupport {
//    @EndpointInject(uri = "file://D:\\\\workspace\\\\bday-greeting\\\\src\\\\test\\\\resources?fineName=result.csv")
//    protected MockEndpoint mockEndpoint;

//    DataFormat bindy = new BindyCsvDataFormat(Employee.class);
//
//    @Override
//    @Before
//    public void setUp() throws Exception {
//        super.setUp();
//    }
//
//    @Override
//    protected RouteBuilder createRouteBuilder() {
//        return new AdviceWithRouteBuilder() {
//            public void configure() {
//                weaveById("processor-2").remove(); 
//                weaveById("processor-3").remove(); 
//
//                from("file://D:\\workspace\\bday-greeting\\src\\test\\resources?include=.*.csv")
////                .split().tokenize("\n", 1)
////                .unmarshal(bindy)
//                .process(new GetTargetRecords()).choice().when(body().isNotNull()).id("processor-2")
//                .process(new SendGreetingMessage()).id("processor-3")
//                .to("file://D:\\workspace\\bday-greeting\\src\\test\\resources?fineName=result.csv");
//            }
//        };
//    }

    @Test
    public void test() throws Exception {
//        mockEndpoint.expectedMessageCount(3);
//        assertMockEndpointsSatisfied();
    }

}