package ru.otus.spring.bookstore.repositories;

import lombok.NonNull;
import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.bookstore.models.Book;

import java.util.List;

public interface BookRepository extends MongoRepository<Book, String> {

    @NonNull
    List<Book> findAll();

}
