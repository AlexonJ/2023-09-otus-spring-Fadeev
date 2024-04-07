package ru.otus.spring.finalproject.easydesk.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.finalproject.easydesk.dtos.SearchRequest;
import ru.otus.spring.finalproject.easydesk.dtos.SearchResponse;
import ru.otus.spring.finalproject.easydesk.dtos.errors.ErrorResponse;
import ru.otus.spring.finalproject.easydesk.dtos.tickets.TicketCreationRequest;
import ru.otus.spring.finalproject.easydesk.dtos.tickets.TicketDto;
import ru.otus.spring.finalproject.easydesk.dtos.tickets.TicketModificationRequest;
import ru.otus.spring.finalproject.easydesk.models.search.TicketSearchFields;

@RestController
@RequestMapping(path = "/api/v1/tickets")
@Tag(name = "Ticket Controller", description = "Endpoints for managing tickets")
public interface TicketController {

    @Operation(summary = "Get a list of tickets", description = "Retrieves a list of tickets from the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of tickets",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = SearchResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid client request",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping
    ResponseEntity<SearchResponse<TicketDto>> getTicketList(
            @RequestBody @Parameter(description = "Ticket search request")
            SearchRequest<TicketSearchFields> request);

    @Operation(summary = "Create ticket", description = "Retrieves a new ticket.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully retrieved new ticket",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TicketCreationRequest.class))),
            @ApiResponse(responseCode = "400", description = "Invalid client request",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    ResponseEntity<TicketDto> createTicket(
            @RequestBody @Validated @Parameter(name = "request", description = "Ticket modification request")
            TicketCreationRequest request);

    @Operation(summary = "Update ticket", description = "Retrieves a modified ticket.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved modified ticket",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TicketModificationRequest.class))),
            @ApiResponse(responseCode = "400", description = "Invalid client request",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/{code}")
    ResponseEntity<TicketDto> updateTicket(
            @PathVariable @Parameter(name = "code", description = "Ticket code") String code,
            @RequestBody @Validated @Parameter(name = "request", description = "Ticket modification request")
            TicketModificationRequest request);

    @Operation(summary = "Delete ticket", description = "Deletes specified ticket.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ticket deleted successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema())),
            @ApiResponse(responseCode = "400", description = "Invalid client request",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })

    @DeleteMapping("/{code}")
    ResponseEntity<Void> deleteTicket(
            @PathVariable @Parameter(name = "code", description = "Ticket code") String code);
}
