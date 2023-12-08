package ru.otus.spring.bookstore.repositories;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.bookstore.models.Genre;

import java.util.List;

public interface GenreRepository extends JpaRepository<Genre, Long> {

}
