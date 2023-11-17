package ru.otus.spring.bookstore.repositories;

import lombok.NonNull;
import org.springframework.data.repository.CrudRepository;
import ru.otus.spring.bookstore.models.Book;

import java.util.List;

public interface BookRepository extends CrudRepository<Book, Long> {

    @NonNull
    List<Book> findAll();

}
