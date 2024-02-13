package ru.otus.spring.bookstore.actuators;

import lombok.Data;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private long totalEntitiesChanged;

    private long totalEntitiesAdded;

    private long totalEntitiesDeleted;

    @Override
    public Health health() {
        return Health.up()
                .status(Status.UP)
                .withDetail("Number of changed entities: ", totalEntitiesChanged)
                .withDetail("Number of added entities: ", totalEntitiesAdded)
                .withDetail("Number of deleted entities: ", totalEntitiesDeleted)
                .build();
    }

    public void increaseChangedEntities() {
        totalEntitiesChanged++;
        logger.info("Entity changed");
    }

    public void increaseAddedEntities() {
        totalEntitiesAdded++;
        logger.info("Entity added");
    }

    public void increaseDeletedEntities() {
        totalEntitiesDeleted++;
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

    @AfterReturning("updateMethod()")
    private void countUpdateMethodCall() {
        increaseChangedEntities();
    }

    @AfterReturning("deleteMethod()")
    private void countDeleteMethodCall() {
        increaseDeletedEntities();
    }

}
