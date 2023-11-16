package ru.otus.spring.bookstore.converters;

import org.springframework.stereotype.Component;
import ru.otus.spring.bookstore.dtos.AuthorDto;

@Component
public class AuthorConverter {
    public String authorToString(AuthorDto authorDto) {
        return "Id: %d, FullName: %s".formatted(authorDto.getId(), authorDto.getFullName());
    }
}
