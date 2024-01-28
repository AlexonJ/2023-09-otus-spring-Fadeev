package ru.otus.spring.bookstore.services;

import ru.otus.spring.bookstore.dtos.BookDto;

import java.util.List;

public interface BookService {
    BookDto findById(long id);

    List<BookDto> findAll();

    BookDto insert(String title, long authorId, List<Long> genresIds, List<Long> commentIds);

    BookDto update(long id, String title, long authorId, List<Long> genresIds, List<Long> commentIds);

    void deleteById(long id);
}
