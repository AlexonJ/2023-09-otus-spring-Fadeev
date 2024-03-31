package ru.otus.spring.finalproject.easydesk.dtos.waypoints;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.spring.finalproject.easydesk.dtos.processes.ProcessDto;
import ru.otus.spring.finalproject.easydesk.models.db.Process;
import ru.otus.spring.finalproject.easydesk.models.db.Waypoint;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WaypointDto {

    private String name;

    private ProcessDto process;

    private List<WaypointDtoWithoutNextWaypoints> nextWaypoints;

}
