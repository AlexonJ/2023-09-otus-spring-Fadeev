package ru.otus.spring.homeworks.hw15springintegration;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.integration.annotation.IntegrationComponentScan;
import ru.otus.spring.homeworks.hw15springintegration.services.OrderGeneratorService;

// url h2 консоли: http://localhost:8080/h2-console
// url базы: jdbc:h2:mem:bookstoredb

@SpringBootApplication
@IntegrationComponentScan
@RequiredArgsConstructor
public class SpringIntegrationApp {

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(SpringIntegrationApp.class, args);
		OrderGeneratorService orderService = ctx.getBean(OrderGeneratorService.class);
		orderService.startGeneratingOrders();
	}

}