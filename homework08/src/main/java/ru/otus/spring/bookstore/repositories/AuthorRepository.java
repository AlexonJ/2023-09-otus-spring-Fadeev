package ru.otus.spring.bookstore.repositories;

import lombok.NonNull;
import ru.otus.spring.bookstore.models.Author;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends MongoRepository<Author, String> {

    @NonNull
    List<Author> findAll();

    Optional<Author> findByFullName(String name);

}
