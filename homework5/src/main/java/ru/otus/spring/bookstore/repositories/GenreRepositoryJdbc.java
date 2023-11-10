package ru.otus.spring.bookstore.repositories;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.spring.bookstore.models.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@AllArgsConstructor
public class GenreRepositoryJdbc implements GenreRepository {

    private final NamedParameterJdbcOperations jdbcOperations;

    @Override
    public List<Genre> findAll() {
        return jdbcOperations.query("SELECT id, name FROM genres", new GenreRowMapper());
    }

    @Override
    public List<Genre> findAllByIds(List<Long> ids) {
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("ids", ids);
        return jdbcOperations.query("SELECT id, name FROM genres WHERE id IN (:ids)", params, new GenreRowMapper());
    }

    private static class GenreRowMapper implements RowMapper<Genre> {

        @Override
        public Genre mapRow(ResultSet rs, int i) throws SQLException {
            return new Genre(rs.getLong("id"), rs.getString("name"));
        }
    }
}
