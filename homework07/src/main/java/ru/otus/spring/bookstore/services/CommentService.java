package ru.otus.spring.bookstore.services;

import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.bookstore.dtos.CommentDto;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    List<CommentDto> findCommentsByBookId(long id);

    Optional<CommentDto> findById(long id);

    CommentDto insert(long bookId, String content);

    CommentDto updateById(long id, String content);

    void deleteById(long id);
}
