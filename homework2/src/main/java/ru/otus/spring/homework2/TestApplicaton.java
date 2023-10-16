package ru.otus.spring.homework2;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.otus.spring.homework2.service.TestRunnerService;


@Configuration
@ComponentScan
public class TestApplicaton {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(TestApplicaton.class);

        context.getBean(TestRunnerService.class).run();
    }
}