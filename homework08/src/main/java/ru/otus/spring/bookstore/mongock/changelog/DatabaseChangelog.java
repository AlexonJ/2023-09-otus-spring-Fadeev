package ru.otus.spring.bookstore.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.otus.spring.bookstore.models.Author;
import ru.otus.spring.bookstore.models.Book;
import ru.otus.spring.bookstore.repositories.AuthorRepository;
import ru.otus.spring.bookstore.repositories.BookRepository;

import java.util.List;

@ChangeLog
public class DatabaseChangelog {

    @ChangeSet(order = "001", id = "dropDb", author = "fadeev", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "002", id = "insertAuthors", author = "fadeev")
    public void fillAuthors(AuthorRepository authorRepository) {

        List<Author> authors = List.of(
                new Author(null, "George Orwell"),
                new Author(null, "Jane Austen"),
                new Author(null, "F. Scott Fitzgerald"),
                new Author(null, "Harper Lee"),
                new Author(null, "Ernest Hemingway"),
                new Author(null, "J.K. Rowling"),
                new Author(null, "Leo Tolstoy"),
                new Author(null, "Agatha Christie")
        );
        authorRepository.saveAll(authors);

    }

    @ChangeSet(order = "003", id = "insertBooks", author = "fadeev")
    public void fillBooks(BookRepository bookRepository, AuthorRepository authorRepository) {
        List<Book> books = List.of(
                new Book(null, "1984", authorRepository.findByFullName("George Orwell").orElseThrow(),
                        List.of("Science Fiction"), List.of("Recommended to read")),
                new Book(null, "Pride and Prejudice", authorRepository.findByFullName("Jane Austen").orElseThrow(),
                        List.of("Romance"), List.of("Have already been read")),
                new Book(null, "The Great Gatsby", authorRepository.findByFullName("F. Scott Fitzgerald").orElseThrow(),
                        List.of("Classic")),
                new Book(null, "To Kill a Mockingbird", authorRepository.findByFullName("Harper Lee").orElseThrow(),
                        List.of("Mystery")),
                new Book(null, "Harry Potter and the Philosopher's Stone",
                        authorRepository.findByFullName("J.K. Rowling").orElseThrow(), List.of("Fantasy")),
                new Book(null, "War and Peace", authorRepository.findByFullName("Leo Tolstoy").orElseThrow(),
                        List.of("Historical Fiction")),
                new Book(null, "Crime and Punishment", authorRepository.findByFullName("Leo Tolstoy").orElseThrow(),
                        List.of("Crime")),
                new Book(null, "Harry Potter and the Chamber of Secrets",
                        authorRepository.findByFullName("J.K. Rowling").orElseThrow(), List.of("Fantasy")),
                new Book(null, "Harry Potter and the Prisoner of Azkaban",
                        authorRepository.findByFullName("J.K. Rowling").orElseThrow(), List.of("Fantasy")),
                new Book(null, "The Adventures of Sherlock Holmes",
                        authorRepository.findByFullName("Agatha Christie").orElseThrow(), List.of("Mystery"))
        );
        bookRepository.saveAll(books);
    }

}
