package ru.otus.spring.bookstore.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.spring.bookstore.converters.BookConverter;
import ru.otus.spring.bookstore.converters.CommentConverter;
import ru.otus.spring.bookstore.dtos.CommentDto;
import ru.otus.spring.bookstore.services.BookService;
import ru.otus.spring.bookstore.services.CommentService;

@RequiredArgsConstructor
@ShellComponent
public class CommentCommand {

    private final CommentService commentService;

    private final CommentConverter commentConverter;

    private final BookConverter bookConverter;

    private final BookService bookService;

    @ShellMethod(value = "Find comments for the book", key = "cfbi")
    public String findCommentsForBook(long id) {
        return bookService.findById(id).map(book -> {
                    book.setComments(commentService.findCommentsByBookId(book.getId()));
                    return book;
                })
                .map(bookConverter::bookToStringWithComments)
                .orElse("Book with id %d not found".formatted(id));
    }

    @ShellMethod(value = "Find comments by id", key = "cfid")
    public String findComment(long id) {
        return commentService.findById(id)
                .map(commentConverter::commentToString)
                .orElse("Comment with id %d not found".formatted(id));
    }

    @ShellMethod(value = "Insert new comment for book", key = "cins")
    public String insertComment(long bookId, String content) {
        CommentDto commentDto = commentService.insert(bookId, content);
        return commentConverter.commentToString(commentDto);
    }

    @ShellMethod(value = "Update existing comment by id", key = "cupd")
    public String updateComment(long id, String content) {
        CommentDto commentDto = commentService.updateById(id, content);
        return commentConverter.commentToString(commentDto);
    }

    @ShellMethod(value = "Delete comment by id", key = "cdel")
    public String deleteComment(long id) {
        commentService.deleteById(id);
        return "Comment with id %s was deleted".formatted(id);
    }

}
