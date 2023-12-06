package ru.otus.spring.bookstore.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.spring.bookstore.models.Book;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Component
public class BookConverter {

    private final AuthorConverter authorConverter;

    public String bookToString(Book book) {
        List<String> bookGenres = book.getGenres();
        var genresString = IntStream.range(0, bookGenres.size()).mapToObj(currentIndex ->
                "%d: %s".formatted(currentIndex, bookGenres.get(currentIndex))).collect(Collectors.joining(", "));
        return "Id: %s, title: %s, author: {%s}, genres: [%s]".formatted(
                book.getId(),
                book.getTitle(),
                authorConverter.authorToString(book.getAuthor()),
                genresString);
    }

    public String bookToStringWithComments(Book book) {

        List<String> bookComments = book.getComments();
        var commentsString = IntStream.range(0, bookComments.size()).mapToObj(currentIndex ->
                "%d: %s".formatted(currentIndex, bookComments.get(currentIndex))).collect(Collectors.joining(", "));

        return "Id: %s, title: %s, author: {%s}, comments: [%s]".formatted(
                book.getId(),
                book.getTitle(),
                authorConverter.authorToString(book.getAuthor()),
                commentsString);
    }
}