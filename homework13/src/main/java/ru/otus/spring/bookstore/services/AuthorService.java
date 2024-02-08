package ru.otus.spring.bookstore.services;

import ru.otus.spring.bookstore.dtos.AuthorDto;

import java.util.List;

public interface AuthorService {
    List<AuthorDto> findAll();

    AuthorDto findById(long id);

    AuthorDto insert(String fullName);

    AuthorDto update(long id, String fullName);

    void deleteById(long id);

}
