package ru.otus.spring.bookstore.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Mono;
import ru.otus.spring.bookstore.dtos.BookDtoIds;
import ru.otus.spring.bookstore.mappers.DtoMapper;
import ru.otus.spring.bookstore.services.AuthorService;
import ru.otus.spring.bookstore.services.BookService;
import ru.otus.spring.bookstore.services.CommentService;
import ru.otus.spring.bookstore.services.GenreService;

@Controller
public class BookPagesController {

    private final BookService bookService;

    private final AuthorService authorService;

    private final GenreService genreService;

    private final CommentService commentService;

    private final DtoMapper mapper;

    @Autowired
    public BookPagesController(BookService bookService,
                               AuthorService authorService,
                               GenreService genreService,
                               CommentService commentService,
                               DtoMapper mapper) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.genreService = genreService;
        this.commentService = commentService;
        this.mapper = mapper;
    }

    @GetMapping(path = {"/books/list", "/books"})
    public Mono<String> bookList() {
        return Mono.just("book-list-ajax");
    }

    @GetMapping("/books/edit")
    public Mono<String> editPage(@RequestParam("id") long id, Model model) {
        return Mono.defer(() -> {
            if (!(id == 0)) {
                return bookService.findByIdWithDetails(id).map(mapper::bookDtoToBookDtoIds);
            } else {
                return Mono.just(new BookDtoIds());
            }
        }).doOnNext(bookDtoIds -> {
            model.addAttribute("book", bookDtoIds);
            model.addAttribute("authors", authorService.findAll());
            model.addAttribute("genres", genreService.findAll());
            model.addAttribute("comments", commentService.findCommentsByBookId(bookDtoIds.getId()));
        }).thenReturn("book-edit-ajax");

    }

}
