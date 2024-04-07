package ru.otus.spring.finalproject.easydesk.services;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;
import ru.otus.spring.finalproject.easydesk.dtos.SearchRequest;
import ru.otus.spring.finalproject.easydesk.dtos.tickets.TicketCreationRequest;
import ru.otus.spring.finalproject.easydesk.dtos.tickets.TicketDto;
import ru.otus.spring.finalproject.easydesk.dtos.tickets.TicketModificationRequest;
import ru.otus.spring.finalproject.easydesk.exceptions.CommonValidationException;
import ru.otus.spring.finalproject.easydesk.exceptions.EntityNotFoundException;
import ru.otus.spring.finalproject.easydesk.exceptions.ErrorMessages;
import ru.otus.spring.finalproject.easydesk.mappers.DtoMapper;
import ru.otus.spring.finalproject.easydesk.models.db.Ticket;
import ru.otus.spring.finalproject.easydesk.models.db.User;
import ru.otus.spring.finalproject.easydesk.models.db.Waypoint;
import ru.otus.spring.finalproject.easydesk.models.enums.Category;
import ru.otus.spring.finalproject.easydesk.models.enums.TicketPriority;
import ru.otus.spring.finalproject.easydesk.models.search.TicketSearchFields;
import ru.otus.spring.finalproject.easydesk.repositories.ProcessRepository;
import ru.otus.spring.finalproject.easydesk.repositories.SettingsProvider;
import ru.otus.spring.finalproject.easydesk.repositories.TicketRepository;
import ru.otus.spring.finalproject.easydesk.repositories.UserCategoryRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
public class TicketServiceImpl implements TicketService {

    Logger logger = LoggerFactory.getLogger(TicketServiceImpl.class);

    private final SettingsProvider settingsProvider;

    private final TicketRepository ticketRepository;

    private final EntitySearchService entitySearchService;

    private final SecurityService securityService;

    private final WaypointService waypointService;

    private final TicketValidationService ticketValidationService;

    private final ProcessRepository processRepository;

    private final EmailSendingService emailSendingService;

    private final HistoryService historyService;

    private final UserCategoryService userCategoryService;

    private final DtoMapper mapper;

    @Transactional(readOnly = true)
    @Override
    public List<TicketDto> findTickets(SearchRequest<TicketSearchFields> request) {
        return entitySearchService.findEntities(request, Ticket.class)
                .map(mapper::ticketToTicketDto).toList();
    }

    @Transactional
    @Override
    public TicketDto saveTicket(String code, TicketModificationRequest request) {

        Ticket ticket = getByCodeChecked(code);

        Ticket originalTicket = new Ticket();
        BeanUtils.copyProperties(ticket, originalTicket);

        if (!StringUtils.isEmptyOrWhitespace(request.getTitle())) {
            ticket.setTitle(request.getTitle());
        }
        if (!StringUtils.isEmptyOrWhitespace(request.getDescription())) {
            ticket.setDescription(request.getDescription());
        }
        if (!Objects.isNull(request.getCategory())) {
            ticket.setCategory(request.getCategory());
        }
        if (!Objects.isNull(request.getPriority())) {
            ticket.setPriority(request.getPriority());
        }
        if (!Objects.isNull(request.getDueDate())) {
            ticket.setDueDate(request.getDueDate());
        }
        if (!StringUtils.isEmptyOrWhitespace(request.getWaypointName())) {
            if (!Objects.isNull(ticket.getWaypoint())) {
                Waypoint nextWaypoint = waypointService.findFirstByProcessAndNameChecked(
                        ticket.getWaypoint().getProcess(), request.getWaypointName());
                if (nextWaypoint.getId() != ticket.getWaypoint().getId()) {
                    ticketValidationService.checkThatWaypointChangingIsPossible(
                            ticket.getWaypoint().getNextWaypoints(), nextWaypoint);
                    ticket.setWaypoint(nextWaypoint);
                }
            }
        }

        ticket.setUpdatedAt(LocalDateTime.now());

        if (settingsProvider.getHistorySendingEnabled()) {
            historyService.sendHistoryData(originalTicket, ticket);
        }
        ticketRepository.save(ticket);
        logger.info("Ticket id={} saved", ticket.getId());

        return mapper.ticketToTicketDto(ticket);
    }

    @Transactional
    @Override
    public TicketDto createTicket(TicketCreationRequest request) {

        Ticket ticket = new Ticket();
        ticket.setTitle(request.getTitle());
        ticket.setDescription(request.getDescription());
        ticket.setCategory(request.getCategory());
        ticket.setCode(getNextCodeForCategory(request.getCategory()));
        ticket.setCreatedAt(LocalDateTime.now());
        ticket.setUpdatedAt(LocalDateTime.now());

        if (!Objects.isNull(request.getPriority())) {
            ticket.setPriority(request.getPriority());
        } else {
            ticket.setPriority(TicketPriority.valueOf(settingsProvider.getDefaultPriority()));
        }

        ticket.setDueDate(LocalDateTime.now().plusDays(settingsProvider.getDaysDuePeriod()));
        ticket.setCreatedBy(securityService.getCurrentUser());
        ticket.setAssignedBy(securityService.getCurrentUser());

        ticket.setWaypoint(processRepository.findFirstByCategory(ticket.getCategory()).orElseThrow(() ->
                new CommonValidationException(
                        ErrorMessages.WAYPOINT_NOT_FOUND_BY_CATEGORY.getCode(),
                        ErrorMessages.WAYPOINT_NOT_FOUND_BY_CATEGORY.getMessage()
                                .formatted(ticket.getCategory().name()))).getStartingPoint());

        ticket.setAssignedTo(userCategoryService.getLeastBusyUserByCategory(ticket.getCategory()));

        ticketRepository.save(ticket);
        logger.info("Ticket id={} created", ticket.getId());

        if (settingsProvider.getEmailSendingEnabled()) {
            if (!StringUtils.isEmptyOrWhitespace(ticket.getAssignedTo().getEmail())) {
                emailSendingService.sendEmailTicketAssigned(ticket.getAssignedTo(), ticket);
            } else {
                logger.info("Cannot send email message to {} due to empty email address",
                        ticket.getAssignedTo().getFullName());
            }
        }

        if (settingsProvider.getHistorySendingEnabled()) {
            historyService.sendHistoryData(new Ticket(), ticket);
        }

        return mapper.ticketToTicketDto(ticket);
    }

    @Override
    public void deleteByCode(String code) {
        Ticket ticket = getByCodeChecked(code);
        ticketRepository.delete(ticket);
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

    public String getNextCodeForCategory(Category category) {
        Integer lastNumber = ticketRepository.findTopByCategoryOrderByCodeDesc(category).map(
                ticket -> Integer.valueOf(ticket.getCode().substring(3))).orElse(0);
        return category.getCodePrefix() + String.format("%03d", lastNumber + 1);
    }
}
