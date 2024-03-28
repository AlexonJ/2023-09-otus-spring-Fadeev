package ru.otus.spring.finalproject.easydesk.services;

import org.springframework.stereotype.Service;
import ru.otus.spring.finalproject.easydesk.exceptions.CommonValidationException;
import ru.otus.spring.finalproject.easydesk.exceptions.ErrorMessages;
import ru.otus.spring.finalproject.easydesk.models.db.Waypoint;

import java.util.Arrays;
import java.util.List;

@Service
public class TicketValidationServiceImpl implements TicketValidationService{

    @Override
    public boolean isPossibleToChangeWaypointChecked(List<Waypoint> possibleWaypoints, Waypoint nextWaypoint) {
        if(!possibleWaypoints.contains(nextWaypoint)) {
            throw new CommonValidationException(
                    ErrorMessages.INVALID_NEXT_WAYPOINT.getCode(),
                    ErrorMessages.INVALID_NEXT_WAYPOINT.getMessage()
                            .formatted(nextWaypoint, Arrays.toString(possibleWaypoints.toArray())));
        }
        return true;
    }
}
