package ru.otus.spring.homeworks.hw15springintegration.models;

import lombok.Builder;
import lombok.Data;
import ru.otus.spring.homeworks.hw15springintegration.models.car.Car;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class CarBatch {

    private LocalDateTime dateTime;

    private List<Car> cars;

}
