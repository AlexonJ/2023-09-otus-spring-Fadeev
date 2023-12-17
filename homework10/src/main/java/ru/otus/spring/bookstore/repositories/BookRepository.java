package ru.otus.spring.bookstore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.bookstore.models.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

}
