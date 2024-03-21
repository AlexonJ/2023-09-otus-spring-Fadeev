package ru.otus.spring.finalproject.easydesk.models.db;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import ru.otus.spring.finalproject.easydesk.models.enums.TicketCategory;
import ru.otus.spring.finalproject.easydesk.models.enums.TicketPriority;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String code;

    @Column
    private String title;

    @Column
    private String description;

    @Column
    @Enumerated(EnumType.STRING)
    private TicketPriority priority;

    @Column
    @Enumerated(EnumType.STRING)
    private TicketCategory category;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

    @Column
    private LocalDateTime dueDate;

    @ManyToOne
    private User createdBy;

    @ManyToOne
    private User assignedTo;

    @ManyToOne
    private Waypoint waypoint;

    @OneToMany(mappedBy = "ticket", orphanRemoval = true)
    private List<Comment> comments;

    @OneToMany(mappedBy = "ticket", orphanRemoval = true)
    private List<Attachment> attachments;

}
