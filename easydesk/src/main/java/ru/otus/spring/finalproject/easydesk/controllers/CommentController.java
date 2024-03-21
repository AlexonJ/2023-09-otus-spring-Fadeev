package ru.otus.spring.finalproject.easydesk.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.finalproject.easydesk.dtos.comments.CommentDto;
import ru.otus.spring.finalproject.easydesk.dtos.comments.CommentModificationRequest;
import ru.otus.spring.finalproject.easydesk.dtos.comments.GetCommentResponse;
import ru.otus.spring.finalproject.easydesk.dtos.errors.ErrorDetailed;

@RestController
@RequestMapping(path = "/api/v1/comments")
@Tag(name = "Comment controller", description = "Endpoints for managing comments")
public interface CommentController {

    @Operation(summary = "Get comments", description = "Retrieves comments for a ticket.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of comments for the ticket",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = GetCommentResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid client request",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetailed.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetailed.class)))
    })
    @GetMapping("/ticket/{ticketCode}")
    ResponseEntity<GetCommentResponse> getComments(
            @PathVariable(name = "ticketCode") @Parameter(name = "ticket code", description = "Ticket code") String ticketCode);

    @Operation(summary = "Creates a new comment for the ticket", description = "Creates a new comment for the ticket.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created new comment",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommentDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid client request",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetailed.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetailed.class)))
    })
    @PostMapping(path = "/ticket/{ticketCode}")
    ResponseEntity<CommentDto> createComment(
            @RequestParam @Parameter(name = "commentText", description = "Text comment") String content,
            @PathVariable(name = "ticketCode") @Parameter(name = "Ticket code", description = "Ticket code to associate with the new comment") String ticketCode);


    @Operation(summary = "Update comment for the ticket", description = "Updates an comment by the provided ticket code.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the content of the comment",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommentDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid client request",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetailed.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetailed.class)))
    })
    @PostMapping(path = "/{commentId}")
    ResponseEntity<CommentDto> updateComment(
            @RequestParam @Parameter(name = "commentText", description = "New comment text") String request,
            @PathVariable(name = "commentId") @Parameter(name = "Comment ID", description = "Comment ID to modification") Long commentId);

    @Operation(summary = "Delete comment", description = "Deletes the comment identified by the provided ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comment deleted successfully.",
                    content = @Content(mediaType = "application/json", schema = @Schema())),
            @ApiResponse(responseCode = "400", description = "Invalid client request",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetailed.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetailed.class)))
    })
    @DeleteMapping("/{commentId}")
    ResponseEntity<Void> deleteComment(
            @PathVariable @Parameter(name = "commentId", description = "The unique identifier of the comment to be deleted.") Long commentId);
}
