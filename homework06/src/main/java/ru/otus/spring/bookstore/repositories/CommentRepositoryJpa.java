package ru.otus.spring.bookstore.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.spring.bookstore.models.Comment;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryJpa implements CommentRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public Optional<Comment> findById(long id) {
        return Optional.ofNullable(entityManager.find(Comment.class, id));
    }

//    @Override
//    public List<Comment> findAllByBookId(long id) {
//        return entityManager.createQuery(
//                "SELECT comment FROM Comment AS comment WHERE comment.book.id= :id", Comment.class)
//                .setParameter("id", id).getResultList();
//    }

    @Override
    public Comment save(Comment comment) {
        if (comment.getId() == 0) {
            entityManager.persist(comment);
            return comment;
        }
        return entityManager.merge(comment);
    }

    @Override
    public void deleteById(long id) {
        Comment comment = findById(id).orElseThrow(EntityNotFoundException::new);
        entityManager.remove(comment);
    }

}
