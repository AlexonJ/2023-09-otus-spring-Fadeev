package ru.otus.spring.bookstore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.bookstore.models.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {

}
