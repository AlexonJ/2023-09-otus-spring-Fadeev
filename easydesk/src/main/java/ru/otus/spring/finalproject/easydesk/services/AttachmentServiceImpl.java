package ru.otus.spring.finalproject.easydesk.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.otus.spring.finalproject.easydesk.dtos.attachments.AttachmentDto;
import ru.otus.spring.finalproject.easydesk.exceptions.CommonValidationException;
import ru.otus.spring.finalproject.easydesk.exceptions.ErrorMessages;
import ru.otus.spring.finalproject.easydesk.exceptions.InternalServerErrorException;
import ru.otus.spring.finalproject.easydesk.mappers.DtoMapper;
import ru.otus.spring.finalproject.easydesk.models.db.Attachment;
import ru.otus.spring.finalproject.easydesk.repositories.AttachmentRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class AttachmentServiceImpl implements AttachmentService{

    private final AttachmentRepository attachmentRepository;

    private final TicketService ticketService;

    private final SecurityService securityService;

    private final DtoMapper mapper;

    @Transactional(readOnly = true)
    @Override
    public List<AttachmentDto> getAttachmentsForTicketCode(String ticketCode) {
        return attachmentRepository.findAllByTicketCodeWithoutContent(ticketCode).map(mapper::attachmentToAttachmentDto).toList();
    }

    @Override
    public byte[] getAttachmentContent(Long attachmentId) {
        return attachmentRepository.getAttachmentContent(attachmentId).orElseThrow(
                () -> new CommonValidationException(
                        ErrorMessages.ATTACHMENT_NOT_FOUND_BY_ID.getCode(),
                        ErrorMessages.ATTACHMENT_NOT_FOUND_BY_ID.getMessage()));
    }

    @Override
    public AttachmentDto uploadAttachment(MultipartFile file, String ticketCode) {
        var ticket = ticketService.getByCodeChecked(ticketCode);
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            try {
                var attachment = new Attachment(0, fileName, file.getContentType(),
                        LocalDateTime.now(), securityService.getCurrentUser(), file.getBytes(), file.getSize(), ticket);
                return mapper.attachmentToAttachmentDtoWithoutContent(attachmentRepository.save(attachment));
            } catch (IOException e) {
                throw new InternalServerErrorException(
                        ErrorMessages.FILE_UPLOADING_ERROR.getCode(),
                        ErrorMessages.FILE_UPLOADING_ERROR.getMessage().formatted(e.getMessage()));
            }
    }

    @Override
    public void deleteAttachment(Long attachmentId) {
        if (isAttachmentExistByIdChecked(attachmentId)) {
            attachmentRepository.deleteById(attachmentId);
        }
    }

    @Override
    public boolean isAttachmentExistByIdChecked(Long attachmentId) {
        if (!attachmentRepository.existsById(attachmentId)) {
            throw new CommonValidationException(
                    ErrorMessages.ATTACHMENT_NOT_FOUND_BY_ID.getCode(),
                    ErrorMessages.ATTACHMENT_NOT_FOUND_BY_ID.getMessage());
        }
        return true;
    }
}
