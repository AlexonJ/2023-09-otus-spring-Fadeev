package ru.otus.spring.bookstore.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.bookstore.dtos.CommentDto;
import ru.otus.spring.bookstore.exceptions.EntityNotFoundException;
import ru.otus.spring.bookstore.mappers.DtoMapper;
import ru.otus.spring.bookstore.models.Book;
import ru.otus.spring.bookstore.models.Comment;
import ru.otus.spring.bookstore.repositories.BookRepository;
import ru.otus.spring.bookstore.repositories.CommentRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final BookRepository bookRepository;

    private final DtoMapper mapper;

    @Transactional
    @Override
    public List<CommentDto> findCommentsByBookId(long id) {
        var book = bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Book with id %d not found".formatted(id)));
        List<Comment> comments = book.getComments();
        return comments.stream().map(mapper::commentToCommentDto).toList();
    }

    @Override
    public Optional<CommentDto> findById(long id) {
        return commentRepository.findById(id).map(mapper::commentToCommentDto);
    }

    @Override
    @Transactional
    public CommentDto insert(long bookId, String content) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book with id %d not found".formatted(bookId)));
        Comment comment = new Comment(0, content, book);
        return mapper.commentToCommentDto(commentRepository.save(comment));
    }

    @Override
    @Transactional
    public CommentDto updateById(long id, String content) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comment with id %d not found".formatted(id)));
        comment.setContent(content);
        return mapper.commentToCommentDto(commentRepository.save(comment));
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        commentRepository.deleteById(id);
    }

}
