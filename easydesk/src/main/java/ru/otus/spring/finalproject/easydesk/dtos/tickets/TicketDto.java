package ru.otus.spring.finalproject.easydesk.dtos.tickets;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.spring.finalproject.easydesk.dtos.users.UserDto;
import ru.otus.spring.finalproject.easydesk.dtos.waypoints.WaypointDto;
import ru.otus.spring.finalproject.easydesk.models.db.User;
import ru.otus.spring.finalproject.easydesk.models.db.Waypoint;
import ru.otus.spring.finalproject.easydesk.models.enums.TicketCategory;
import ru.otus.spring.finalproject.easydesk.models.enums.TicketPriority;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(description = "Ticket data exchange entity")
public class TicketDto {

    private long id;

    private String code;

    private String title;

    private String description;

    private TicketPriority priority;

    private TicketCategory category;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime dueDate;

    private UserDto createdBy;

    private UserDto assignedTo;

    private WaypointDto waypoint;

}
