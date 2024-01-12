package ru.otus.spring.bookstore.repositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import ru.otus.spring.bookstore.models.Author;

public interface AuthorRepository extends ReactiveCrudRepository<Author, Long> {

}
