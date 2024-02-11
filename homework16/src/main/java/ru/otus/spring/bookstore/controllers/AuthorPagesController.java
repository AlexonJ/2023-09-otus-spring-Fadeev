package ru.otus.spring.bookstore.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.util.StringUtils;
import ru.otus.spring.bookstore.dtos.AuthorDto;

@Controller
@RequiredArgsConstructor
public class AuthorPagesController {

    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping(path = {"/authors/list", "/authors"})
    public String authorList() {
        return "author-list-ajax";
    }

    @GetMapping("/authors/edit")
    public String editPage(@RequestParam("author_href") String authorHref, Model model) {
        AuthorDto author;
        if (!StringUtils.isEmpty(authorHref)) {
            author = restTemplate.getForObject(authorHref, AuthorDto.class);
        } else {
            author = new AuthorDto();
        }
        model.addAttribute("author", author);
        model.addAttribute("author_href", authorHref);
        return "author-edit-ajax";
    }

}
