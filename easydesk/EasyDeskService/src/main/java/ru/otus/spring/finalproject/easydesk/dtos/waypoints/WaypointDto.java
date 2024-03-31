package ru.otus.spring.finalproject.easydesk.dtos.waypoints;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.spring.finalproject.easydesk.dtos.processes.ProcessDto;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WaypointDto {

    private String name;

    private ProcessDto process;

    private List<WaypointDtoWithoutNextWaypoints> nextWaypoints;

}
