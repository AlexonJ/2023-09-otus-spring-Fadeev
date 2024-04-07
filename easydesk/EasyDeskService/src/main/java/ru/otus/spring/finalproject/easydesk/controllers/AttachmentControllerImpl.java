package ru.otus.spring.finalproject.easydesk.controllers;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Override
    public ResponseEntity<GetAttachmentResponse> getAttachments(String ticketCode) {
        var attachments = attachmentService.getAttachmentsByTicketCode(ticketCode);
        return ResponseEntity.ok(GetAttachmentResponse.builder().attachments(attachments).build());
    }

    @Override
    public ResponseEntity<Resource> getAttachmentContent(Long attachmentId) {
        var content = attachmentService.getAttachmentContent(attachmentId);
        ByteArrayResource resource = new ByteArrayResource(content);
        return ResponseEntity.ok(resource);
    }

    @Override
    public ResponseEntity<AttachmentDto> uploadAttachment(MultipartFile file, String ticketCode) {
        var attachment = attachmentService.uploadByTicketCode(file, ticketCode);
        logger.info("File {} successfully uploaded to ticket with code {}", file.getName(), ticketCode);
        return ResponseEntity.status(HttpStatus.CREATED).body(attachment);
    }

    @Override
    public ResponseEntity<Void> deleteAttachment(Long attachmentId) {
        attachmentService.deleteById(attachmentId);
        logger.info("Attachment with id {} successfully deleted", attachmentId);
        return ResponseEntity.ok().build();
    }
}
