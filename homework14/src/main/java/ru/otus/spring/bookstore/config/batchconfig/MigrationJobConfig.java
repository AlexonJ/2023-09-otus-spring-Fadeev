package ru.otus.spring.bookstore.config.batchconfig;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.transaction.PlatformTransactionManager;

import static ru.otus.spring.bookstore.config.batchconfig.SharedBatchConfig.AUTHORS_COLLECTION_NAME;
import static ru.otus.spring.bookstore.config.batchconfig.SharedBatchConfig.BOOKS_COLLECTION_NAME;

@RequiredArgsConstructor
@Configuration
public class MigrationJobConfig {

    public static final String MIGRATE_DATA_JOB_NAME = "migrateDataJob";

    private final Logger logger = LoggerFactory.getLogger("Batch");

    private final JobRepository jobRepository;

    private final PlatformTransactionManager transactionManager;

    @Bean
    public Job migrateDataJob(Step startUpStep, Step migrateAuthorsStep, Step migrateBooksStep) {
        logger.info("Starting data migration job ...");
        return new JobBuilder(MIGRATE_DATA_JOB_NAME, jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(startUpStep).on("FAILED").end()
                .next(migrateAuthorsStep).on("FAILED").end()
                .next(migrateBooksStep)
                .end()
                .build();
    }

    @Bean
    public Step startUpStep(MongoTemplate mongoTemplate) {
        return new StepBuilder("startUpStep", jobRepository)
                .tasklet(startUpTasklet(mongoTemplate), transactionManager)
                .build();
    }

    @Bean
    public Tasklet startUpTasklet(MongoTemplate mongoTemplate) {
        return (contribution, chunkContext) -> {
            logger.info("Cleaning up MongoDB collections before migration...");

            // Perform cleanup operations for MongoDB collections
            mongoTemplate.remove(new Query(), ru.otus.spring.bookstore.models.nonrelational.Author.class, AUTHORS_COLLECTION_NAME);
            mongoTemplate.remove(new Query(), ru.otus.spring.bookstore.models.nonrelational.Book.class, BOOKS_COLLECTION_NAME);

            logger.info("MongoDB cleanup completed.");
            return RepeatStatus.FINISHED;
        };
    }

}
