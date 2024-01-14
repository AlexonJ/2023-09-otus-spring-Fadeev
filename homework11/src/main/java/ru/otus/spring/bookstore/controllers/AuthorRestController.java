package ru.otus.spring.bookstore.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.bookstore.dtos.AuthorDto;
import ru.otus.spring.bookstore.services.AuthorService;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class AuthorRestController {

    private final AuthorService authorService;

    @GetMapping(path = {"api/authors"})
    public ResponseEntity<Flux<AuthorDto>> getAllAuthorList() {
        return ResponseEntity.ok(authorService.findAll());
    }

    @GetMapping("api/authors/{id}")
    public Mono<ResponseEntity<AuthorDto>> getBook(@PathVariable("id") long id) {
        return authorService.findById(id).map(ResponseEntity::ok);
    }

    @PostMapping("api/authors")
    public Mono<ResponseEntity<Object>> saveAuthor(@Validated @RequestBody Mono<AuthorDto> authorDtoMono) {
        return authorDtoMono
                .flatMap(authorDto ->
                        authorService.update(authorDto.getId(), authorDto.getFullName())
                                .map(updatedAuthorDto -> ResponseEntity.ok((Object)updatedAuthorDto))
                )
                .onErrorResume(WebExchangeBindException.class, ex -> {
                    Map<String, String> errors = new HashMap<>();
                    for (FieldError error : ex.getFieldErrors()) {
                        errors.put(error.getField(), error.getDefaultMessage());
                    }
                    return Mono.just(ResponseEntity.badRequest().body(errors));
                });
    }

    @DeleteMapping("api/authors/{id}")
    public Mono<ResponseEntity<Void>> deleteAuthor(@PathVariable("id") long id) {
        return authorService.findById(id).flatMap(authorDto -> authorService.deleteById(id).thenReturn(authorDto))
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}
