package ru.otus.spring.finalproject.easydesk.services;

import ru.otus.spring.finalproject.easydesk.dtos.comments.CommentDto;
import ru.otus.spring.finalproject.easydesk.models.db.Comment;

import java.util.List;

public interface CommentService {
    List<CommentDto> getByTicketCode(String ticketCode);

    CommentDto createByTicketCode(String commentText, String ticketCode);

    CommentDto updateById(String content, Long commentId);

    void deleteById(Long id);

    Comment findByIdChecked(Long commentId);
}
