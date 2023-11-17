package ru.otus.spring.bookstore.repositories;

import lombok.NonNull;
import org.springframework.data.repository.CrudRepository;
import ru.otus.spring.bookstore.models.Author;

import java.util.List;

public interface AuthorRepository extends CrudRepository<Author, Long> {

    @NonNull
    List<Author> findAll();

}
