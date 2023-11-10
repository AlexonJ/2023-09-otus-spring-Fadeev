package ru.otus.spring.bookstore.converters;

import org.springframework.stereotype.Component;
import ru.otus.spring.bookstore.models.Genre;

@Component
public class GenreConverter {
    public String genreToString(Genre genre) {
        return "Id: %d, Name: %s".formatted(genre.getId(), genre.getName());
    }
}
