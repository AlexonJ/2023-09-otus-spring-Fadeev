package ru.otus.spring.bookstore.actuators;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;
import ru.otus.spring.bookstore.controllers.ControllerAdvice;

@Component
@RequiredArgsConstructor
public class ExceptionHealthIndicator implements HealthIndicator {

    private final ControllerAdvice controllerAdvice;

    @Override
    public Health health() {

        var status = Status.UP;
        if (controllerAdvice.getTotalExceptionsCounter() != 0) {
            status = Status.DOWN;
        }

        return Health.up()
                .status(status)
                .withDetail("Entity not found exceptions number", controllerAdvice.getExceptionNotFoundCounter())
                .withDetail("Total exceptions number", controllerAdvice.getTotalExceptionsCounter())
                .build();
    }
}
