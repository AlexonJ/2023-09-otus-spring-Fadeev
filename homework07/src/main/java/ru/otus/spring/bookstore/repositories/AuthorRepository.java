package ru.otus.spring.bookstore.repositories;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.bookstore.models.Author;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {

}
