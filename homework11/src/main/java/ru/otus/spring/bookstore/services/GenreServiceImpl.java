package ru.otus.spring.bookstore.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import ru.otus.spring.bookstore.dtos.GenreDto;
import ru.otus.spring.bookstore.mappers.DtoMapper;
import ru.otus.spring.bookstore.repositories.GenreRepository;

@RequiredArgsConstructor
@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    private final DtoMapper mapper;

    @Override
    public Flux<GenreDto> findAll() {
        return genreRepository.findAll().map(mapper::genreToGenreDto);
    }
}
