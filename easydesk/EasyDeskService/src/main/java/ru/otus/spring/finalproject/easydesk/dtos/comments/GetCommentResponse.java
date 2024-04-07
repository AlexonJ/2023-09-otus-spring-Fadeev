package ru.otus.spring.finalproject.easydesk.dtos.comments;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class GetCommentResponse {

    String ticketCode;
    List<CommentDto> comments;

}
