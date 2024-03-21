package ru.otus.spring.finalproject.easydesk.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.otus.spring.finalproject.easydesk.dtos.attachments.AttachmentDto;
import ru.otus.spring.finalproject.easydesk.dtos.attachments.GetAttachmentResponse;
import ru.otus.spring.finalproject.easydesk.dtos.errors.ErrorDetailed;

@RestController
@Tag(name = "Attachment controller", description = "Endpoints for managing attachments")
public interface AttachmentController {

    @Operation(summary = "Get attachments", description = "Retrieves attachments for a ticket.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of attachments",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = GetAttachmentResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid client request",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetailed.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetailed.class)))
    })
    @GetMapping("/ticket/{ticketCode}")
    ResponseEntity<GetAttachmentResponse> getAttachments(
            @PathVariable @Parameter(name = "ticket code", description = "Ticket code") String ticketCode);

    @Operation(summary = "Get attachment content", description = "Retrieves attachment content.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the content of attachment",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Resource.class))),
            @ApiResponse(responseCode = "400", description = "Invalid client request",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetailed.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetailed.class)))
    })
    @GetMapping("/{attachmentId}")
    ResponseEntity<Resource> getAttachments(
            @PathVariable @Parameter(name = "Attachment identificator", description = "Attachment identificator") Long attachmentId);

    @Operation(summary = "Upload attachment content for the ticket", description = "This endpoint uploads an attachment for the ticket.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the content of the attachment",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AttachmentDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid client request",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetailed.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetailed.class)))
    })
    @PostMapping(path = "/ticket/{ticketId}")
    ResponseEntity<AttachmentDto> uploadAttachment(
            @RequestBody @Parameter(name = "File", description = "The file to upload") MultipartFile file,
            @PathVariable(name = "ticketId") @Parameter(name = "Ticket code", description = "Ticket code to associate with the attachment") String ticketCode);

    @Operation(summary = "Delete Attachment", description = "Deletes the attachment identified by the provided attachment ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Attachment deleted successfully.",
                    content = @Content(mediaType = "application/json", schema = @Schema())),
            @ApiResponse(responseCode = "400", description = "Invalid client request",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetailed.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetailed.class)))
    })
    @DeleteMapping("/{attachmentId}")
    ResponseEntity<Void> deleteAttachment(
            @PathVariable @Parameter(name = "attachmentId", description = "The unique identifier of the attachment to be deleted.") Long attachmentId);
}
