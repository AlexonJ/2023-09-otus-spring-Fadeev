package ru.otus.spring.finalproject.easydesk.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;
import ru.otus.spring.finalproject.easydesk.dtos.SearchRequest;
import ru.otus.spring.finalproject.easydesk.dtos.tickets.TicketCreationRequest;
import ru.otus.spring.finalproject.easydesk.dtos.tickets.TicketDto;
import ru.otus.spring.finalproject.easydesk.dtos.tickets.TicketModificationRequest;
import ru.otus.spring.finalproject.easydesk.exceptions.EntityNotFoundException;
import ru.otus.spring.finalproject.easydesk.exceptions.ErrorMessages;
import ru.otus.spring.finalproject.easydesk.mappers.DtoMapper;
import ru.otus.spring.finalproject.easydesk.models.db.Ticket;
import ru.otus.spring.finalproject.easydesk.models.enums.TicketCategory;
import ru.otus.spring.finalproject.easydesk.models.enums.TicketPriority;
import ru.otus.spring.finalproject.easydesk.models.search.TicketSearchFields;
import ru.otus.spring.finalproject.easydesk.repositories.SettingsProvider;
import ru.otus.spring.finalproject.easydesk.repositories.TicketRepository;
import ru.otus.spring.finalproject.easydesk.repositories.WaypointRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class TicketServiceImpl implements TicketService {

    private final SettingsProvider settingsProvider;

    private final TicketRepository ticketRepository;

    private final EntitySearchService entitySearchService;

    private final SecurityService securityService;

    private final WaypointRepository waypointRepository;

    private final DtoMapper mapper;

    @Transactional
    @Override
    public List<TicketDto> getTickets(SearchRequest<TicketSearchFields> request) {
        return entitySearchService.findEntities(request, Ticket.class)
                .map(mapper::ticketToTicketDto).toList();
    }

    @Override
    public TicketDto saveTicket(String code, TicketModificationRequest request) {

        Ticket foundedTicket = getByCodeChecked(code);

        if (!StringUtils.isEmptyOrWhitespace(request.getTitle())) {
            foundedTicket.setTitle(request.getTitle());
        }
        if (!StringUtils.isEmptyOrWhitespace(request.getDescription())) {
            foundedTicket.setDescription(request.getDescription());
        }
        if (!Objects.isNull(request.getCategory())) {
            foundedTicket.setCategory(request.getCategory());
        }
        if (!Objects.isNull(request.getPriority())) {
            foundedTicket.setPriority(request.getPriority());
        }
        if (!Objects.isNull(request.getDueDate())) {
            foundedTicket.setDueDate(request.getDueDate());
        }

        foundedTicket.setUpdatedAt(LocalDateTime.now());
        return mapper.ticketToTicketDto(ticketRepository.save(foundedTicket));
    }

    @Override
    public TicketDto createTicket(TicketCreationRequest request) {
        Ticket ticket = new Ticket();
        ticket.setTitle(request.getTitle());
        ticket.setDescription(request.getDescription());
        ticket.setCategory(request.getCategory());
        ticket.setCode(getNextCodeForCategory(request.getCategory()));
        ticket.setCreatedAt(LocalDateTime.now());
        ticket.setUpdatedAt(LocalDateTime.now());
        if (Objects.isNull(request.getPriority())) {
            ticket.setPriority(TicketPriority.valueOf(settingsProvider.getDefaultPriority()));
        }
        ticket.setDueDate(LocalDateTime.now().plusDays(settingsProvider.getDaysDuePeriod()));
        ticket.setCreatedBy(securityService.getCurrentUser());
        //TODO complete
        ticket.setAssignedTo(ticket.getCreatedBy());
        ticket.setWaypoint(waypointRepository.findFirstById(1L).get());
        return mapper.ticketToTicketDto(ticketRepository.save(ticket));
    }

    @Override
    public Ticket getByIdChecked(Long id) {
        return ticketRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(ErrorMessages.TICKET_NOT_FOUND_BY_ID.getMessage().formatted(id)));
    }

    @Override
    public Ticket getByCodeChecked(String code) {
        return ticketRepository.findByCode(code).orElseThrow(() ->
                new EntityNotFoundException(ErrorMessages.TICKET_NOT_FOUND_BY_CODE.getMessage().formatted(code)));
    }

    public String getNextCodeForCategory(TicketCategory category) {
        Integer lastNumber =  ticketRepository.findTopByCategory(category).map(
                ticket -> Integer.valueOf(ticket.getCode().substring(3))).get();
        return category.getCodePrefix() + String.format("%03d", lastNumber);
    }
}
