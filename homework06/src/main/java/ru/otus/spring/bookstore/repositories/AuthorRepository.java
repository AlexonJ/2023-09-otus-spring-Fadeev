package ru.otus.spring.bookstore.repositories;

import ru.otus.spring.bookstore.models.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {
    List<Author> findAll();

    Optional<Author> findById(long id);
}
