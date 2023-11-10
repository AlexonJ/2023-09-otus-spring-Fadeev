package ru.otus.spring.bookstore.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.spring.bookstore.models.BookGenreRelation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class GenreRelationsRepositoryJdbc implements GenreRelationsRepository {

    private final NamedParameterJdbcOperations jdbcOperations;

    @Override
    public List<BookGenreRelation> getAllGenreRelations() {
        return jdbcOperations.query("SELECT book_id, genre_id FROM books_genres",
                (rs, rowNum) -> new BookGenreRelation(rs.getLong("book_id"), rs.getLong("genre_id")));
    }

    @Override
    public List<BookGenreRelation> getGenreRelationsByBookId(long id) {
        Map<String, Object> params = Collections.singletonMap("book_id", id);
        return jdbcOperations.query("SELECT book_id, genre_id FROM books_genres WHERE book_id=:book_id",
                params, (rs, rowNum) -> new BookGenreRelation(rs.getLong("book_id"), rs.getLong("genre_id")));
    }

    @Override
    public void removeGenresRelationsForBookId(long bookId) {
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("bookId", bookId);
        jdbcOperations.update("DELETE books_genres WHERE book_id = :bookId", params);
    }

    @Override
    public void insertGenreRelationsForBookId(long bookId, List<Long> genreIds) {

        List<MapSqlParameterSource> batchParameters = new ArrayList<>();

        for (Long genreId : genreIds) {
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("bookId", bookId);
            parameters.addValue("genreId", genreId);
            batchParameters.add(parameters);
        }

        jdbcOperations.batchUpdate(
                "INSERT INTO books_genres (book_id, genre_id) VALUES (:bookId, :genreId)",
                batchParameters.toArray(MapSqlParameterSource[]::new));

    }
}
