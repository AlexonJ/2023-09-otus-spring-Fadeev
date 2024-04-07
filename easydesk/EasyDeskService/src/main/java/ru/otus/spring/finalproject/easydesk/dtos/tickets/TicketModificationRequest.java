package ru.otus.spring.finalproject.easydesk.dtos.tickets;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.spring.finalproject.easydesk.models.enums.Category;
import ru.otus.spring.finalproject.easydesk.models.enums.TicketPriority;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(description = "Ticket data exchange entity")
public class TicketModificationRequest {

    private String title;

    private String description;

    private TicketPriority priority;

    private Category category;

    private LocalDateTime dueDate;

    private String waypointName;

}
