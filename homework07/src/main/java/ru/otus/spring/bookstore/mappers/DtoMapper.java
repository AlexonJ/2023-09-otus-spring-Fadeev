package ru.otus.spring.bookstore.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.otus.spring.bookstore.dtos.AuthorDto;
import ru.otus.spring.bookstore.dtos.CommentDto;
import ru.otus.spring.bookstore.dtos.GenreDto;
import ru.otus.spring.bookstore.models.Author;
import ru.otus.spring.bookstore.models.Book;
import ru.otus.spring.bookstore.dtos.BookDto;
import ru.otus.spring.bookstore.models.Comment;
import ru.otus.spring.bookstore.models.Genre;

@Mapper(componentModel = "spring")
public interface DtoMapper {

        @Mapping(target = "comments", ignore = true)
        BookDto bookToBookDTO(final Book book);

        AuthorDto authorToAuthorDto(final Author author);

        GenreDto genreToGenreDto(final Genre genre);

        CommentDto commentToCommentDto(final Comment comment);

}
