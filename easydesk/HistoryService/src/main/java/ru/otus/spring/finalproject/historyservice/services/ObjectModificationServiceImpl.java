package ru.otus.spring.finalproject.historyservice.services;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.otus.spring.finalproject.historyservice.dtos.PutObjectModificationRequest;
import ru.otus.spring.finalproject.historyservice.dtos.PutObjectModificationResponse;
import ru.otus.spring.finalproject.historyservice.mappers.DtoMapper;
import ru.otus.spring.finalproject.historyservice.models.db.ObjectModificationEntity;
import ru.otus.spring.finalproject.historyservice.repositories.FieldModificationRepository;
import ru.otus.spring.finalproject.historyservice.repositories.ObjectModificationRepository;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ObjectModificationServiceImpl implements ObjectModificationService {

    private final ObjectModificationRepository objectModificationRepository;
    private final FieldModificationRepository fieldModificationRepository;

    private final DtoMapper mapper;

    @Override
    public Mono<PutObjectModificationResponse> save(PutObjectModificationRequest request) {

        return save(mapper.toObjectModificationEntity(request)).map(mapper::toPutObjectModificationResponse);
    }

    @Override
    public Mono<ObjectModificationEntity> save(ObjectModificationEntity objectModificationEntity) {

        return objectModificationRepository.save(objectModificationEntity)
                .flatMap(savedObjectModificationEntity -> fieldModificationRepository.saveAll(
                        savedObjectModificationEntity.getFields().stream().peek(fieldModificationEntity ->
                                fieldModificationEntity.setObjectId(savedObjectModificationEntity.getId())
                        ).collect(Collectors.toList())).then(Mono.just(savedObjectModificationEntity)));
    }
}
