package ru.otus.spring.bookstore.repositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import ru.otus.spring.bookstore.models.Book;

public interface BookRepository extends ReactiveCrudRepository<Book, Long> {

}
