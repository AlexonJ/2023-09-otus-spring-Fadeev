package ru.otus.spring.finalproject.easydesk.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.finalproject.easydesk.dtos.comments.CommentDto;
import ru.otus.spring.finalproject.easydesk.exceptions.CommonValidationException;
import ru.otus.spring.finalproject.easydesk.exceptions.ErrorMessages;
import ru.otus.spring.finalproject.easydesk.mappers.DtoMapper;
import ru.otus.spring.finalproject.easydesk.models.db.Comment;
import ru.otus.spring.finalproject.easydesk.repositories.CommentRepository;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final TicketService ticketService;

    private final SecurityService securityService;

    private final DtoMapper mapper;

    @Override
    public List<CommentDto> getByTicketCode(String ticketCode) {
        var ticket = ticketService.getByCodeChecked(ticketCode);
        return commentRepository.findAllByTicket(ticket).map(mapper::commentToCommentDto).toList();
    }

    @Override
    public CommentDto createByTicketCode(String commentText, String ticketCode) {

        var ticket = ticketService.getByCodeChecked(ticketCode);

        var comment = new Comment(0, LocalDateTime.now(), securityService.getCurrentUser(), commentText, ticket);
        return mapper.commentToCommentDto(commentRepository.save(comment));

    }

    @Override
    public CommentDto updateById(String content, Long id) {
        var comment = findByIdChecked(id);
        comment.setContent(content);
        return mapper.commentToCommentDto(commentRepository.save(comment));
    }

    @Override
    public void deleteById(Long id) {
        var comment = findByIdChecked(id);
        commentRepository.delete(comment);
    }

    @Override
    public Comment findByIdChecked(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() ->
                new CommonValidationException(
                        ErrorMessages.COMMENT_NOT_FOUND_BY_ID.getCode(),
                        ErrorMessages.COMMENT_NOT_FOUND_BY_ID.getMessage()));
    }
}
