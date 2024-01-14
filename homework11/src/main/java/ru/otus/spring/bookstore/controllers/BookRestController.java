package ru.otus.spring.bookstore.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.thymeleaf.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.bookstore.dtos.BookDto;
import ru.otus.spring.bookstore.dtos.BookDtoIds;
import ru.otus.spring.bookstore.services.BookService;
import ru.otus.spring.bookstore.services.CommentService;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class BookRestController {

    private final BookService bookService;

    private final CommentService commentService;

    @GetMapping("api/books")
    public ResponseEntity<Flux<BookDto>> getAllBookList() {
        return ResponseEntity.ok(bookService.findAllWithDetails());
    }

    @GetMapping("api/books/{id}")
    public Mono<ResponseEntity<BookDto>> getBook(@PathVariable("id") long id) {
        return bookService.findByIdWithDetails(id).map(ResponseEntity::ok);
    }

    @PostMapping(value = "api/books", consumes = "application/json", produces = "application/json")
    public Mono<ResponseEntity<Object>> saveBook(@Validated @RequestBody Mono<BookDtoIds> bookMonoDto,
                                            @RequestParam(name = "newCommentContent", required = false)
                                            String newCommentContent) {
        return bookMonoDto.zipWith(Mono.just(StringUtils.isEmptyOrWhitespace(
                newCommentContent) ? "" : newCommentContent))
                .flatMap(objects -> {
                    var bookDtoIds = objects.getT1();
                    var commentContent = objects.getT2();
                    return bookService.save(bookDtoIds.getId(), bookDtoIds.getTitle(), bookDtoIds.getAuthorId(),
                            bookDtoIds.getGenreIds(), bookDtoIds.getCommentIds())
                            .map(bookDto -> {
                                if (!StringUtils.isEmptyOrWhitespace(commentContent)) {
                                    return commentService.insert(bookDtoIds.getId(), commentContent);
                                }
                                return bookDto;
                            })
                            .map(ResponseEntity::ok);
                })
                .onErrorResume(WebExchangeBindException.class, ex -> {
                    Map<String, String> errors = new HashMap<>();
                    for (FieldError error : ex.getFieldErrors()) {
                        errors.put(error.getField(), error.getDefaultMessage());
                    }
                    return Mono.just(ResponseEntity.badRequest().body(errors));
                });
    }

    @DeleteMapping("api/books/{id}")
    public Mono<ResponseEntity<Void>> deleteBook(@PathVariable("id") long id) {
        return bookService.findById(id)
                .flatMap(bookDto -> bookService.deleteById(bookDto.getId()).thenReturn(bookDto))
                .thenReturn(ResponseEntity.noContent().build());
    }
}
