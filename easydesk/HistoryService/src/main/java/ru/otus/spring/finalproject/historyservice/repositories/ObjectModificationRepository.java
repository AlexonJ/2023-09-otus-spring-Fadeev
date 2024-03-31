package ru.otus.spring.finalproject.historyservice.repositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import ru.otus.spring.finalproject.historyservice.models.db.ObjectModificationEntity;

public interface ObjectModificationRepository extends ReactiveCrudRepository<ObjectModificationEntity, Long> {

}
