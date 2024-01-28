package ru.otus.spring.bookstore.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import ru.otus.spring.bookstore.dtos.AuthorDto;
import ru.otus.spring.bookstore.exceptions.EntityNotFoundException;
import ru.otus.spring.bookstore.mappers.DtoMapper;
import ru.otus.spring.bookstore.models.Author;
import ru.otus.spring.bookstore.repositories.AuthorRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    private final DtoMapper mapper;

    @PreAuthorize("{hasAuthority('READ')} && {hasAuthority('AUTHORS_ACCESS')}")
    @Override
    public List<AuthorDto> findAll() {
        return authorRepository.findAll().stream().map(mapper::authorToAuthorDto).toList();
    }

    @PreAuthorize("{hasAuthority('READ')} && {hasAuthority('AUTHORS_ACCESS')}")
    @Override
    public AuthorDto findById(long id) {
        return authorRepository.findById(id).map(mapper::authorToAuthorDto)
                .orElseThrow(() -> new EntityNotFoundException("Author with id %d not found".formatted(id)));
    }

    @PreAuthorize("{hasAuthority('WRITE')} && {hasAuthority('AUTHORS_ACCESS')}")
    @Override
    public AuthorDto insert(String fullName) {
        return mapper.authorToAuthorDto(save(0, fullName));
    }

    @PreAuthorize("{hasAuthority('WRITE')} && {hasAuthority('AUTHORS_ACCESS')}")
    @Override
    public AuthorDto update(long id, String fullName) {
        return mapper.authorToAuthorDto(save(id, fullName));
    }

    @PreAuthorize("{hasAuthority('DELETE')} && {hasAuthority('AUTHORS_ACCESS')}")
    @Override
    public void deleteById(long id) {
        authorRepository.deleteById(id);
    }

    private Author save(long id, String fullName) {
        var author = new Author(id, fullName);
        return authorRepository.save(author);
    }
}
