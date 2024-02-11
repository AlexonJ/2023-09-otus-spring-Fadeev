package ru.otus.spring.bookstore.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.otus.spring.bookstore.models.Author;

@RepositoryRestResource(path = "authors")
public interface AuthorRepository extends CrudRepository<Author, Long> {

}
