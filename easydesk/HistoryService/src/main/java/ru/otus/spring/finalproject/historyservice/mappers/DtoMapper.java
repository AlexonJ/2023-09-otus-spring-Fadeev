package ru.otus.spring.finalproject.historyservice.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.otus.spring.finalproject.historyservice.dtos.PutObjectModificationRequest;
import ru.otus.spring.finalproject.historyservice.dtos.PutObjectModificationResponse;
import ru.otus.spring.finalproject.historyservice.models.db.ObjectModificationEntity;

@Mapper(componentModel = "spring")
public interface DtoMapper {

    ObjectModificationEntity toObjectModificationEntity(PutObjectModificationRequest request);

    PutObjectModificationResponse toPutObjectModificationResponse(ObjectModificationEntity entity);

}
