package ru.otus.spring.finalproject.easydesk.dtos.attachments;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AttachmentDto {

    private Long id;

    private String filename;

    private String type;

    private Long size;

    private byte[] content;

}