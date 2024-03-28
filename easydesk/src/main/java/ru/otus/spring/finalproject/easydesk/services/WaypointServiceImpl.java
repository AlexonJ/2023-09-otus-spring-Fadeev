package ru.otus.spring.finalproject.easydesk.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.finalproject.easydesk.exceptions.CommonValidationException;
import ru.otus.spring.finalproject.easydesk.exceptions.ErrorMessages;
import ru.otus.spring.finalproject.easydesk.models.db.Process;
import ru.otus.spring.finalproject.easydesk.models.db.Waypoint;
import ru.otus.spring.finalproject.easydesk.repositories.WaypointRepository;

@RequiredArgsConstructor
@Service
public class WaypointServiceImpl implements WaypointService {

    private final WaypointRepository waypointRepository;

    @Override
    public Waypoint findFirstByProcessAndNameChecked(Process process, String waypointName) {
        return waypointRepository.findFirstByProcessAndName(process, waypointName).orElseThrow(
                () -> new CommonValidationException(
                        ErrorMessages.WAYPOINT_NOT_FOUND_BY_NAME.getCode(),
                        ErrorMessages.WAYPOINT_NOT_FOUND_BY_NAME.getMessage().formatted(waypointName, process.getName())));
    }
}
