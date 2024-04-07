package ru.otus.spring.finalproject.easydesk.services;

import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.finalproject.easydesk.dtos.TicketSummaryReportResponse;

import java.time.LocalDateTime;

public interface ReportingService {

    @Transactional
    TicketSummaryReportResponse getTicketSummaryReport(LocalDateTime beginDate, LocalDateTime endDate);
}
