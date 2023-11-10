package ru.otus.spring.bookstore.repositories;

import ru.otus.spring.bookstore.models.Genre;

import java.util.List;

public interface GenreRepository {
    List<Genre> findAll();

    List<Genre> findAllByIds(List<Long> ids);
}
