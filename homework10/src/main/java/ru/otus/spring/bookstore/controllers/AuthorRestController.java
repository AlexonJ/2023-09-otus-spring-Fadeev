package ru.otus.spring.bookstore.controllers;

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
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.bookstore.dtos.AuthorDto;
import ru.otus.spring.bookstore.services.AuthorService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class AuthorRestController {

    private final AuthorService authorService;

    @Autowired
    public AuthorRestController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping(path = {"api/authors"})
    public ResponseEntity<List<AuthorDto>> getAllAuthorList() {
        return ResponseEntity.ok(authorService.findAll());
    }

    @GetMapping("api/authors/{id}")
    public ResponseEntity<AuthorDto> getBook(@PathVariable("id") long id) {
        return ResponseEntity.ok(authorService.findById(id));
    }

    @PostMapping("api/authors")
    public ResponseEntity<?> saveAuthor(@Validated @RequestBody AuthorDto authorDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }

        return ResponseEntity.ok(authorService.update(
                authorDto.getId(),
                authorDto.getFullName()));
    }

    @DeleteMapping("api/authors/{id}")
    public ResponseEntity<AuthorDto> deleteAuthor(@PathVariable("id") long id) {
        var authorDto = authorService.findById(id);
        authorService.deleteById(id);
        return ResponseEntity.ok(authorDto);
    }
}
