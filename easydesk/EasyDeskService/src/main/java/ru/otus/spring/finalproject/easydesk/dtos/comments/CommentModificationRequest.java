package ru.otus.spring.finalproject.easydesk.dtos.comments;

import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.spring.finalproject.easydesk.models.db.Ticket;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CommentModificationRequest {

    @Column
    private String content;

    @ManyToOne
    private Ticket ticket;
}
