package ru.otus.spring.bookstore.services;

import ru.otus.spring.bookstore.dtos.GenreDto;

import java.util.List;

public interface GenreService {
    List<GenreDto> findAll();
}
