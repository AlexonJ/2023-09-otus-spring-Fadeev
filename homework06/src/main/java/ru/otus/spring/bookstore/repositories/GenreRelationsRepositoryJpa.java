package ru.otus.spring.bookstore.repositories;

import ru.otus.spring.bookstore.models.BookGenreRelation;

import java.util.List;

public class GenreRelationsRepositoryJpa implements GenreRelationsRepository {
    @Override
    public List<BookGenreRelation> getAllGenreRelations() {
        return null;
    }

    @Override
    public List<BookGenreRelation> getGenreRelationsByBookId(long id) {
        return null;
    }

    @Override
    public void removeGenresRelationsForBookId(long bookId) {

    }

    @Override
    public void insertGenreRelationsForBookId(long bookId, List<Long> genreIds) {

    }
}
