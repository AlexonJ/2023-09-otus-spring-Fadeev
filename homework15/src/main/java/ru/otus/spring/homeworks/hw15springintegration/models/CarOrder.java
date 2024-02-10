package ru.otus.spring.homeworks.hw15springintegration.models;

import lombok.Builder;
import lombok.Data;
import ru.otus.spring.homeworks.hw15springintegration.models.car.CarColor;
import ru.otus.spring.homeworks.hw15springintegration.models.car.CarModel;

@Builder
@Data
public class CarOrder {

    private int count;

    private CarModel model;

    private CarColor color;

}
