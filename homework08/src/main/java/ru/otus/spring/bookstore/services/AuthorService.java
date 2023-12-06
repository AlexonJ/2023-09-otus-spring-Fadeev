package ru.otus.spring.bookstore.services;

import ru.otus.spring.bookstore.models.Author;

import java.util.List;

public interface AuthorService {
    List<Author> findAll();
}
