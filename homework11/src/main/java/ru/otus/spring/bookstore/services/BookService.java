package ru.otus.spring.bookstore.services;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.bookstore.dtos.BookDto;

import java.util.List;

public interface BookService {

    String BOOK_NOT_FOUND_MESSAGE = "Book with id %d not found";

    Mono<BookDto> findByIdWithDetails(Long id);

    Flux<BookDto> findAllWithDetails();

    Mono<BookDto> findById(long id);

    Flux<BookDto> findAll();

    Mono<BookDto> save(long id, String title, long authorId, List<Long> genresIds, List<Long> commentIds);

    Mono<Void> deleteById(long id);
}
