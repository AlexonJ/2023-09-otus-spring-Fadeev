package ru.otus.spring.homework02.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


@Configuration
@PropertySource("classpath:application.properties")
public class AppConfig implements TestFileNameProvider, TestConfig {

    private final String testFileName;

    private final Integer numberRightAnswersToPass;

    public AppConfig(@Value("${application.testFileName}") String testFileName,
                     @Value("${application.numberRightAnswersToPass}") Integer numberRightAnswersToPass) {
        this.testFileName = testFileName;
        this.numberRightAnswersToPass = numberRightAnswersToPass;
    }

    @Override
    public String getTestFileName() {
        return testFileName;
    }

    @Override
    public Integer getNumberRightAnswersToPass() {
        return this.numberRightAnswersToPass;
    }
}