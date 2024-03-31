package ru.otus.spring.finalproject.historyservice.services;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.otus.spring.finalproject.historyservice.dtos.PutObjectModificationRequest;
import ru.otus.spring.finalproject.historyservice.dtos.PutObjectModificationResponse;
import ru.otus.spring.finalproject.historyservice.models.db.ObjectModificationEntity;


public interface ObjectModificationService {

    Mono<PutObjectModificationResponse> save(PutObjectModificationRequest request);

    Mono<ObjectModificationEntity> save(ObjectModificationEntity objectModificationEntity);
}
