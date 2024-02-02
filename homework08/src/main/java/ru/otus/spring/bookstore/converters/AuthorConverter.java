package ru.otus.spring.bookstore.converters;

import org.springframework.stereotype.Component;
import ru.otus.spring.bookstore.models.Author;

@Component
public class AuthorConverter {
    public String authorToString(Author author) {
        return "Id: %s, FullName: %s".formatted(author.getId(), author.getFullName());
    }
}