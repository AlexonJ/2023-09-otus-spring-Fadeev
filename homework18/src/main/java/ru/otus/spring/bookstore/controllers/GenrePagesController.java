package ru.otus.spring.bookstore.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GenrePagesController {

    @GetMapping(path = {"/genres/list", "/genres"})
    public String genreList() {
        return "genre-list-ajax";
    }

}
