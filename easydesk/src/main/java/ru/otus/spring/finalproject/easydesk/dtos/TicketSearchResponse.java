package ru.otus.spring.finalproject.easydesk.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.spring.finalproject.easydesk.dtos.tickets.TicketDto;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response object containing a list of tickets.")
public class TicketSearchResponse {
    private List<TicketDto> tickets;
}
