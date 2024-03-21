package ru.otus.spring.finalproject.easydesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.finalproject.easydesk.models.db.Waypoint;

import java.util.Optional;

public interface WaypointRepository extends JpaRepository<Waypoint, Long> {

    Optional<Waypoint> findFirstById(Long id);
}
