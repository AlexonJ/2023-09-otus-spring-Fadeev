package ru.otus.spring.finalproject.easydesk.services;

import ru.otus.spring.finalproject.easydesk.models.db.Waypoint;

import java.util.List;

public interface TicketValidationService {
    void checkThatWaypointChangingIsPossible(List<Waypoint> possibleWaypoints, Waypoint nextWaypoint);
}
