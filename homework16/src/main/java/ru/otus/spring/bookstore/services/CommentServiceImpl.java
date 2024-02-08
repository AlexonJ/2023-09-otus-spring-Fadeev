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

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private static final String COMMENT_NOT_FOUND_MESSAGE = "Comment with id %d not found";

    private static final String BOOK_NOT_FOUND_MESSAGE = "Book with id %d not found";

    private final CommentRepository commentRepository;

    private final BookRepository bookRepository;

    private final DtoMapper mapper;

    @Override
    public List<CommentDto> findCommentsByBookId(long id) {
         List<Comment> comments = commentRepository.findAllByBookId(id);
         return comments.stream().map(mapper::commentToCommentDto).toList();
    }

    @Override
    public CommentDto findById(long id) {
        return commentRepository.findById(id).map(mapper::commentToCommentDto)
                .orElseThrow(() -> new EntityNotFoundException(COMMENT_NOT_FOUND_MESSAGE.formatted(id)));
    }

    @Transactional
    @Override
    public CommentDto insert(long bookId, String content) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException(BOOK_NOT_FOUND_MESSAGE.formatted(bookId)));
        Comment comment = new Comment(0, content, book);
        return mapper.commentToCommentDto(commentRepository.save(comment));
    }

    @Override
    public CommentDto updateById(long id, String content) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(COMMENT_NOT_FOUND_MESSAGE.formatted(id)));
        comment.setContent(content);
        return mapper.commentToCommentDto(commentRepository.save(comment));
    }

    @Override
    public void deleteById(long id) {
        commentRepository.deleteById(id);
    }

}
