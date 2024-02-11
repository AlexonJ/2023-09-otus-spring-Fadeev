package ru.otus.spring.bookstore.converters;

import org.springframework.stereotype.Component;
import ru.otus.spring.bookstore.dtos.GenreDto;

@Component
public class GenreConverter {
    public String genreToString(GenreDto genreDto) {
        return "Id: %d, Name: %s".formatted(genreDto.getId(), genreDto.getName());
    }
}
