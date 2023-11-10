package ru.otus.spring.homework01;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.spring.homework01.service.TestRunnerService;

public class TestApplicaton {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        var testRunnerService = context.getBean(TestRunnerService.class);
        testRunnerService.run();

    }
}