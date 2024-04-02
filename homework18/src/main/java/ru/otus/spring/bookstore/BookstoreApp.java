package ru.otus.spring.bookstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// url h2 консоли: http://localhost:8080/h2-console
// url базы: jdbc:h2:mem:bookstoredb

@SpringBootApplication
public class BookstoreApp {

	public static void main(String[] args) {
		SpringApplication.run(BookstoreApp.class, args);
		System.out.printf("Main page is here: %n%s%n",
				"http://localhost:8080");
		System.out.printf("Actuator health page here: %n%s%n",
				"http://localhost:8080/actuator/health");
		System.out.printf("HAL explorer here: %n%s%n",
				"http://localhost:8080/api-rest/explorer/");
	}

}