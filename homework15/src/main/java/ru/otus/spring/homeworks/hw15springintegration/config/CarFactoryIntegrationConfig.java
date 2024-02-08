package ru.otus.spring.homeworks.hw15springintegration.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.MessageChannelSpec;
import org.springframework.integration.dsl.MessageChannels;
import ru.otus.spring.homeworks.hw15springintegration.services.CarFactoryService;

import static ru.otus.spring.homeworks.hw15springintegration.config.CarQualityControlIntegrationConfig.QUALITY_CONTROL_CHANNEL_NAME;

@Slf4j
@Configuration
public class CarFactoryIntegrationConfig {

    public static final String ORDERS_CHANNEL_NAME = "ordersChannel";

    @Bean
    public MessageChannelSpec<?, ?> ordersChannel() {
        return MessageChannels.queue(5);
    }

    @Bean
    public MessageChannelSpec<?, ?> carsConsumerChannel() {
        return MessageChannels.publishSubscribe();
    }

    @Bean
    public IntegrationFlow factoryFlow(CarFactoryService carFactoryService) {
        return IntegrationFlow.from(ORDERS_CHANNEL_NAME)
                .handle(carFactoryService, "produce")
                .split()
                .channel(QUALITY_CONTROL_CHANNEL_NAME)
                .get();
    }

}
