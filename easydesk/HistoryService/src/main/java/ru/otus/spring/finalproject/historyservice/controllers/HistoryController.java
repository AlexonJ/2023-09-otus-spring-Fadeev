package ru.otus.spring.finalproject.historyservice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import ru.otus.spring.finalproject.historyservice.dtos.PutObjectModificationRequest;
import ru.otus.spring.finalproject.historyservice.dtos.PutObjectModificationResponse;
import ru.otus.spring.finalproject.historyservice.errors.ErrorDetailed;

@RestController
@RequestMapping(path = "/api/v1/history")
@Tag(name = "History controller", description = "Endpoints for managing object changes history records")
public interface HistoryController {

    @Operation(summary = "Put object modification data", description = "This endpoint is used to store modifications made to objects in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully retrieved the object modification data",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PutObjectModificationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid client request",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetailed.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetailed.class)))
    })
    @PostMapping("/")
    Mono<ResponseEntity<Void>> putObjectModificationData(
            @RequestBody @Parameter(name = "ObjectModificationData", description = "Object modification data") PutObjectModificationRequest request);
}