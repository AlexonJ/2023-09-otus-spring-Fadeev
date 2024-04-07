package ru.otus.spring.finalproject.easydesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.otus.spring.finalproject.easydesk.models.db.Attachment;

import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {

    @Query("SELECT NEW ru.otus.spring.finalproject.easydesk.models.db.Attachment(\n" +
            "    attachment.id, \n" +
            "    attachment.filename,\n" +
            "    attachment.type,\n" +
            "    attachment.createdBy,\n" +
            "    attachment.createdAt,\n" +
            "    attachment.size\n" +
            "    ) FROM Attachment AS attachment WHERE attachment.ticket.code = :ticketCode")
    Stream<Attachment> findAllByTicketCodeWithoutContent(String ticketCode);

    @Query("SELECT attachment.content FROM Attachment attachment WHERE attachment.id = :attachmentId")
    Optional<byte[]> getAttachmentContent(Long attachmentId);

    Optional<Attachment> findFirstById(Long id);
}
