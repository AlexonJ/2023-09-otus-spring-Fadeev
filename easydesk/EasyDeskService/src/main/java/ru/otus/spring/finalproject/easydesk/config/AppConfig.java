package ru.otus.spring.finalproject.easydesk.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import ru.otus.spring.finalproject.easydesk.repositories.SettingsProvider;

@Data
@AllArgsConstructor
@ConfigurationProperties(prefix = "service")
public class AppConfig implements SettingsProvider {

    private final String basePath;

    private final Boolean emailSendingEnabled;

    private final String defaultPriority;

    private final Integer daysDuePeriod;

    private final String historyServicePath;

    private final Boolean historySendingEnabled;
    
}
