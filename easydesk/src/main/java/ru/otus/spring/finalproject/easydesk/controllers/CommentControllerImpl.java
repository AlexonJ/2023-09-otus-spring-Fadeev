package ru.otus.spring.finalproject.easydesk.controllers;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.finalproject.easydesk.dtos.comments.CommentDto;
import ru.otus.spring.finalproject.easydesk.dtos.comments.GetCommentResponse;
import ru.otus.spring.finalproject.easydesk.services.CommentService;

@RequiredArgsConstructor
@RestController
public class CommentControllerImpl implements CommentController {

    private final Logger logger = LoggerFactory.getLogger(AttachmentControllerImpl.class);

    private final CommentService commentService;

    @Transactional
    @Override
    public ResponseEntity<GetCommentResponse> getComments(String ticketCode) {
        return ResponseEntity.ok(GetCommentResponse.builder()
                .ticketCode(ticketCode)
                .comments(commentService.getByTicketCode(ticketCode)).build());
    }

    @Override
    public ResponseEntity<CommentDto> createComment(String commentText, String ticketCode) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.createByTicketCode(commentText, ticketCode));
    }

    @Override
    public ResponseEntity<CommentDto> updateComment(String commentContent, Long commentId) {
        return ResponseEntity.ok().body(commentService.updateById(commentContent, commentId));
    }

    @Override
    public ResponseEntity<Void> deleteComment(Long commentId) {
        commentService.deleteById(commentId);
        return ResponseEntity.ok().build();
    }
}
