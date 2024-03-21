package ru.otus.spring.finalproject.easydesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.finalproject.easydesk.models.db.Ticket;
import ru.otus.spring.finalproject.easydesk.models.enums.TicketCategory;

import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    Optional<Ticket> findByCode(String code);

    Optional<Ticket> findTopByCategory(TicketCategory category);
}
