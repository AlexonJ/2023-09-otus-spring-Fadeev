package ru.otus.spring.bookstore.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.spring.bookstore.converters.BookConverter;
import ru.otus.spring.bookstore.services.BookService;
import ru.otus.spring.bookstore.services.CommentService;

@RequiredArgsConstructor
@ShellComponent
public class CommentCommand {

    private final CommentService commentService;

    private final BookConverter bookConverter;

    private final BookService bookService;

    @ShellMethod(value = "Find comments for the book", key = "cfbi")
    public String findCommentsForBook(String id) {
        return bookService.findById(id).map(book -> {
                    book.setComments(commentService.findCommentsByBookId(book.getId()));
                    return book;
                })
                .map(bookConverter::bookToStringWithComments)
                .orElse("Book with id %s not found".formatted(id));
    }

    @ShellMethod(value = "Find comments by book id and comment index", key = "cfid")
    public String findCommentsByBookIdAndIndex(String bookId, int index) {
        return commentService.findCommentByBookIdAndIndex(bookId, index)
                .orElse("Comment for book id %s with index %d not found".formatted(bookId, index));
    }

    @ShellMethod(value = "Insert new comment for book", key = "cins")
    public String insertComment(String bookId, String content) {
        commentService.add(bookId, content);
        return "Comment %s for book %s was added".formatted(content, bookId);
    }

    @ShellMethod(value = "Update existing comment by id", key = "cupd")
    public String updateComment(String bookId, int index, String content) {
        commentService.updateByBookIdAndIndex(bookId, index, content);
        return "Comment for book %s with index %d was updated".formatted(bookId, index);
    }

    @ShellMethod(value = "Delete comment by id", key = "cdel")
    public String deleteComment(String bookId, int index) {
        commentService.deleteByBookIdAndIndex(bookId, index);
        return "Comment for book %s with index %d was deleted".formatted(bookId, index);
    }

}
