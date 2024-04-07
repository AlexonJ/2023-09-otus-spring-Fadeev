package ru.otus.spring.finalproject.easydesk.dtos.comments;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.spring.finalproject.easydesk.dtos.users.UserDto;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CommentDto {

    private long id;

    private LocalDateTime createdAt;

    private UserDto createdBy;

    private String content;

}
