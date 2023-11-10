package ru.otus.spring.testapplication.service;

public interface MessageService {

    String getMessage(String code);

    String getMessage(String code, String[] args);
}
