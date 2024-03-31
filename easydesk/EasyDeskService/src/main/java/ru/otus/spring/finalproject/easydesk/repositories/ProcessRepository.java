package ru.otus.spring.finalproject.easydesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.finalproject.easydesk.models.db.Process;
import ru.otus.spring.finalproject.easydesk.models.enums.Category;

import java.util.Optional;

public interface ProcessRepository extends JpaRepository<Process, Long> {

    Optional<Process> findFirstByCategory(Category category);

}
