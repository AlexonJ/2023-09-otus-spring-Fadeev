package ru.otus.spring.homeworks.hw15springintegration.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.MessageChannelSpec;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.handler.LoggingHandler;
import ru.otus.spring.homeworks.hw15springintegration.models.CarBatch;
import ru.otus.spring.homeworks.hw15springintegration.models.car.Car;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
public class DeliveryIntegrationConfig {

    public static final String DELIVERY_CARS_CHANNEL_NAME = "deliveryCarsChannel";

    @Bean
    public MessageChannelSpec<?, ?> deliveryCarsChannel() {
        return MessageChannels.publishSubscribe();
    }

    @Bean
    public IntegrationFlow deliveryCarsFlow() {
        return IntegrationFlow.from(CarQualityControlIntegrationConfig.NON_DEFECTIVE_CARS_CHANNEL_NAME)
                .log(LoggingHandler.Level.INFO, "", message -> "car %s is waiting for delivery".formatted(message.getPayload()))
                .aggregate(aggregator -> aggregator
                        .expireGroupsUponCompletion(true)
                        .groupTimeout(100))
                .log(LoggingHandler.Level.INFO, "",
                        message -> "Set of cars %s is waiting for delivery".formatted(message.getPayload()))
                .<List<Car>, CarBatch>transform(cars ->
                        CarBatch.builder().dateTime(LocalDateTime.now()).cars(cars).build())
                .log(LoggingHandler.Level.INFO, "",
                        message -> "batch %s is ready for delivery".formatted(message.getPayload()))
                .channel(DELIVERY_CARS_CHANNEL_NAME)
                .get();
    }

}
