package ru.otus.spring.bookstore.repositories;

import lombok.NonNull;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import ru.otus.spring.bookstore.models.Comment;

import java.util.List;

public interface CommentRepository extends ReactiveCrudRepository<Comment, Long> {

    String COMMENT_NOT_FOUND_MESSAGE = "Comment with id %d not found";

    @NonNull
    Flux<Comment> findAllByBookId(long id);

    Flux<Comment> findAllByIdIn(List<Long> ids);

}
