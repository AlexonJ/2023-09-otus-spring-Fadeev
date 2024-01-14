package ru.otus.spring.bookstore.controllers;

import jakarta.validation.Valid;
import org.h2.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.spring.bookstore.dtos.AuthorDto;
import ru.otus.spring.bookstore.dtos.BookDto;
import ru.otus.spring.bookstore.dtos.BookDtoIds;
import ru.otus.spring.bookstore.dtos.CommentDto;
import ru.otus.spring.bookstore.dtos.GenreDto;
import ru.otus.spring.bookstore.mappers.DtoMapper;
import ru.otus.spring.bookstore.services.AuthorService;
import ru.otus.spring.bookstore.services.BookService;
import ru.otus.spring.bookstore.services.CommentService;
import ru.otus.spring.bookstore.services.GenreService;

import java.util.List;

@Controller
public class BookController {

    private final BookService bookService;

    private final AuthorService authorService;

    private final GenreService genreService;

    private final CommentService commentService;

    private final DtoMapper mapper;

    @Autowired
    public BookController(BookService bookService,
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
    public String bookList(Model model) {
        List<BookDto> booksDto = bookService.findAll();
        model.addAttribute("books", booksDto);
        return "book-list";
    }

    @GetMapping("/books/edit")
    public String editPage(@RequestParam("id") long id, Model model) {
        BookDtoIds book;
        if (!(id == 0)) {
            book = mapper.bookDtoToBookDtoIds(bookService.findById(id));
        } else {
            book = new BookDtoIds();
        }
        model.addAttribute("book", book);
        fillDataInModel(model, id);
        return "book-edit";
    }

    @PostMapping("/books/edit")
    public String saveBook(@Valid @ModelAttribute("book") BookDtoIds book,
                           BindingResult bindingResult,
                           @RequestParam("newCommentContent") String newCommentContent,
                           Model model
                           ) {
        if (bindingResult.hasErrors()) {
            fillDataInModel(model, book.getId());
            return "book-edit";
        }

        var savedBookId = bookService.update(book.getId(), book.getTitle(), book.getAuthorId(),
                book.getGenreIds(), book.getCommentIds()).getId();

        if (!StringUtils.isNullOrEmpty(newCommentContent)) {
            commentService.insert(savedBookId, newCommentContent);
        }

        return "redirect:/books";
    }

    @GetMapping("/books/delete")
    //TODO: не нашел возможности вызывать метод DELETE из HTML, поэтому использовал GET
    public String deleteBook(@RequestParam("id") long id) {
        bookService.deleteById(id);
        return "redirect:/books";
    }

    private void fillDataInModel(Model model, long bookId) {
        List<AuthorDto> authors = authorService.findAll();
        List<GenreDto> genres = genreService.findAll();
        List<CommentDto> comments = commentService.findCommentsByBookId(bookId);
        model.addAttribute("authors", authors);
        model.addAttribute("genres", genres);
        model.addAttribute("comments", comments);

    }
}
