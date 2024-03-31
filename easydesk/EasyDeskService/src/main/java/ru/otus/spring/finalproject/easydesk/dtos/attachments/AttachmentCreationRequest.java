package ru.otus.spring.finalproject.easydesk.dtos.attachments;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class AttachmentCreationRequest {

    @NotEmpty(message = "Filename cannot be empty")
    private String filename;

    @NotEmpty(message = "Content type cannot be empty")
    private String type;

    @NotEmpty(message = "Content cannot be empty")
    private byte[] content;

    @NotEmpty(message = "Ticket code cannot be empty")
    private String ticketCode;
}
