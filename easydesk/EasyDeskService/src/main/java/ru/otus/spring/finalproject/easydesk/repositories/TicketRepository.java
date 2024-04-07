package ru.otus.spring.finalproject.easydesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.finalproject.easydesk.models.db.Ticket;
import ru.otus.spring.finalproject.easydesk.models.enums.Category;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Stream;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    Optional<Ticket> findByCode(String code);

    Optional<Ticket> findTopByCategoryOrderByCodeDesc(Category category);

    Stream<Ticket> findAllByDueDateBetween(LocalDateTime dateBegin, LocalDateTime dateEnd);
}
