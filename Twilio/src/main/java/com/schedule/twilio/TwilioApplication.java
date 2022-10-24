package com.schedule.twilio;

import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.CrossOrigin;

@EnableScheduling
@SpringBootApplication
public class TwilioApplication {

    public static void main(String[] args) {
        SpringApplication.run(TwilioApplication.class, args);
    }

}
