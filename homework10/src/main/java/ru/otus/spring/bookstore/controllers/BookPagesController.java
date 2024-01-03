package ru.otus.spring.bookstore.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public String bookList() {
        return "book-list-ajax";
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
        fillDataInModel(model, book.getId());
        return "book-edit-ajax";
    }

//    @PostMapping("/books/edit")
//    public String saveBook(@Valid @ModelAttribute("book") BookDtoIds book,
//                           BindingResult bindingResult,
//                           @RequestParam(name = "newCommentContent", required = false) String newCommentContent,
//                           Model model) {
//        if (bindingResult.hasErrors()) {
//            fillDataInModel(model, book.getId());
//            return "book-edit-ajax";
//        }
//
//        var savedBookId = bookService.update(book.getId(), book.getTitle(), book.getAuthorId(),
//                book.getGenreIds(), book.getCommentIds()).getId();
//
//        if (!StringUtils.isNullOrEmpty(newCommentContent)) {
//            commentService.insert(savedBookId, newCommentContent);
//        }
//
//        return "redirect:/books";
//    }

    private void fillDataInModel(Model model, long bookId) {
        model.addAttribute("authors", authorService.findAll());
        model.addAttribute("genres", genreService.findAll());
        model.addAttribute("comments", commentService.findCommentsByBookId(bookId));
    }
}
