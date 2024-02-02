package ru.otus.spring.bookstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// url h2 консоли: http://localhost:8080/h2-console
// url базы: jdbc:h2:mem:bookstoredb

@SpringBootApplication
public class BookstoreApp {

	public static void main(String[] args) {
		SpringApplication.run(BookstoreApp.class, args);
		System.out.printf("URL H2 console %n%s%n",
				"http://localhost:8080/h2-console");
	}

}