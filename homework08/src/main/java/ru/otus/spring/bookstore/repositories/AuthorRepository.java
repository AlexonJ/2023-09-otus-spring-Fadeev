package ru.otus.spring.bookstore.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.bookstore.models.Author;

import java.util.Optional;

public interface AuthorRepository extends MongoRepository<Author, String> {
    Optional<Author> findByFullName(String name);

}
