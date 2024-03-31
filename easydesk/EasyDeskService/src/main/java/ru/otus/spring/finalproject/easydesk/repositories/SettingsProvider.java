package ru.otus.spring.finalproject.easydesk.repositories;

public interface SettingsProvider {

    String getBasePath();

    Boolean getEmailSendingEnabled();

    String getDefaultPriority();

    Integer getDaysDuePeriod();

    String getHistoryServicePath();

    Boolean getHistorySendingEnabled();
}
