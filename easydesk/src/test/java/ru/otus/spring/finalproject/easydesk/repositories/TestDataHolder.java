package ru.otus.spring.finalproject.easydesk.repositories;

import lombok.Getter;
import ru.otus.spring.finalproject.easydesk.models.db.Attachment;
import ru.otus.spring.finalproject.easydesk.models.db.Comment;
import ru.otus.spring.finalproject.easydesk.models.db.Ticket;
import ru.otus.spring.finalproject.easydesk.models.db.User;
import ru.otus.spring.finalproject.easydesk.models.enums.Category;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class TestDataHolder {

    public static final LocalDateTime TICKET_CREATION_START_DATE_TIME = LocalDateTime.of(2024, 2,15, 8, 0);

    @Getter
    private static List<Ticket> tickets;

    @Getter
    private static List<User> users;

    @Getter
    private static List<Comment> comments;

    @Getter
    private static List<Attachment> attachments;

    public static void prepareTestData() {
        users = getDbUsers();
        tickets = getDbTickets();
        comments = getDbComments();
        attachments = getDbAttachments();
    }

    public static List<Ticket> getDbTickets() {
        return IntStream.range(1, 4).boxed().map(value -> {
            var ticket = new Ticket();
            ticket.setId(value);
            ticket.setTitle("Ticket_%d_title".formatted(value));
            ticket.setDescription("Ticket_%d_description".formatted(value));
            ticket.setCategory(Category.values()[value]);
            ticket.setCode(ticket.getCategory().getCodePrefix() + value);
            ticket.setCreatedAt(TICKET_CREATION_START_DATE_TIME.plusDays(value));
            ticket.setDueDate(ticket.getCreatedAt().plusDays(3));
            ticket.setAssignedTo(getUserById(value));
            return ticket;}).toList();
    }

    public static List<User> getDbUsers() {
        return IntStream.range(1, 4).boxed().map(value -> {
            var user = new User();
            user.setId(value);
            user.setUsername("username_%s".formatted(value));
            user.setFirstName("First_name_%s".formatted(value));
            user.setLastName("Last_name_%s".formatted(value));
            return user;
        }).toList();
    }

    public static List<Comment> getDbComments() {
        List<Comment> comments = new ArrayList<>();
        for(Ticket ticket : tickets) {
            for (int i = 1; i < 20; i++) {
                var comment = new Comment();
                comment.setId(i);
                comment.setTicket(ticket);
                comment.setCreatedAt(ticket.getCreatedAt().plusHours(i));
                comment.setContent("Comment_%s_content".formatted(i));
                comment.setCreatedBy(ticket.getCreatedBy());
                comments.add(comment);
            }
        }
        return comments;
    }

    public static List<Attachment> getDbAttachments() {
        List<Attachment> attachments = new ArrayList<>();
        for(Ticket ticket : tickets) {
            for (int i = 1; i < 5; i++) {
                var attachment = new Attachment();
                attachment.setId(i);
                attachment.setTicket(ticket);
                attachment.setCreatedAt(ticket.getCreatedAt().plusHours(i));
                attachment.setCreatedBy(ticket.getCreatedBy());
                attachment.setFilename("Attachment_" + i + ".txt");
                attachment.setType("text/plain");
                attachment.setContent("Attachment_%s_content".formatted(i).getBytes());
                attachments.add(attachment);
            }
        }
        return attachments;
    }

    public static Ticket getTicketById(long id) {
        return getTickets().stream().filter(ticket -> ticket.getId() == id).findFirst().orElseThrow();
    }

    public static User getUserById(long id) {
        return getUsers().stream().filter(user -> user.getId() == id).findFirst().orElseThrow();
    }

    public static List<Comment> getCommentsByTicketCode(String code) {
        return getComments().stream().filter(comment -> comment.getTicket().getCode().equals(code)).toList();
    }

    public static Comment getCommentById(Long id) {
        return getComments().stream().filter(comment -> comment.getTicket().getId() == id).findFirst().get();
    }

    public static List<Attachment> getAttachmentsByTicketCode(String code) {
        return getAttachments().stream().filter(attachment -> attachment.getTicket().getCode().equals(code)).toList();
    }

    public static Attachment getAttachmentById(Long id) {
        return getAttachments().stream().filter(attachment -> attachment.getId() == id).findFirst().get();
    }
}
