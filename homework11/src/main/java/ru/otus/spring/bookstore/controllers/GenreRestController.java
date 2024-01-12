package ru.otus.spring.bookstore.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.otus.spring.bookstore.dtos.GenreDto;
import ru.otus.spring.bookstore.services.GenreService;

@RestController
public class GenreRestController {

    private final GenreService genreService;

    @Autowired
    public GenreRestController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping(path = {"api/genres"})
    public ResponseEntity<Flux<GenreDto>> getAllAuthorList() {
        return ResponseEntity.ok(genreService.findAll());
    }

}
