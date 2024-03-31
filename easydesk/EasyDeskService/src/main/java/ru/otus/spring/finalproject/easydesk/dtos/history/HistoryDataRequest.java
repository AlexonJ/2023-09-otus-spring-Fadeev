package ru.otus.spring.finalproject.easydesk.dtos.history;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Builder
@Data
public class HistoryDataRequest {

    private String type;

    private UUID objectId;

    List<FieldModificationRequestLog> fields;

}
