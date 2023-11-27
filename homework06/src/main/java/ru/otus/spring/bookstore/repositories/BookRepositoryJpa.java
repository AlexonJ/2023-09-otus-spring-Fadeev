package ru.otus.spring.bookstore.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.spring.bookstore.models.Book;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BookRepositoryJpa implements BookRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public Optional<Book> findById(long id) {
        try {
            return Optional.of(entityManager.createQuery(
                            "SELECT book FROM Book book " +
                               "LEFT JOIN FETCH book.genres " +
                               "LEFT JOIN FETCH book.author " +
                               "WHERE book.id = :id ", Book.class)
                    .setParameter("id", id)
                    .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Book> findAll() {
        return entityManager.createQuery(
                "SELECT book FROM Book book LEFT JOIN FETCH book.genres LEFT JOIN FETCH book.author", Book.class)
                .getResultList();
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == 0) {
            entityManager.persist(book);
            return book;
        }
        return entityManager.merge(book);
    }

    @Override
    public void deleteById(long id) {
        Book book = findById(id).orElseThrow(EntityNotFoundException::new);
        entityManager.remove(book);
    }
}
