package ru.otus.spring.bookstore.services;

import java.util.List;
import java.util.Optional;

public interface CommentService {

    List<String> findCommentsByBookId(String bookId);

    Optional<String> findCommentByBookIdAndIndex(String bookId, int index);

    String add(String bookId, String content);

    String updateByBookIdAndIndex(String bookId, int index, String content);

    void deleteByBookIdAndIndex(String bookId, int index);
}
