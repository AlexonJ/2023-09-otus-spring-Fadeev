package ru.otus.spring.finalproject.easydesk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.otus.spring.finalproject.easydesk.config.AppConfig;

@SpringBootApplication
@EnableConfigurationProperties(AppConfig.class)
public class EasyDeskApp {

    private static final Logger LOGGER = LoggerFactory.getLogger(EasyDeskApp.class);

    public static void main(String[] args) {
        SpringApplication.run(EasyDeskApp.class, args);
        LOGGER.info("Main page is here: {}", "http://localhost:8080");
        LOGGER.info("Swagger UI is here: {}", "http://localhost:8080/swagger-ui/index.html");
        LOGGER.info("H2 console here: {}", "http://localhost:8080/h2-console");
        LOGGER.info("Actuator health page here: {}", "http://localhost:8080/actuator/health");
        LOGGER.info("HAL explorer here: {}", "http://localhost:8080/api-rest/explorer/");
    }

}
