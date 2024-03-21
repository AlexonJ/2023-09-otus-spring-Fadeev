package ru.otus.spring.finalproject.easydesk.services;

import ru.otus.spring.finalproject.easydesk.dtos.comments.CommentDto;
import ru.otus.spring.finalproject.easydesk.models.db.Comment;

import java.util.List;

public interface CommentService {
    List<CommentDto> getByTicketCode(String ticketCode);

    CommentDto create(String commentText, String ticketCode);

    CommentDto update(String content, Long commentId);

    void delete(Long id);

    Comment findByIdChecked(Long commentId);
}
