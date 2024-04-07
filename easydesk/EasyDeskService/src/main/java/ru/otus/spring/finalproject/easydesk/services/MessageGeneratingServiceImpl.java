package ru.otus.spring.finalproject.easydesk.services;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import ru.otus.spring.finalproject.easydesk.models.db.Ticket;
import ru.otus.spring.finalproject.easydesk.models.db.User;

@Service
public class MessageGeneratingServiceImpl implements MessageGeneratingService {

    private static final String TICKET_ASSIGNMENT_SUBJECT = "New Ticket Assigned: [TicketTitle]";
    private static final String TICKET_ASSIGNMENT_MESSAGE =
                    "Hello, [EmployeeName],\n" +
                    "\n" +
                    "You have been assigned a new ticket on our system. Here are the details:\n" +
                    "\n" +
                    "Ticket Code: [TicketCode]\n" +
                    "Ticket Title: [TicketTitle]\n" +
                    "Description: [TicketDescription]\n" +
                    "Priority: [TicketPriority]\n" +
                    "Assigned By: [AssignedBy]\n" +
                    "\n" +
                    "Please login to our ticket management system to view and address this ticket at your earliest convenience. " +
                            "If you have any questions or need further assistance, feel free to reach out to the support team.\n" +
                    "\n" +
                    "Thank you for your attention to this matter.";

    @Override
    public SimpleMailMessage generateBookingConfirmation(User assignedTo, Ticket ticket) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(assignedTo.getEmail());

        message.setSubject(TICKET_ASSIGNMENT_SUBJECT
                .replace("[TicketTitle]", ticket.getTitle()));

        message.setText(TICKET_ASSIGNMENT_MESSAGE
                .replace("[EmployeeName]", assignedTo.getFullName())
                .replace("[TicketTitle]", ticket.getTitle())
                .replace("[TicketCode]", ticket.getCode())
                .replace("[TicketDescription]", ticket.getDescription())
                .replace("[TicketPriority]", ticket.getPriority().toString())
                .replace("[AssignedBy]", ticket.getAssignedBy().getFullName()));

        return message;
    }

}
