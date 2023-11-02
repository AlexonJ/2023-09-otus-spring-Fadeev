package ru.otus.spring.testapplication.config;

import jakarta.validation.constraints.Min;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;

@Data
@Component
@ConfigurationProperties(prefix = "application")
public class AppConfig implements TestFileNameProvider, TestConfig, LocaleProvider {

    private String testFileName;

    @Min(1)
    private Integer numberRightAnswersToPass;

    private Locale defaultLocale;

    private List<Locale> possibleLocales;

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