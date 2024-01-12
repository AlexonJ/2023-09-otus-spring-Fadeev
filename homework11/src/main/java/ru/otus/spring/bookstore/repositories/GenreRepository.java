package ru.otus.spring.bookstore.repositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import ru.otus.spring.bookstore.models.Genre;

import java.util.List;

public interface GenreRepository extends ReactiveCrudRepository<Genre, Long> {
    Flux<Genre> findAllByIdIn(List<Long> ids);
}
