package ru.otus.spring.bookstore.actuators;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.Data;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;
import ru.otus.spring.bookstore.BookstoreApp;

@Component
@Data
@Aspect
public class DataBaseHealthIndicator  implements HealthIndicator {

    private final Logger logger = LoggerFactory.getLogger(BookstoreApp.class);

    private final Counter totalEntitiesChanged;

    private final Counter totalEntitiesAdded;

    private final Counter totalEntitiesDeleted;

    @Autowired
    public DataBaseHealthIndicator(MeterRegistry registry) {

        totalEntitiesChanged = Counter.builder("totalEntitiesChanged")
                .description("Number of changed entities")
                .register(registry);

        totalEntitiesAdded = Counter.builder("totalEntitiesAdded")
                .description("Number of saved entities")
                .register(registry);

        totalEntitiesDeleted = Counter.builder("totalEntitiesDeleted")
                .description("Number of deleted entities")
                .register(registry);
    }

    @Override
    public Health health() {
        return Health.up()
                .status(Status.UP)
                .withDetail("Number of changed entities: ", totalEntitiesChanged.count())
                .withDetail("Number of added entities: ", totalEntitiesAdded.count())
                .withDetail("Number of deleted entities: ", totalEntitiesDeleted.count())
                .build();
    }

    public void increaseChangedEntities() {
        totalEntitiesChanged.increment();
        logger.info("Entity changed");
    }

    public void increaseAddedEntities() {
        totalEntitiesAdded.increment();
        logger.info("Entity added");
    }

    public void increaseDeletedEntities() {
        totalEntitiesDeleted.increment();
        logger.info("Entity deleted");
    }

    @Pointcut("execution(* ru.otus.spring.bookstore.services.AuthorService.insert(..)) ||" +
              "execution(* ru.otus.spring.bookstore.services.BookService.insert(..)) ||" +
              "execution(* ru.otus.spring.bookstore.services.CommentService.insert(..))")
    private void insertMethod() {

    }

    @Pointcut("execution(* ru.otus.spring.bookstore.services.AuthorService.update(..)) ||" +
              "execution(* ru.otus.spring.bookstore.services.BookService.update(..)) ||" +
              "execution(* ru.otus.spring.bookstore.services.CommentService.updateById(..))")
    private void updateMethod() {

    }

    @Pointcut("execution(* ru.otus.spring.bookstore.services.AuthorService.deleteById(..)) ||" +
              "execution(* ru.otus.spring.bookstore.services.BookService.deleteById(..)) ||" +
              "execution(* ru.otus.spring.bookstore.services.CommentService.deleteById(..))")
    private void deleteMethod() {

    }

    @AfterReturning("insertMethod()")
    private void countInsertMethodCall() {
        increaseAddedEntities();
    }

    // Advice for update method
    @AfterReturning("updateMethod()")
    private void countUpdateMethodCall() {
        increaseChangedEntities();
    }

    // Advice for delete method
    @AfterReturning("deleteMethod()")
    private void countDeleteMethodCall() {
        increaseDeletedEntities();
    }

}
