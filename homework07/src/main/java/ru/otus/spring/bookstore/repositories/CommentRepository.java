package ru.otus.spring.bookstore.repositories;

import lombok.NonNull;
import org.springframework.data.repository.CrudRepository;
import ru.otus.spring.bookstore.models.Comment;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, Long> {

    @NonNull
    List<Comment> findAllByBookId(long id);

}
