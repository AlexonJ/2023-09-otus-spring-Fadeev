package ru.otus.spring.bookstore.services;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.bookstore.dtos.AuthorDto;
import ru.otus.spring.bookstore.models.Author;

public interface AuthorService {
    Flux<AuthorDto> findAll();

    Mono<AuthorDto> findById(long id);

    Mono<AuthorDto> insert(String fullName);

    Mono<AuthorDto> update(long id, String fullName);

    Mono<Void> deleteById(long id);

    Mono<Author> save(long id, String fullName);
}
