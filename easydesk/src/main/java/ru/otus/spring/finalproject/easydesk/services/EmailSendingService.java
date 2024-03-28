package ru.otus.spring.finalproject.easydesk.services;

import org.springframework.mail.SimpleMailMessage;
import ru.otus.spring.finalproject.easydesk.models.db.Ticket;
import ru.otus.spring.finalproject.easydesk.models.db.User;

public interface EmailSendingService {

    void sendEmailTicketAssigned(User user, Ticket ticket);

    void sendSimpleEmail(SimpleMailMessage message);
}
