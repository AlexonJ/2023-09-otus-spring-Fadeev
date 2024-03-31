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
    public void checkThatWaypointChangingIsPossible(List<Waypoint> possibleWaypoints, Waypoint nextWaypoint) {
        if (possibleWaypoints.isEmpty()) {
            throw new CommonValidationException(
                    ErrorMessages.END_OF_THE_PROCESS.getCode(),
                    ErrorMessages.END_OF_THE_PROCESS.getMessage()
                            .formatted(nextWaypoint.getName(), nextWaypoint.getProcess().getName()));

        }
        if(!possibleWaypoints.contains(nextWaypoint)) {
            throw new CommonValidationException(
                    ErrorMessages.INVALID_NEXT_WAYPOINT.getCode(),
                    ErrorMessages.INVALID_NEXT_WAYPOINT.getMessage()
                            .formatted(nextWaypoint, Arrays.toString(possibleWaypoints.toArray())));
        }
    }
}
