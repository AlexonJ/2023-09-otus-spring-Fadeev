package ru.otus.spring.bookstore.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.bookstore.models.Book;

public interface BookRepository extends MongoRepository<Book, String> {
}
