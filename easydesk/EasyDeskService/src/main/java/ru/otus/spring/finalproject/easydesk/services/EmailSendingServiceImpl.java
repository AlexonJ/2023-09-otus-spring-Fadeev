package ru.otus.spring.finalproject.easydesk.services;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SignalType;
import ru.otus.spring.finalproject.easydesk.models.db.Ticket;
import ru.otus.spring.finalproject.easydesk.models.db.User;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class EmailSendingServiceImpl implements EmailSendingService {

    Logger logger = LoggerFactory.getLogger(EmailSendingServiceImpl.class);

    private final JavaMailSender emailSender;

    private final MessageGeneratingService messageGeneratingService;

    @Override
    public void sendEmailTicketAssigned(User user, Ticket ticket) {
        Mono.zip(Mono.just(user), Mono.just(ticket))
                .map(objects -> messageGeneratingService.generateBookingConfirmation(objects.getT1(), objects.getT2()))
                .doOnNext(this::sendSimpleEmail)
                .subscribe();
    }

    @Override
    public void sendSimpleEmail(SimpleMailMessage message) {
        try {
            emailSender.send(message);
            logger.info("Email message with new ticket information is sent to {}.",
                    String.join(", ", Objects.requireNonNull(message.getTo())));
        } catch (MailException ex) {
            logger.error("An error occurred while sending email to %s : %s caused by %s".formatted(
                    String.join(", ", Objects.requireNonNull(message.getTo()))
                    , ex.getMessage(), ex.getCause().getMessage()));
        }
    }

}
