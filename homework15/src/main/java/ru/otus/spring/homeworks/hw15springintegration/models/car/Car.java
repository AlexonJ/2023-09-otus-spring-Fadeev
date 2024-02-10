package ru.otus.spring.homeworks.hw15springintegration.models.car;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Car {

    private String vin;

    private CarModel model;

    private CarColor color;

    private Boolean isDefective;

}
