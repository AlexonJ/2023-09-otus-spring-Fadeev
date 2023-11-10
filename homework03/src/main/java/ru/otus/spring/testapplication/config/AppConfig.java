package ru.otus.spring.testapplication.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;


@Data
@Configuration
@ConfigurationProperties(prefix = "application")
public class AppConfig implements TestFileNameProvider, TestConfig, LocaleProvider {

    private String testFileName;

    private Integer numberRightAnswersToPass;

    private Locale locale;

}