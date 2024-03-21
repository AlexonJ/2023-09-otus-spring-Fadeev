package ru.otus.spring.finalproject.easydesk.services;

import org.springframework.web.multipart.MultipartFile;
import ru.otus.spring.finalproject.easydesk.dtos.attachments.AttachmentCreationRequest;
import ru.otus.spring.finalproject.easydesk.dtos.attachments.AttachmentDto;

import java.util.List;

public interface AttachmentService {
    List<AttachmentDto> getAttachmentsForTicketCode(String ticketCode);

    byte[] getAttachmentContent(Long attachmentId);

    AttachmentDto uploadAttachment(MultipartFile file, String ticketCode);

    void deleteAttachment(Long attachmentId);

    boolean isAttachmentExistByIdChecked(Long attachmentId);
}
