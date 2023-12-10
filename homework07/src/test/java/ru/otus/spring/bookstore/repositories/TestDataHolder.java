package ru.otus.spring.bookstore.repositories;

import lombok.Getter;
import ru.otus.spring.bookstore.models.Author;
import ru.otus.spring.bookstore.models.Book;
import ru.otus.spring.bookstore.models.Comment;
import ru.otus.spring.bookstore.models.Genre;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class TestDataHolder {

    @Getter
    private static List<Author> authors;

    @Getter
    private static List<Genre> genres;

    @Getter
    private static List<Book> books;

    public static void prepareTestData(){
        authors = getDbAuthors();
        genres = getDbGenres();
        books = getDbBooks(authors, genres);
    }


    private static List<Author> getDbAuthors() {
        return IntStream.range(1, 4).boxed()
                .map(id -> new Author(id, "Author_" + id))
                .toList();
    }

    private static List<Genre> getDbGenres() {
        return IntStream.range(1, 7).boxed()
                .map(id -> new Genre(id, "Genre_" + id))
                .toList();
    }

    private static List<Comment> getCommentsForBook(Book book) {
        var currentIndex = (book.getId() - 1) * 2;
        List<Comment> comments = new ArrayList<>();
        for (long i = 1; i < 3; i++ ){
            currentIndex++;
            comments.add(new Comment(currentIndex, "Comment_%d_for_book_%d".formatted(i, book.getId()), book));
        }
        return comments;
    }

    private static List<Book> getDbBooks(List<Author> dbAuthors, List<Genre> dbGenres) {
        return IntStream.range(1, 4).boxed()
                .map(id -> {
                            Book book = new Book(id,
                                    "BookTitle_" + id,
                                    dbAuthors.get(id - 1),
                                    dbGenres.subList((id - 1) * 2, (id - 1) * 2 + 2));
                            book.setComments(getCommentsForBook(book));
                            return book;
                        }
                )
                .toList();
    }

}
