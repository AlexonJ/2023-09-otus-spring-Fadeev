package ru.otus.spring.finalproject.easydesk.services;

import ru.otus.spring.finalproject.easydesk.dtos.SearchRequest;
import ru.otus.spring.finalproject.easydesk.dtos.tickets.TicketCreationRequest;
import ru.otus.spring.finalproject.easydesk.dtos.tickets.TicketDto;
import ru.otus.spring.finalproject.easydesk.dtos.tickets.TicketModificationRequest;
import ru.otus.spring.finalproject.easydesk.models.db.Ticket;
import ru.otus.spring.finalproject.easydesk.models.search.TicketSearchFields;

import java.util.List;

public interface TicketService {
    List<TicketDto> findTickets(SearchRequest<TicketSearchFields> request);

    TicketDto saveTicket(String code, TicketModificationRequest request);

    TicketDto createTicket(TicketCreationRequest request);

    Ticket getByIdChecked(Long id);

    Ticket getByCodeChecked(String code);

    void deleteByCode(String code);
}
