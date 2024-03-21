package ru.otus.spring.finalproject.easydesk.controllers;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.otus.spring.finalproject.easydesk.dtos.attachments.AttachmentDto;
import ru.otus.spring.finalproject.easydesk.dtos.attachments.GetAttachmentResponse;
import ru.otus.spring.finalproject.easydesk.services.AttachmentService;

@RequiredArgsConstructor
@RestController
public class AttachmentControllerImpl implements AttachmentController {

    private final Logger logger = LoggerFactory.getLogger(AttachmentControllerImpl.class);

    private final AttachmentService attachmentService;

    @GetMapping("/ticket/{ticketCode}")
    @Override
    public ResponseEntity<GetAttachmentResponse> getAttachments(
            @PathVariable @Parameter(name = "ticket code", description = "Ticket code") String ticketCode) {
        var attachments = attachmentService.getAttachmentsForTicketCode(ticketCode);
        return ResponseEntity.ok(GetAttachmentResponse.builder().attachments(attachments).build());
    }

    @GetMapping("/{attachmentId}")
    @Override
    public ResponseEntity<Resource> getAttachments(
            @PathVariable @Parameter(name = "Attachment identificator", description = "Attachment identificator") Long attachmentId) {
        var content = attachmentService.getAttachmentContent(attachmentId);
        ByteArrayResource resource = new ByteArrayResource(content);
        return ResponseEntity.ok(resource);
    }

    @PostMapping(path = "/ticket/{ticketId}")
    @Override
    public ResponseEntity<AttachmentDto> uploadAttachment(
            @RequestBody @Parameter(name = "File", description = "The file to upload") MultipartFile file,
            @PathVariable(name = "ticketId") @Parameter(name = "Ticket code", description = "Ticket code to associate with the attachment") String ticketCode) {
        var attachment = attachmentService.uploadAttachment(file, ticketCode);
        logger.info("File {} successfully uploaded to ticket with code {}", file.getName(), ticketCode);
        return ResponseEntity.ok(attachment);
    }

    @DeleteMapping("/{attachmentId}")
    @Override
    public ResponseEntity<Void> deleteAttachment(
            @PathVariable @Parameter(name = "attachmentId", description = "The unique identifier of the attachment to be deleted.") Long attachmentId) {
        attachmentService.deleteAttachment(attachmentId);
        logger.info("Attachment with id {} successfully deleted", attachmentId);
        return ResponseEntity.ok().build();
    }
}
