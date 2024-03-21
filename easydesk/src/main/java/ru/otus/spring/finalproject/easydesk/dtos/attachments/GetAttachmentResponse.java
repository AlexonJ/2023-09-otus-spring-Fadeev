package ru.otus.spring.finalproject.easydesk.dtos.attachments;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAttachmentResponse {

    List<AttachmentDto> attachments;

}
