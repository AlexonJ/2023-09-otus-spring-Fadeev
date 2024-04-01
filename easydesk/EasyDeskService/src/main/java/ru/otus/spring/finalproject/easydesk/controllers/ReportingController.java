package ru.otus.spring.finalproject.easydesk.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.finalproject.easydesk.dtos.TicketSummaryReportResponse;
import ru.otus.spring.finalproject.easydesk.dtos.comments.GetCommentResponse;
import ru.otus.spring.finalproject.easydesk.dtos.errors.ErrorResponse;

import java.time.LocalDate;

@RestController
@RequestMapping(path = "/api/v1/reports")
@Tag(name = "Reporting controller", description = "Endpoints for getting reports")
public interface ReportingController {

    @Operation(summary = "Get tickets summary data", description = "Retrieves comments for a ticket.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved report",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = GetCommentResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid client request",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/ticket-summary-data")
    ResponseEntity<TicketSummaryReportResponse> getTicketsSummaryData(
            @RequestParam(name = "beginDate") @Parameter(name = "beginDate", description = "Report beginning date") LocalDate beginDate,
            @RequestParam(name = "endDate") @Parameter(name = "endDate", description = "Report ending date") LocalDate endDate);

}
