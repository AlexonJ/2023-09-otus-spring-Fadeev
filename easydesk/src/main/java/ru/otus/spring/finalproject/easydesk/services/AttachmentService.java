package ru.otus.spring.finalproject.easydesk.services;

import org.springframework.web.multipart.MultipartFile;
import ru.otus.spring.finalproject.easydesk.dtos.attachments.AttachmentDto;

import java.util.List;

public interface AttachmentService {
    List<AttachmentDto> getAttachmentsByTicketCode(String ticketCode);

    byte[] getAttachmentContent(Long attachmentId);

    AttachmentDto uploadByTicketCode(MultipartFile file, String ticketCode);

    void deleteById(Long attachmentId);

    boolean isAttachmentExistByIdChecked(Long attachmentId);
}
