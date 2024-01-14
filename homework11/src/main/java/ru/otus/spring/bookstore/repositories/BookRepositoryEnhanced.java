package ru.otus.spring.bookstore.repositories;

import io.r2dbc.spi.Row;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.bookstore.exceptions.EntityNotFoundException;
import ru.otus.spring.bookstore.models.Author;
import ru.otus.spring.bookstore.models.Book;
import ru.otus.spring.bookstore.models.Comment;
import ru.otus.spring.bookstore.models.Genre;

import java.util.ArrayList;
import java.util.List;

import static ru.otus.spring.bookstore.services.BookService.BOOK_NOT_FOUND_MESSAGE;

@Repository
@RequiredArgsConstructor
public class BookRepositoryEnhanced {

    private final DatabaseClient databaseClient;

    private final CommentRepository commentRepository;

    private final BookRepository bookRepository;

    private static final String QUERY_SELECT_BOOKS =
            "SELECT " +
            "books.id AS book_id, books.title AS book_title, " +
            "authors.id AS author_id, authors.full_name AS author_name " +
            "FROM books " +
            "LEFT JOIN authors ON books.author_id = authors.id";

    public Mono<Book> findByIdWithDetails(Long id) {
        String query = QUERY_SELECT_BOOKS + " WHERE books.id = :bookId";

        return databaseClient.sql(query)
                .bind("bookId", id)
                .map(this::mapBook)
                .one()
                .switchIfEmpty(Mono.error(() -> new EntityNotFoundException(BOOK_NOT_FOUND_MESSAGE.formatted(id))))
                .flatMap(this::enrichBookWithGenres)
                .flatMap(this::enrichBookWithComments);
    }

    public Flux<Book> findAllWithDetails() {
        return databaseClient.sql(QUERY_SELECT_BOOKS)
                .map(this::mapBook)
                .all()
                .flatMap(this::enrichBookWithGenres)
                .flatMap(this::enrichBookWithComments)
                .sort((book1, book2) -> Math.toIntExact(book1.getId() - book2.getId()));
    }

    public Book mapBook(Row row, Object o) {
        var id = row.get("book_id", Long.class);
        var title = row.get("book_title", String.class);

        Author author = new Author(row.get("author_id", Long.class), row.get("author_name", String.class));
        Book book =  new Book(id, title, author, new ArrayList<>(), new ArrayList<>());
        return book;
    }

    private Mono<Book> enrichBookWithComments(Book book) {
        return commentRepository.findAllByBookId(book.getId()).collectList().map(comments -> {
            book.setComments(comments);
            return book;
        });
    }

    private Mono<Book> enrichBookWithGenres(Book book) {
        return findGenresByBookId(book.getId())
                .collectList()
                .map(genres -> {
                    book.setGenres(genres);
                    return book;
                });
    }

    private Flux<Genre> findGenresByBookId(Long bookId) {
        return databaseClient.sql("SELECT genres.id AS genre_id, genres.name AS genre_name FROM genres " +
                        "JOIN books_genres ON genres.id = books_genres.genre_id " +
                        "WHERE books_genres.book_id = :bookId")
                .bind("bookId", bookId)
                .map((row, rowMetadata) -> new Genre(row.get("genre_id", Long.class), row.get("genre_name", String.class)))
                .all();
    }


    public Mono<Book> save(Book book) {
        return bookRepository.save(book)
                .flatMap(savedBook -> updateAuthorId(savedBook)
                        .then(saveGenres(savedBook.getGenres(), savedBook.getId()))
                        .then(saveComments(savedBook.getComments(), savedBook.getId()))
                        .then(this.findByIdWithDetails(savedBook.getId())));
    }

    private Mono<Void> updateAuthorId(Book book) {

        return databaseClient.sql("UPDATE books SET author_id = :authorId WHERE id = :id")
                .bind("authorId", book.getAuthor().getId())
                .bind("id", book.getId())
                .fetch()
                .rowsUpdated()
                .then();
    }

    private Mono<Void> saveGenres(List<Genre> genres, Long bookId) {
        return databaseClient.sql("DELETE FROM books_genres WHERE book_id = :bookId")
                .bind("bookId", bookId)
                .fetch()
                .rowsUpdated()
                .thenMany(Flux.fromIterable(genres))
                .flatMap(genre -> databaseClient.sql("INSERT INTO books_genres (book_id, genre_id) VALUES (:book_id, :genre_id)")
                        .bind("book_id", bookId)
                        .bind("genre_id", genre.getId())
                        .fetch()
                        .rowsUpdated())
                .then();
    }

    private Mono<Void> saveComments(List<Comment> comments, Long bookId) {
        return commentRepository.findAllByBookId(bookId)
                .collectList()
                .flatMap(existingComments -> {
                            var commentsIds = comments.stream().map(Comment::getId).toList();
                            var commentsToDelete = existingComments.stream().map(Comment::getId)
                                    .filter(commentId -> !commentsIds.contains(commentId)).toList();
                            var commentsToSave = existingComments.stream()
                                    .filter(comment -> commentsIds.contains(comment.getId())).toList();
                            Mono<Void> deleteMono = commentRepository.deleteAllById(commentsToDelete).then();
                            return commentRepository.saveAll(commentsToSave).then(deleteMono).then();
                        }
                );
    }

}