package ru.otus.spring.bookstore.converters;

import org.springframework.stereotype.Component;
import ru.otus.spring.bookstore.dtos.CommentDto;

@Component
public class CommentConverter {
    public String commentToString(CommentDto commentDto) {
        return "Id: %d, Content: %s".formatted(commentDto.getId(), commentDto.getContent());
    }
}
