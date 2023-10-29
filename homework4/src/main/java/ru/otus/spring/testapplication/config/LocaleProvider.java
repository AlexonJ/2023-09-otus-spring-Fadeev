package ru.otus.spring.testapplication.config;

import java.util.List;
import java.util.Locale;

public interface LocaleProvider {
    Locale getDefaultLocale();

    Locale getCurrentLocale();

    void setCurrentLocale(Locale locale);

    List<Locale> getPossibleLocales();
}
