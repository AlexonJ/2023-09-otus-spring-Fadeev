package ru.otus.spring.bookstore.services;

import ru.otus.spring.bookstore.dtos.AuthorDto;

import java.util.List;

public interface AuthorService {
    List<AuthorDto> findAll();
}
