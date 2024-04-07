package ru.otus.spring.finalproject.easydesk.models.db;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "attachments")
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String filename;

    @Column
    private String type;

    @Column
    private LocalDateTime createdAt;

    @ManyToOne
    private User createdBy;

//    @Lob
    @Column(columnDefinition = "bytea")
    private byte[] content;

    @Column
    private long size;

    @ManyToOne
    private Ticket ticket;

    public Attachment(Long id, String filename, String type, User createdBy, LocalDateTime createdAt, Long size) {
        this.id = id;
        this.filename = filename;
        this.type = type;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.size = size;
    }

}