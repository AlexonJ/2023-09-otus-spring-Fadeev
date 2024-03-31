package ru.otus.spring.finalproject.historyservice.dtos;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Schema(name = "PutModificationDataRequest", description = "")
public class PutObjectModificationRequest {

    @NotEmpty
    private UUID objectId;

    @NotEmpty
    private String type;

    @NotEmpty
    private List<FieldModificationRequestLog> fields;

}
