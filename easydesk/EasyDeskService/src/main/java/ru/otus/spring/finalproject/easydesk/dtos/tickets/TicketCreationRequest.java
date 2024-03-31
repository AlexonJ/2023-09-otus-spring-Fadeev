package ru.otus.spring.finalproject.easydesk.dtos.tickets;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.spring.finalproject.easydesk.models.enums.Category;
import ru.otus.spring.finalproject.easydesk.models.enums.TicketPriority;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class TicketCreationRequest {

    @NotEmpty(message = "Title cannot be empty")
    private String title;

    @NotEmpty(message = "Description cannot be empty")
    private String description;

    private TicketPriority priority;

    @NotNull(message = "Category cannot be empty")
    private Category category;

    private LocalDateTime dueDate;

}