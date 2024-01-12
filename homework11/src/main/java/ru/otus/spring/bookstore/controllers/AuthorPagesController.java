package ru.otus.spring.bookstore.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.spring.bookstore.dtos.AuthorDto;
import ru.otus.spring.bookstore.services.AuthorService;

@Controller
public class AuthorPagesController {

    private final AuthorService authorService;

    @Autowired
    public AuthorPagesController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping(path = {"/authors/list", "/authors"})
    public String authorList() {
        return "author-list-ajax";
    }

    @GetMapping("/authors/edit")
    public String editPage(@RequestParam("id") long id, Model model) {
        AuthorDto author;
        if (!(id == 0)) {
            author = authorService.findById(id).block();
        } else {
            author = new AuthorDto();
        }
        model.addAttribute("author", author);
        return "author-edit-ajax";
    }

}
