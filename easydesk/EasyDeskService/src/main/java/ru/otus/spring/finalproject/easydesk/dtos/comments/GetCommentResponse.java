package ru.otus.spring.finalproject.easydesk.dtos.comments;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.spring.finalproject.easydesk.models.db.Comment;

import java.util.List;

@Builder
@Data
public class GetCommentResponse {

    String ticketCode;
    List<CommentDto> comments;

}
