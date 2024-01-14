package ru.otus.spring.bookstore.services;

import reactor.core.publisher.Flux;
import ru.otus.spring.bookstore.dtos.GenreDto;

public interface GenreService {
    Flux<GenreDto> findAll();
}
