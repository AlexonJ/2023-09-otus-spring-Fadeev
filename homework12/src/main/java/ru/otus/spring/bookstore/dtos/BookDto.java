package ru.otus.spring.bookstore.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {

    private long id;

    private String title;

    private AuthorDto author;

    private List<GenreDto> genres;

    private List<CommentDto> comments;

    public String getCommentsAsString() {
        if (Objects.isNull(comments)) {
            return "";
        }
        return comments.stream().map(CommentDto::getContent).collect(Collectors.joining(", "));
    }

    public String getGenresAsString() {
        if (Objects.isNull(genres)) {
            return "";
        }
        return genres.stream().map(GenreDto::getName).collect(Collectors.joining(", "));
    }

}
