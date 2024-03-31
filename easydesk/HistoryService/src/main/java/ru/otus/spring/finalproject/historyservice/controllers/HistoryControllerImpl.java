package ru.otus.spring.finalproject.historyservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import ru.otus.spring.finalproject.historyservice.dtos.PutObjectModificationRequest;
import ru.otus.spring.finalproject.historyservice.services.ObjectModificationService;

@RequiredArgsConstructor
@RestController
public class HistoryControllerImpl implements HistoryController {

    private final ObjectModificationService objectModificationService;

    @Override
    public Mono<ResponseEntity<Void>> putObjectModificationData(PutObjectModificationRequest request) {
        return objectModificationService.save(request).then(Mono.just(ResponseEntity.status(HttpStatus.CREATED).build()));
    }
}
