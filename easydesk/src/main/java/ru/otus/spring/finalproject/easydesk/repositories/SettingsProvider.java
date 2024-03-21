package ru.otus.spring.finalproject.easydesk.repositories;

public interface SettingsProvider {

    String getBasePath();

    Boolean getAllowSendingEmail();

    String getDefaultPriority();

    Integer getDaysDuePeriod();
}
