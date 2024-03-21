package ru.otus.spring.finalproject.easydesk.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.finalproject.easydesk.dtos.SearchRequest;
import ru.otus.spring.finalproject.easydesk.dtos.TicketSearchResponse;
import ru.otus.spring.finalproject.easydesk.dtos.tickets.TicketCreationRequest;
import ru.otus.spring.finalproject.easydesk.dtos.tickets.TicketDto;
import ru.otus.spring.finalproject.easydesk.dtos.tickets.TicketModificationRequest;
import ru.otus.spring.finalproject.easydesk.models.search.TicketSearchFields;
import ru.otus.spring.finalproject.easydesk.dtos.errors.ErrorDetailed;
import ru.otus.spring.finalproject.easydesk.services.TicketService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TicketControllerImpl implements TicketController {

    private final TicketService ticketService;

    @Override
    public ResponseEntity<TicketSearchResponse> getTicketList(
            @RequestBody SearchRequest<TicketSearchFields> request) {
        List<TicketDto> tickets = ticketService.getTickets(request);
        return ResponseEntity.ok(TicketSearchResponse.builder().tickets(tickets).build());
    }

    @Override
    public ResponseEntity<TicketDto> createTicket(
            @RequestBody TicketCreationRequest request) {
        TicketDto ticket = ticketService.createTicket(request);
        return ResponseEntity.ok(ticket);
    }

    @Override
    public ResponseEntity<TicketDto> updateTicket(
            @PathVariable String code,
            @RequestBody  TicketModificationRequest request) {
        TicketDto ticket = ticketService.saveTicket(code, request);
        return ResponseEntity.ok(ticket);
    }

}
