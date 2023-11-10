package ru.otus.spring.bookstore.repositories;

import ru.otus.spring.bookstore.models.BookGenreRelation;

import java.util.List;

public interface GenreRelationsRepository {
    List<BookGenreRelation> getAllGenreRelations();

    List<BookGenreRelation> getGenreRelationsByBookId(long id);

    void removeGenresRelationsForBookId(long bookId);

    void insertGenreRelationsForBookId(long bookId, List<Long> genreIds);
}
