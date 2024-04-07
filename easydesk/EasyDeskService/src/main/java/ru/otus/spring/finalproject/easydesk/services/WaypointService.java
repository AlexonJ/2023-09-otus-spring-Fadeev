package ru.otus.spring.finalproject.easydesk.services;

import ru.otus.spring.finalproject.easydesk.models.db.Process;
import ru.otus.spring.finalproject.easydesk.models.db.Waypoint;

public interface WaypointService {
    Waypoint findFirstByProcessAndNameChecked(Process process, String waypointName);
}
