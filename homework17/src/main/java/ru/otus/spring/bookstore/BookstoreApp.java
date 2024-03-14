package ru.otus.spring.bookstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// url h2 консоли: http://localhost:8080/h2-console
// url базы: jdbc:h2:mem:bookstoredb

@SpringBootApplication
public class BookstoreApp {

	private static final Logger LOGGER = LoggerFactory.getLogger(BookstoreApp.class);

	public static void main(String[] args) {
		SpringApplication.run(BookstoreApp.class, args);

		LOGGER.info("Main page is here: {}", "http://localhost:8080");
		LOGGER.info("H2 console here: {}", "http://localhost:8080/h2-console");
		LOGGER.info("Actuator health page here: {}", "http://localhost:8080/actuator/health");
		LOGGER.info("HAL explorer here: {}", "http://localhost:8080/api-rest/explorer/");

	}

}