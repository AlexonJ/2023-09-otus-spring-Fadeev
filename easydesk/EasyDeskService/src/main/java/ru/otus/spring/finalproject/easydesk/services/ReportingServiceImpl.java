package ru.otus.spring.finalproject.easydesk.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.finalproject.easydesk.dtos.TicketSummaryReportResponse;
import ru.otus.spring.finalproject.easydesk.models.db.Ticket;
import ru.otus.spring.finalproject.easydesk.models.enums.Category;
import ru.otus.spring.finalproject.easydesk.repositories.TicketRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
public class ReportingServiceImpl implements ReportingService {

    private final TicketRepository ticketRepository;

    @Transactional
    @Override
    public TicketSummaryReportResponse getTicketSummaryReport(LocalDateTime beginDateTime, LocalDateTime endDateTime) {
        HashMap<Category, TicketSummaryReportResponse.CategorySummary> categories = new HashMap<>();

        var report = TicketSummaryReportResponse.builder()
                .categories(new ArrayList<>())
                .beginReportDate(beginDateTime)
                .endReportDate(endDateTime).build();
        Stream<Ticket> tickets = ticketRepository.findAllByDueDateBetween(beginDateTime, endDateTime);
        tickets.forEach(ticket -> {
            var categorySummary = categories.get(ticket.getCategory());
            if (Objects.isNull(categorySummary)) {
                categorySummary = TicketSummaryReportResponse.CategorySummary.builder().tickets(new ArrayList<>()).build();
                categories.put(ticket.getCategory(), categorySummary);
                report.getCategories().add(categorySummary);
            }
            var ticketShortInfo = TicketSummaryReportResponse.TicketShortInfo.builder()
                    .ticketCode(ticket.getCode())
                    .description(ticket.getDescription()).build();

            categorySummary.getTickets().add(ticketShortInfo);
            categorySummary.setCategory(ticket.getCategory());

            categorySummary.increaseTotalTicketsCount();
            report.increaseTotalTicketsCount();

            if(ticket.getCreatedAt().isAfter(beginDateTime) && ticket.getCreatedAt().isBefore(endDateTime)) {
                categorySummary.increaseCreatedTicketsCount();
                report.increaseCreatedTicketsCount();
            }

            if (!ticket.getWaypoint().isLastWaypoint()) {
                if (ticket.getDueDate().isBefore(LocalDateTime.now())) {
                    categorySummary.increaseExpiredTicketsCount();
                    report.increaseExpiredTicketsCount();
                } else {
                    categorySummary.increaseCompletedInTimeTicketsCount();
                    report.increaseCompletedInTimeTicketsCount();
                }
            } else {
                categorySummary.increaseClosedTicketsCount();
                report.increaseClosedTicketsCount();
            }
        });
        return report;
    }
}