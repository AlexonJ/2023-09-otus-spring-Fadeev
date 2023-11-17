package ru.otus.spring.bookstore.repositories;

import lombok.NonNull;
import org.springframework.data.repository.CrudRepository;
import ru.otus.spring.bookstore.models.Genre;

import java.util.List;

public interface GenreRepository extends CrudRepository<Genre, Long> {

    @NonNull
    List<Genre> findAll();

    List<Genre> findAllByIdIn(List<Long> ids);

}
