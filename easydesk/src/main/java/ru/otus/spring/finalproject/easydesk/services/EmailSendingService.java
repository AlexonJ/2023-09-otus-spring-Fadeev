package ru.otus.spring.finalproject.easydesk.services;

import org.springframework.mail.SimpleMailMessage;

public interface EmailSendingService {

    void sendSimpleEmail(SimpleMailMessage message);
}
