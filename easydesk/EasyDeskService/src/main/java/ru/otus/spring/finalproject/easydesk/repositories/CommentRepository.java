package ru.otus.spring.finalproject.easydesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.finalproject.easydesk.models.db.Comment;
import ru.otus.spring.finalproject.easydesk.models.db.Ticket;

import java.util.stream.Stream;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Stream<Comment> findAllByTicket(Ticket ticket);

}
