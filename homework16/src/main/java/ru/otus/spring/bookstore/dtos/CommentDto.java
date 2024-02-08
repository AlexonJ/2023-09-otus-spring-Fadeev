package ru.otus.spring.bookstore.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// CommentDTO.java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    private long id;

    private String content;

}
