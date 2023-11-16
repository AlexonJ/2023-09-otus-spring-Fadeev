package ru.otus.spring.bookstore.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.spring.bookstore.models.Genre;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class GenreRepositoryJpa implements GenreRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public List<Genre> findAll() {
        return entityManager.createQuery("SELECT genre FROM Genre genre", Genre.class).getResultList();
    }

    @Override
    public List<Genre> findAllByIds(List<Long> ids) {
        return entityManager.createQuery("SELECT genre FROM Genre genre WHERE genre.id IN (:ids)", Genre.class)
                .setParameter("ids", ids).getResultList();
    }
}
