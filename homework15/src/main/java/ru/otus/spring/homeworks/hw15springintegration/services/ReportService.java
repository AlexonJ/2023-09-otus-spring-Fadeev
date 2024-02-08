package ru.otus.spring.homeworks.hw15springintegration.services;

import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Service;

import static ru.otus.spring.homeworks.hw15springintegration.config.CarQualityControlIntegrationConfig.QUALITY_CONTROL_CHANNEL_NAME;
import static ru.otus.spring.homeworks.hw15springintegration.config.CarQualityControlIntegrationConfig.NON_DEFECTIVE_CARS_CHANNEL_NAME;
import static ru.otus.spring.homeworks.hw15springintegration.config.CarQualityControlIntegrationConfig.DEFECTIVE_CARS_CHANNEL_NAME;
import static ru.otus.spring.homeworks.hw15springintegration.config.DeliveryIntegrationConfig.DELIVERY_CARS_CHANNEL_NAME;

@Service
public class ReportService {

    private int producedCars;

    private int goodCars;

    private int defectiveCars;

    @ServiceActivator(inputChannel = QUALITY_CONTROL_CHANNEL_NAME)
    public void producedCarsCounter() {
        producedCars ++;
    }

    @ServiceActivator(inputChannel = NON_DEFECTIVE_CARS_CHANNEL_NAME)
    public void goodCarsCounter() {
        goodCars ++;
    }

    @ServiceActivator(inputChannel = DEFECTIVE_CARS_CHANNEL_NAME)
    public void defectiveCarsCounter() {
        defectiveCars ++;
    }

    @ServiceActivator(inputChannel = DELIVERY_CARS_CHANNEL_NAME)
    public void showReport() {
        System.out.printf("Good cars produced: %d%n", goodCars);
        System.out.printf("Defective cars produced: %d%n", defectiveCars);
        System.out.printf("Total cars produced: %d%n", producedCars);
    }

}
