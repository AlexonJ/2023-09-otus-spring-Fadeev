package ru.otus.spring.finalproject.easydesk.services;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Objects;

@RequiredArgsConstructor
@Service
public class EmailSendingServiceImpl implements EmailSendingService {

    Logger logger = LoggerFactory.getLogger(EmailSendingServiceImpl.class);

    private final JavaMailSender emailSender;

    @Override
    public void sendSimpleEmail(SimpleMailMessage message) {
        try {
            emailSender.send(message);
        } catch (MailException ex) {
            logger.error("An error occurred while sending email to %s".formatted(
                    String.join(", ", Objects.requireNonNull(message.getTo()))));
        }
    }

}
