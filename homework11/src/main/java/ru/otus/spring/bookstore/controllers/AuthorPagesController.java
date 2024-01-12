package ru.otus.spring.bookstore.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Mono;
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
    public Mono<String> authorList() {
        return Mono.just("author-list-ajax");
    }

    @GetMapping("/authors/edit")
    public Mono<String> editPage(@RequestParam("id") long id, Model model) {
        return Mono.defer(() -> {
            if (!(id == 0)) {
                return authorService.findById(id);
            } else {
                return Mono.just(new AuthorDto());
            }
        }).doOnNext(updatedAuthorDto -> {
                    model.addAttribute("author", updatedAuthorDto);
                }
        ).thenReturn("author-edit-ajax");

    }

}
