package ru.otus.spring.bookstore.repositories;

import ru.otus.spring.bookstore.models.Comment;

import java.util.Optional;

public interface CommentRepository {

    Optional<Comment> findById(long id);

//    List<Comment> findAllByBookId(long id);

    Comment save(Comment comment);

    void deleteById(long id);
}
