package ru.otus.spring.bookstore.repositories;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.bookstore.models.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
