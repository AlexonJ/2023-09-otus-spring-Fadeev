package ru.otus.spring.bookstore.repositories;

import lombok.Getter;
import ru.otus.spring.bookstore.exceptions.EntityNotFoundException;
import ru.otus.spring.bookstore.models.Author;
import ru.otus.spring.bookstore.models.Book;

import java.util.List;

public class TestDataHolder {

    @Getter
    private static List<Author> authors;

    @Getter
    private static List<Book> books;

    public static void prepareTestData(){
        authors = getDbAuthors();
        books = getDbBooks();
    }


    private static List<Author> getDbAuthors() {
        return List.of(
                new Author("1", "George Orwell"),
                new Author("2", "Jane Austen"),
                new Author("3", "F. Scott Fitzgerald"),
                new Author("4", "Harper Lee"),
                new Author("5", "Ernest Hemingway"),
                new Author("6", "J.K. Rowling"),
                new Author("7", "Leo Tolstoy"),
                new Author("8", "Agatha Christie")
        );
    }

    private static List<Book> getDbBooks() {
        return List.of(
                new Book(null, "1984", getAuthorByName("George Orwell"),
                        List.of("Science Fiction")),
                new Book(null, "Pride and Prejudice", getAuthorByName("Jane Austen"),
                        List.of("Romance"), List.of("Have already been read")),
                new Book(null, "The Great Gatsby", getAuthorByName("F. Scott Fitzgerald"),
                        List.of("Classic")),
                new Book(null, "To Kill a Mockingbird", getAuthorByName("Harper Lee"),
                        List.of("Mystery")),
                new Book(null, "Harry Potter and the Philosopher's Stone",
                        getAuthorByName("J.K. Rowling"), List.of("Fantasy")),
                new Book(null, "War and Peace", getAuthorByName("Leo Tolstoy"),
                        List.of("Historical Fiction")),
                new Book(null, "Crime and Punishment", getAuthorByName("Leo Tolstoy"),
                        List.of("Crime")),
                new Book(null, "Harry Potter and the Chamber of Secrets",
                        getAuthorByName("J.K. Rowling"), List.of("Fantasy")),
                new Book(null, "Harry Potter and the Prisoner of Azkaban",
                        getAuthorByName("J.K. Rowling"), List.of("Fantasy")),
                new Book(null, "The Adventures of Sherlock Holmes",
                        getAuthorByName("Agatha Christie"), List.of("Mystery"))
        );
    }
    
    private static Author getAuthorByName(String AuthorName) {
        return getDbAuthors().stream().filter(author -> author.getFullName().equals(AuthorName)).findFirst().orElseThrow(
                () -> new EntityNotFoundException("Author with name %s not found".formatted(AuthorName)));
    }

}
