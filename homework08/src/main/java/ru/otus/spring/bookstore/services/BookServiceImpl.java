package ru.otus.spring.bookstore.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.bookstore.exceptions.EntityNotFoundException;
import ru.otus.spring.bookstore.models.Book;
import ru.otus.spring.bookstore.repositories.AuthorRepository;
import ru.otus.spring.bookstore.repositories.BookRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final AuthorRepository authorRepository;

    private final BookRepository bookRepository;

    @Override
    public Optional<Book> findById(String id) {
        return bookRepository.findById(id);
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book insert(String title, String authorId, List<String> genres) {
        return save(null, title, authorId, genres);
    }

    @Override
    public Book update(String id, String title, String authorId, List<String> genresIds) {
        return save(id, title, authorId, genresIds);
    }

    @Override
    public void deleteById(String id) {
        bookRepository.deleteById(id);
    }

    private Book save(String id, String title, String authorId, List<String> genres) {
        var author = authorRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException("Author with id %s not found".formatted(authorId)));
        var book = new Book(id, title, author, genres);
        return bookRepository.save(book);
    }
}