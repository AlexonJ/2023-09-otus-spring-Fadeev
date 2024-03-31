package ru.otus.spring.finalproject.easydesk.services;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.otus.spring.finalproject.easydesk.dtos.history.Externable;
import ru.otus.spring.finalproject.easydesk.dtos.history.FieldModificationRequestLog;
import ru.otus.spring.finalproject.easydesk.dtos.history.HistoryDataRequest;
import ru.otus.spring.finalproject.easydesk.repositories.SettingsProvider;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class HistoryServiceImpl implements HistoryService {

    private final SettingsProvider settingsProvider;
    private final Logger logger = LoggerFactory.getLogger(HistoryServiceImpl.class);

    @Override
    public <T extends Externable> void sendHistoryData(T original, T updated) {

        List<FieldModificationRequestLog> fieldChanges;

        try {
            fieldChanges = findChanges(original, updated);
        } catch (IllegalAccessException e) {
            logger.error("An error occurred while collect changes data: {}", e.getMessage());
            return;
        }

        var historyDataRequest = HistoryDataRequest.builder()
                .type(original.getClass().toString())
                .objectId(updated.getExternalId())
                .fields(fieldChanges).build();

        WebClient webClient = WebClient.create();
        webClient.post().uri(settingsProvider.getHistoryServicePath()).bodyValue(historyDataRequest)
                .retrieve()
                .toBodilessEntity()
                .subscribe(response -> logger.info("History data successfully send with HTTP status: " + response.getStatusCode()),
                        error -> logger.error("An error occurred while sending history data: " + error.getMessage()),
                        () -> logger.info("History data request completed."));
    }

    public List<FieldModificationRequestLog> findChanges(Object original, Object updated) throws IllegalAccessException {
        List<FieldModificationRequestLog> changes = new ArrayList<>();

        Field[] fields = original.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Object originalValue = field.get(original);
            Object updatedValue = field.get(updated);

            if (!Objects.equals(originalValue, updatedValue)) {
                var fieldModificationLog = new FieldModificationRequestLog();
                fieldModificationLog.setName(field.getName());
                fieldModificationLog.setValue(updatedValue.toString());
                changes.add(fieldModificationLog);
            }
        }

        return changes;
    }
}
