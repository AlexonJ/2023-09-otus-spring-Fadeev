package ru.otus.spring.finalproject.easydesk.dtos.waypoints;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.spring.finalproject.easydesk.dtos.processes.ProcessDto;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WaypointDtoWithoutNextWaypoints {

    private String name;

    private ProcessDto process;

}
