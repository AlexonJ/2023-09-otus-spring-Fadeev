package ru.otus.spring.testapplication.config;

import jakarta.validation.constraints.Min;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Locale;

@Data
@Configuration
@ConfigurationProperties(prefix = "application")
public class AppConfig implements TestFileNameProvider, TestConfig, LocaleProvider {

    private String testFileName;

    @Min(1)
    private Integer numberRightAnswersToPass;

    private Locale defaultLocale;

    private ArrayList<Locale> possibleLocales;

    private Locale currentLocale;

    public String getTestFileName() {
        return testFileName.replace("locale", getCurrentLocale().toString());
    }

    public Locale getCurrentLocale() {
        if (currentLocale == null) {
            return this.defaultLocale;
        } else {
            return this.currentLocale;
        }
    }

}