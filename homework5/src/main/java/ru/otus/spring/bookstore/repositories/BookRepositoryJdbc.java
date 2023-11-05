package ru.otus.spring.bookstore.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.spring.bookstore.models.Author;
import ru.otus.spring.bookstore.models.Book;
import ru.otus.spring.bookstore.models.BookGenreRelation;
import ru.otus.spring.bookstore.models.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;


@Repository
@RequiredArgsConstructor
public class BookRepositoryJdbc implements BookRepository {

    private final GenreRepository genreRepository;

    private final NamedParameterJdbcOperations jdbcOperations;

    private final GenreRelationsRepository genreRelationsRepository;

    @Override
    public Optional<Book> findById(long id) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);

        try {
            Book book = jdbcOperations.queryForObject(
                    "SELECT " +
                            "books.id    AS book_id," +
                            "title       AS book_title," +
                            "author_id   AS author_id," +
                            "full_name   AS author_name " +
                            "FROM books " +
                            "LEFT JOIN authors ON authors.id = books.author_id " +
                            "WHERE books.id = :id", params, new BookRowMapper());

            List<BookGenreRelation> genreRelations = genreRelationsRepository.getGenreRelationsByBookId(id);
            List<Genre> genres = genreRepository.findAllByIds(
                    genreRelations.stream().map(BookGenreRelation::genreId).toList());
            book.setGenres(genres);

            return Optional.of(book);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Book> findAll() {
        var genres = genreRepository.findAll();
        var relations = genreRelationsRepository.getAllGenreRelations();
        var books = getAllBooksWithoutGenres();
        mergeBooksInfo(books, genres, relations);
        return books;
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == 0) {
            return insert(book);
        }
        return update(book);
    }

    @Override
    public void deleteById(long id) {
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("id", id);
        jdbcOperations.update(
                "DELETE FROM books WHERE id = :id", params);
    }

    private List<Book> getAllBooksWithoutGenres() {
        return jdbcOperations.query(
                "SELECT " +
                        "books.id  AS book_id, " +
                        "title     AS book_title, " +
                        "author_id AS author_id, " +
                        "full_name AS author_name " +
                        "FROM books " +
                        "LEFT JOIN authors ON authors.id = books.author_id",
                new BookResultSetExtractor()
        );
    }

    private void mergeBooksInfo(List<Book> booksWithoutGenres, List<Genre> genres,
                                List<BookGenreRelation> relations) {
        // Добавить книгам (booksWithoutGenres) жанры (genres) в соответствии со связями (relations)
        for (Book book: booksWithoutGenres) {
            List<Long> bookGenreIds = relations.stream().filter(bookGenreRelation ->
                    bookGenreRelation.bookId() == book.getId()).map(BookGenreRelation::genreId).toList();
            book.getGenres().addAll(genres.stream().filter(genre -> bookGenreIds.contains(genre.getId())).toList());
        }
    }

    private Book insert(Book book) {

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("title", book.getTitle());
        params.addValue("authorId", book.getAuthor().getId());

        var keyHolder = new GeneratedKeyHolder();

        jdbcOperations.update(
                "INSERT INTO books (title, author_id) VALUES (:title, :authorId)", params, keyHolder);

        Number generatedKey = keyHolder.getKeyAs(Long.class);
        if (generatedKey != null) {
            long id = generatedKey.longValue();
            book.setId(id);
        }
        genreRelationsRepository.insertGenreRelationsForBookId(book.getId(),
                book.getGenres().stream().map(Genre::getId).toList());
        return book;
    }

    private Book update(Book book) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", book.getId());
        params.addValue("title", book.getTitle());
        params.addValue("authorId", book.getAuthor().getId());

        jdbcOperations.update(
                "UPDATE books SET title = :title, author_id = :authorId WHERE id = :id", params);

        genreRelationsRepository.removeGenresRelationsForBookId(book.getId());
        genreRelationsRepository.insertGenreRelationsForBookId(book.getId(),
                book.getGenres().stream().map(Genre::getId).toList());

        return book;
    }

    private static class BookRowMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            return mapRow(rs);
        }

        public Book mapRow(ResultSet rs) throws SQLException {
            return new Book(rs.getLong("book_id"), rs.getString("book_title"),
                    new Author(rs.getLong("author_id"), rs.getString("author_name")), new ArrayList<>());
        }
    }

    @RequiredArgsConstructor
    private static class BookResultSetExtractor implements ResultSetExtractor<List<Book>> {
        @Override
        public List<Book> extractData(ResultSet rs) throws SQLException, DataAccessException {
            List<Book> books = new ArrayList<>();
            while (rs.next()) {
                Book book = new BookRowMapper().mapRow(rs);
                books.add(book);
            }
            return books;
        }
    }

}
