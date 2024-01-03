package ru.otus.spring.bookstore.services;

import ru.otus.spring.bookstore.dtos.CommentDto;

import java.util.List;

public interface CommentService {
    List<CommentDto> findCommentsByBookId(long id);

    CommentDto findById(long id);

    CommentDto insert(long bookId, String content);

    CommentDto updateById(long id, String content);

    void deleteById(long id);
}
