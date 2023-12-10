package ru.otus.spring.bookstore.repositories;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.bookstore.models.Book;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

}
