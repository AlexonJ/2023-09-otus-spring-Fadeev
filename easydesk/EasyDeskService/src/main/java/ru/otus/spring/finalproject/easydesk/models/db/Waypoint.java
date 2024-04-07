package ru.otus.spring.finalproject.easydesk.models.db;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "waypoints")
public class Waypoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    @ManyToOne
    private Process process;

    @ManyToMany(targetEntity = Waypoint.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "waypoints_relations",
            joinColumns = @JoinColumn(name = "waypoint_id"),
            inverseJoinColumns = @JoinColumn(name = "next_waypoint_id")
    )
    private List<Waypoint> nextWaypoints;

    public Boolean isLastWaypoint() {
        return nextWaypoints.isEmpty();
    }
    @Override
    public String toString() {
        return "%s (id = %d)".formatted(name, getId());
    }
}
