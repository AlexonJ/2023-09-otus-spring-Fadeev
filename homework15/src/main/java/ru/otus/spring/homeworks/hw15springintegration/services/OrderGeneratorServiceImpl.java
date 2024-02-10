package ru.otus.spring.homeworks.hw15springintegration.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.spring.homeworks.hw15springintegration.models.CarBatch;
import ru.otus.spring.homeworks.hw15springintegration.models.CarOrder;
import ru.otus.spring.homeworks.hw15springintegration.models.car.CarColor;
import ru.otus.spring.homeworks.hw15springintegration.models.car.CarModel;

import java.util.concurrent.ForkJoinPool;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderGeneratorServiceImpl implements OrderGeneratorService {

    private final CarFactory carFactory;

    @Override
    public void startGeneratingOrders() {

        ForkJoinPool pool = ForkJoinPool.commonPool();
        pool.execute(() -> {
            CarOrder newCarOrder = CarOrder.builder()
                    .model(CarModel.AUDI_A4).color(CarColor.BLACK).count(5).build();
            log.info("New order generated: {}", newCarOrder);
            CarBatch carBatch = carFactory.produce(newCarOrder);
            log.info("New cars produced: {}", carBatch.getCars());
        });

    }
}
