package ru.otus.spring.homeworks.hw15springintegration.services;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.spring.homeworks.hw15springintegration.models.CarBatch;
import ru.otus.spring.homeworks.hw15springintegration.models.CarOrder;

import static ru.otus.spring.homeworks.hw15springintegration.config.CarFactoryIntegrationConfig.ORDERS_CHANNEL_NAME;
import static ru.otus.spring.homeworks.hw15springintegration.config.DeliveryIntegrationConfig.DELIVERY_CARS_CHANNEL_NAME;

@MessagingGateway
public interface CarFactory {

    @Gateway(requestChannel = ORDERS_CHANNEL_NAME, replyChannel = DELIVERY_CARS_CHANNEL_NAME)
    CarBatch produce(CarOrder carOrder);

}
