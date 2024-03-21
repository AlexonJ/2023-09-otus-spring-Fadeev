package ru.otus.spring.finalproject.easydesk.services;

import ru.otus.spring.finalproject.easydesk.dtos.tickets.TicketDto;

public interface ValidationService {

    void checkModificationTicketRequest(TicketDto ticketDto);
}
