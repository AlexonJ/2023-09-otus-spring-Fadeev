package ru.otus.spring.bookstore.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.otus.spring.bookstore.models.relational.Author;
import ru.otus.spring.bookstore.models.relational.Book;
import ru.otus.spring.bookstore.models.relational.Comment;
import ru.otus.spring.bookstore.models.relational.Genre;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface EntityMapper {

    @Mapping(target = "id", ignore = true)
    ru.otus.spring.bookstore.models.nonrelational.Author authorToAuthorNonRelational(Author author);

    @Mapping(target = "comments", expression = "java(extractComments(book.getComments()))")
    @Mapping(target = "genres", expression = "java(extractGenres(book.getGenres()))")
    @Mapping(target = "id", ignore = true)
    ru.otus.spring.bookstore.models.nonrelational.Book bookToBookNonRelational(Book book);

    default List<String> extractGenres(List<Genre> genres) {
        return genres.stream()
                .map(Genre::getName)
                .collect(Collectors.toList());
    }

    default List<String> extractComments(List<Comment> comments) {
        return comments.stream()
                .map(Comment::getContent)
                .collect(Collectors.toList());
    }
}
