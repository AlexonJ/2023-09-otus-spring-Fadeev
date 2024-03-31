package ru.otus.spring.finalproject.easydesk.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.finalproject.easydesk.dtos.TicketSummaryReportResponse;
import ru.otus.spring.finalproject.easydesk.dtos.comments.GetCommentResponse;
import ru.otus.spring.finalproject.easydesk.services.ReportingService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@RequiredArgsConstructor
@RestController
public class ReportingControllerImpl implements ReportingController {

    private final ReportingService reportingService;

    @Override
    public ResponseEntity<TicketSummaryReportResponse> getTicketsSummaryData(LocalDate beginDate, LocalDate endDate) {
        return ResponseEntity.ok(reportingService.getTicketSummaryReport(beginDate.atStartOfDay(), endDate.atTime(LocalTime.MAX)));
    }
}
