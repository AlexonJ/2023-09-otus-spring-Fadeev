package ru.otus.spring.homeworks.hw15springintegration.services;

import org.springframework.stereotype.Component;
import ru.otus.spring.homeworks.hw15springintegration.models.CarOrder;
import ru.otus.spring.homeworks.hw15springintegration.models.car.Car;

import java.util.Collection;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class CarFactoryServiceImpl implements CarFactoryService {

    @Override
    public Collection<Car> produce(CarOrder order) {
        return IntStream.range(1, order.getCount() + 1)
                .mapToObj(orderNumber ->
                        Car.builder()
                                .vin(generateRandomVIN())
                                .model(order.getModel())
                                .color(order.getColor())
                                .isDefective(Math.random() > 0.6)
                                .build()).collect(Collectors.toList());
    }


    private static String generateRandomVIN() {
        StringBuilder vinBuilder = new StringBuilder();
        Random random = new Random();

        char firstChar = (char) ('A' + random.nextInt(26));
        vinBuilder.append(firstChar);

        for (int i = 0; i < 16; i++) {
            int randomInt = random.nextInt(36);
            if (randomInt < 10) {
                vinBuilder.append(randomInt);
            } else {
                vinBuilder.append((char) ('A' + (randomInt - 10)));
            }
        }

        return vinBuilder.toString();
    }
}
