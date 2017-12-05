package com.tony.bday.greeting;

import org.apache.camel.spring.boot.CamelSpringBootApplicationController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 
 * <strong>Main Class</strong>
 * <ol>
 * <li>Configures main runtime environment for spring boot</li>
 * </ol>
 *
 */
@SpringBootApplication
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class })
public class Application {
    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(Application.class, args);
        ctx.start();
        //Start camel listener
        CamelSpringBootApplicationController applicationController = ctx.getBean(CamelSpringBootApplicationController.class);
        applicationController.run();
        ctx.stop();
        ctx.close();
    }
}
