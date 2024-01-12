package ru.otus.spring.bookstore.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.bookstore.dtos.BookDto;
import ru.otus.spring.bookstore.exceptions.EntityNotFoundException;
import ru.otus.spring.bookstore.mappers.DtoMapper;
import ru.otus.spring.bookstore.models.Author;
import ru.otus.spring.bookstore.models.Book;
import ru.otus.spring.bookstore.models.Comment;
import ru.otus.spring.bookstore.models.Genre;
import ru.otus.spring.bookstore.repositories.AuthorRepository;
import ru.otus.spring.bookstore.repositories.BookRepository;
import ru.otus.spring.bookstore.repositories.BookRepositoryEnhanced;
import ru.otus.spring.bookstore.repositories.CommentRepository;
import ru.otus.spring.bookstore.repositories.GenreRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final BookRepositoryEnhanced bookRepositoryEnhanced;

    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    private final CommentRepository commentRepository;

    private final DtoMapper mapper;

    @Override
    public Mono<BookDto> findByIdWithDetails(Long id) {
        return bookRepositoryEnhanced.findByIdWithDetails(id).map(mapper::bookToBookDTO)
                .switchIfEmpty(Mono.error(() -> new EntityNotFoundException(BOOK_NOT_FOUND_MESSAGE.formatted(id))));
    }

    @Override
    public Flux<BookDto> findAllWithDetails() {
        return bookRepositoryEnhanced.findAllWithDetails().map(mapper::bookToBookDTO);
    }

    @Override
    public Mono<BookDto> findById(long id) {
        return bookRepository.findById(id).map(mapper::bookToBookDTO)
                .switchIfEmpty(Mono.error(() -> new EntityNotFoundException(BOOK_NOT_FOUND_MESSAGE.formatted(id))));
    }

    @Override
    public Flux<BookDto> findAll() {
        return bookRepository.findAll().map(mapper::bookToBookDTO);
    }

    @Override
    public Mono<Void> deleteById(long id) {
        return bookRepository.deleteById(id);
    }

    public Mono<BookDto> save(long id, String title, long authorId, List<Long> genresIds, List<Long> commentIds) {
        Mono<Author> authorMono = Mono.defer(() -> {
            if (authorId != 0) {
                return authorRepository.findById(authorId)
                        .switchIfEmpty(Mono.error(() ->
                                new EntityNotFoundException("Author with id %d not found".formatted(authorId))));
            } else {
                return Mono.empty();
            }
        });

        Flux<Genre> genresFlux = Flux.defer(() -> {
            if (!genresIds.isEmpty()) {
                return genreRepository.findAllByIdIn(genresIds)
                        .switchIfEmpty(Mono.error(() ->
                                new EntityNotFoundException("Genres with ids %s not found".formatted(genresIds))));
            } else {
                return Flux.empty();
            }
        });

        Flux<Comment> commentsFlux = Flux.defer(() -> {
            if (!commentIds.isEmpty()) {
                return commentRepository.findAllByIdIn(commentIds)
                        .switchIfEmpty(Mono.error(() ->
                                new EntityNotFoundException("Comments with ids %s not found".formatted(commentIds))));
            } else {
                return Flux.empty();
            }
        });

        return Mono.zip(authorMono, genresFlux.collectList(), commentsFlux.collectList())
                .flatMap(objects -> {
                    Author author = objects.getT1();
                    List<Genre> genres = objects.getT2();
                    List<Comment> comments = objects.getT3();
                    var book = new Book(id, title, author, genres, comments);
                    return bookRepositoryEnhanced.save(book);
                }).map(mapper::bookToBookDTO);
    }
}