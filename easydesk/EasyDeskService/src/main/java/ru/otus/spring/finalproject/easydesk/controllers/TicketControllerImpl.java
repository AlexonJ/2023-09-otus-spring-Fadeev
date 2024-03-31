package ru.otus.spring.finalproject.easydesk.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.otus.spring.finalproject.easydesk.dtos.SearchRequest;
import ru.otus.spring.finalproject.easydesk.dtos.SearchResponse;
import ru.otus.spring.finalproject.easydesk.dtos.tickets.TicketCreationRequest;
import ru.otus.spring.finalproject.easydesk.dtos.tickets.TicketDto;
import ru.otus.spring.finalproject.easydesk.dtos.tickets.TicketModificationRequest;
import ru.otus.spring.finalproject.easydesk.models.search.TicketSearchFields;
import ru.otus.spring.finalproject.easydesk.services.TicketService;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TicketControllerImpl implements TicketController {

    private final TicketService ticketService;

    @Override
    public ResponseEntity<SearchResponse<TicketDto>> getTicketList(SearchRequest<TicketSearchFields> request) {
        List<TicketDto> tickets = ticketService.findTickets(request);
        SearchResponse<TicketDto> ticketSearchResponse = new SearchResponse<>();
        ticketSearchResponse.setResult(tickets);
        return ResponseEntity.ok(ticketSearchResponse);
    }

    @Override
    public ResponseEntity<TicketDto> createTicket(TicketCreationRequest request) {
        TicketDto ticket = ticketService.createTicket(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ticket);
    }

    @Override
    public ResponseEntity<TicketDto> updateTicket(String code, TicketModificationRequest request) {
        TicketDto ticket = ticketService.saveTicket(code, request);
        return ResponseEntity.ok(ticket);
    }

    @Override
    public ResponseEntity<Void> deleteTicket(String code) {
        ticketService.deleteByCode(code);
        return ResponseEntity.ok().build();
    }

}
