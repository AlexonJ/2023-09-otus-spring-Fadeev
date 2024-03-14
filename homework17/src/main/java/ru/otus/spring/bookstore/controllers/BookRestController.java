package ru.otus.spring.bookstore.controllers;

import org.h2.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.bookstore.dtos.BookDto;
import ru.otus.spring.bookstore.dtos.BookDtoIds;
import ru.otus.spring.bookstore.services.BookService;
import ru.otus.spring.bookstore.services.CommentService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class BookRestController {

    private final BookService bookService;

    private final CommentService commentService;


    @Autowired
    public BookRestController(BookService bookService,
                              CommentService commentService) {
        this.bookService = bookService;
        this.commentService = commentService;
    }

    @GetMapping("api/books")
    public ResponseEntity<List<BookDto>> getAllBookList() {
        return ResponseEntity.ok(bookService.findAll());
    }

    @GetMapping("api/books/{id}")
    public ResponseEntity<BookDto> getBook(@PathVariable("id") long id) {
        return ResponseEntity.ok(bookService.findById(id));
    }

    @PostMapping(value = "api/books")
    public ResponseEntity<?> saveBook(@Validated @RequestBody BookDtoIds book,
                                      BindingResult bindingResult,
                                      @RequestParam(name = "newCommentContent", required = false)
                                          String newCommentContent) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }

        var savedBook = bookService.update(book.getId(), book.getTitle(), book.getAuthorId(),
                book.getGenreIds(), book.getCommentIds());

        if (!StringUtils.isNullOrEmpty(newCommentContent)) {
            commentService.insert(savedBook.getId(), newCommentContent);
        }

        return ResponseEntity.ok().body(savedBook);

    }

    @DeleteMapping("api/books/{id}")
    public ResponseEntity<BookDto> deleteBook(@PathVariable("id") long id) {
        var bookDto = bookService.findById(id);
        bookService.deleteById(id);
        return ResponseEntity.ok(bookDto);
    }
}
