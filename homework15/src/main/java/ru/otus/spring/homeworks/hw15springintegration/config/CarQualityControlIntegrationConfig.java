package ru.otus.spring.homeworks.hw15springintegration.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.MessageChannelSpec;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.handler.LoggingHandler;
import ru.otus.spring.homeworks.hw15springintegration.models.car.Car;

@Configuration
public class CarQualityControlIntegrationConfig {

    public static final String QUALITY_CONTROL_CHANNEL_NAME = "qualityControlChannel";

    public static final String DEFECTIVE_CARS_CHANNEL_NAME = "defectiveCarsChannel";

    public static final String NON_DEFECTIVE_CARS_CHANNEL_NAME = "nonDefectiveCarsChannel";


    @Bean
    public MessageChannelSpec<?, ?> qualityControlChannel() {
        return MessageChannels.publishSubscribe();
    }

    @Bean
    public MessageChannelSpec<?, ?> nonDefectiveCarsChannel() {
        return MessageChannels.publishSubscribe();
    }

    @Bean
    public MessageChannelSpec<?, ?> defectiveCarsChannel() {
        return MessageChannels.publishSubscribe();
    }

    @Bean
    IntegrationFlow carQualityControlFlow() {
        return IntegrationFlow.from(QUALITY_CONTROL_CHANNEL_NAME)
                .<Car, String>route(car -> {
                    if (car.getIsDefective()) {
                        return DEFECTIVE_CARS_CHANNEL_NAME;
                    } else {
                        return NON_DEFECTIVE_CARS_CHANNEL_NAME;
                    }
                })
                .get();
    }

    @Bean
    IntegrationFlow fixingDefectiveCarFlow() {
        return IntegrationFlow.from(DEFECTIVE_CARS_CHANNEL_NAME)
                .<Car>handle((payload, headers) -> {
                    payload.setIsDefective(false);
                    return payload;
                })
                .log(LoggingHandler.Level.INFO, "", message -> "Defective car fixed: %s".formatted(message.getPayload()))
                .channel(NON_DEFECTIVE_CARS_CHANNEL_NAME)
                .get();
        }

}
