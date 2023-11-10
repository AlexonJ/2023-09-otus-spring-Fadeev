package ru.otus.spring.bookstore.services;

import ru.otus.spring.bookstore.models.Genre;

import java.util.List;

public interface GenreService {
    List<Genre> findAll();
}
