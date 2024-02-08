package ru.otus.spring.bookstore.config.batchconfig;

import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemProcessListener;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.lang.NonNull;
import org.springframework.transaction.PlatformTransactionManager;
import ru.otus.spring.bookstore.models.relational.Author;

import java.util.Map;

import static ru.otus.spring.bookstore.config.batchconfig.SharedBatchConfig.AUTHORS_COLLECTION_NAME;

@Configuration
@RequiredArgsConstructor
public class AuthorsMigrationJobConfig {

    private final Logger logger = LoggerFactory.getLogger("Batch");

    private final JobRepository jobRepository;

    private final PlatformTransactionManager transactionManager;

    private final Map<Long, String> authorRelations;

    @Bean
    public Step migrateAuthorsStep(JpaPagingItemReader<Author> reader,
                                   ItemProcessor<Author, ru.otus.spring.bookstore.models.nonrelational.Author> processor,
                                   MongoItemWriter<ru.otus.spring.bookstore.models.nonrelational.Author> writer) {
        return new StepBuilder("migrateAuthorsStep", jobRepository)
                .<Author, ru.otus.spring.bookstore.models.nonrelational.Author>chunk(5, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .listener(new JobExecutionListener() {
                    @Override
                    public void beforeJob(JobExecution jobExecution) {
                        JobExecutionListener.super.beforeJob(jobExecution);
                        logger.info("start migrating authors");
                    }

                    @Override
                    public void afterJob(JobExecution jobExecution) {
                        JobExecutionListener.super.afterJob(jobExecution);
                        logger.info("stop migrating authors");
                    }

                })
                .listener(new ItemProcessListener<>() {
                    public void beforeProcess(@NonNull Author author) {
                        logger.info("Start processing author %s".formatted(author.getFullName()));
                    }

                    public void afterProcess(@NonNull Author author,
                                             ru.otus.spring.bookstore.models.nonrelational.Author authorNr) {
                        logger.info("Processing author %s finished. Created author %s in mongo DB"
                                .formatted(author, authorNr.getFullName()));
                    }

                    public void onProcessError(@NonNull Author author, @NonNull Exception e) {
                        logger.info("An error occurred while processing author %s".formatted(author));
                    }
                })
                .build();
    }

    @StepScope
    @Bean
    public JpaPagingItemReader<Author> reader(EntityManagerFactory entityManagerFactory) {
        return new JpaPagingItemReaderBuilder<Author>()
                .name("authorsReader")
                .queryString("SELECT author FROM Author author")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(10)
                .build();
    }

    @StepScope
    @Bean
    public ItemProcessor<Author, ru.otus.spring.bookstore.models.nonrelational.Author> processor() {
        return author -> {
            var newAuthor = ru.otus.spring.bookstore.models.nonrelational.Author.builder()
                    .fullName(author.getFullName())
                    .id(ObjectId.get().toString()).build();
            authorRelations.put(author.getId(), newAuthor.getId());
            return newAuthor;
        };
    }

    @StepScope
    @Bean
    public MongoItemWriter<ru.otus.spring.bookstore.models.nonrelational.Author> writer(MongoTemplate mongoTemplate) {
        return new MongoItemWriterBuilder<ru.otus.spring.bookstore.models.nonrelational.Author>()
                .template(mongoTemplate)
                .collection(AUTHORS_COLLECTION_NAME)
                .build();
    }
}
