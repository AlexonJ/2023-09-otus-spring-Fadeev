package ru.otus.spring.bookstore.services;

import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.bookstore.models.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
    Optional<Book> findById(String id);

    List<Book> findAll();

    Book insert(String title, String authorId, List<String> genresIds);

    @Transactional
    Book update(String id, String title, String authorId, List<String> genresIds);

    @Transactional
    void deleteById(String id);
}
