package ru.otus.spring.homeworks.hw15springintegration.services;

import ru.otus.spring.homeworks.hw15springintegration.models.CarOrder;
import ru.otus.spring.homeworks.hw15springintegration.models.car.Car;

import java.util.Collection;

public interface CarFactoryService {
    Collection<Car> produce(CarOrder order);
}
