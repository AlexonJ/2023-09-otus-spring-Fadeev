package ru.otus.spring.finalproject.easydesk.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.MeterBinder;
import lombok.Data;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Data
@Aspect
public class DataBaseMetricsBinder implements MeterBinder {

    private final Logger logger = LoggerFactory.getLogger(DataBaseMetricsBinder.class);

    private Counter totalEntitiesChangedCounter;
    private Counter totalEntitiesAddedCounter;
    private Counter totalEntitiesDeletedCounter;

    @Override
    public void bindTo(MeterRegistry meterRegistry) {
        totalEntitiesAddedCounter = meterRegistry.counter("database_total_added_entities", "description", "Total number of added entities");
        totalEntitiesChangedCounter = meterRegistry.counter("database_total_changed_entities", "description", "Total number of changed entities");
        totalEntitiesDeletedCounter = meterRegistry.counter("database_total_deleted_entities", "description", "Total number of deleted entities");

    }

    public void increaseChangedEntities() {
        totalEntitiesChangedCounter.increment();
        logger.info("Entity changed");
    }

    public void increaseAddedEntities() {
        totalEntitiesAddedCounter.increment();
        logger.info("Entity added");
    }

    public void increaseDeletedEntities() {
        totalEntitiesDeletedCounter.increment();
        logger.info("Entity deleted");
    }

    @Pointcut("execution(* ru.otus.spring.finalproject.easydesk.services.TicketService.createTicket(..)) ||" +
              "execution(* ru.otus.spring.finalproject.easydesk.services.CommentService.createByTicketCode(..)) ||" +
              "execution(* ru.otus.spring.finalproject.easydesk.services.AttachmentService.uploadByTicketCode(..))")
    private void insertMethod() {

    }

    @Pointcut("execution(* ru.otus.spring.finalproject.easydesk.services.TicketService.saveTicket(..)) ||" +
              "execution(* ru.otus.spring.finalproject.easydesk.services.CommentService.updateById(..))")
    private void updateMethod() {

    }

    @Pointcut("execution(* ru.otus.spring.finalproject.easydesk.services.TicketService.deleteByCode(..)) ||" +
              "execution(* ru.otus.spring.finalproject.easydesk.services.CommentService.deleteById(..)) ||" +
              "execution(* ru.otus.spring.finalproject.easydesk.services.AttachmentService.deleteById(..))")
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
