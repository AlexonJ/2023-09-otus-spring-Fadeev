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
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.lang.NonNull;
import org.springframework.transaction.PlatformTransactionManager;
import ru.otus.spring.bookstore.mappers.EntityMapper;
import ru.otus.spring.bookstore.models.relational.Author;
import ru.otus.spring.bookstore.models.relational.Book;

import java.util.Map;
import java.util.Optional;

import static ru.otus.spring.bookstore.config.batchconfig.SharedBatchConfig.AUTHORS_COLLECTION_NAME;
import static ru.otus.spring.bookstore.config.batchconfig.SharedBatchConfig.BOOKS_COLLECTION_NAME;

@Configuration
@RequiredArgsConstructor
public class BooksMigrationJobConfig {

    private final Logger logger = LoggerFactory.getLogger("Batch");

    private final JobRepository jobRepository;

    private final PlatformTransactionManager transactionManager;

    private final EntityMapper entityMapper;

    private final Map<Long, String> authorRelations;

    private final Map<Long, String> bookRelations;

    @Bean
    public Step migrateBooksStep(JpaPagingItemReader<Book> reader,
                                 ItemProcessor<Book, ru.otus.spring.bookstore.models.nonrelational.Book> processor,
                                 MongoItemWriter<ru.otus.spring.bookstore.models.nonrelational.Book> writer) {
        return new StepBuilder("migrateBooksStep", jobRepository)
                .<Book, ru.otus.spring.bookstore.models.nonrelational.Book>chunk(5, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .listener(new JobExecutionListener() {

                    @Override
                    public void beforeJob(JobExecution jobExecution) {
                        JobExecutionListener.super.beforeJob(jobExecution);
                        logger.info("Start migrating books job");
                    }

                    @Override
                    public void afterJob(JobExecution jobExecution) {
                        JobExecutionListener.super.afterJob(jobExecution);
                        logger.info("Stop migrating books job");
                    }

                })
                .listener(new ItemProcessListener<>() {
                    public void beforeProcess(@NonNull Book book) {
                        logger.info("Start processing book %s".formatted(book.toString()));
                    }

                    public void afterProcess(@NonNull Book book, ru.otus.spring.bookstore.models.nonrelational.Book bookNr) {
                        logger.info("Processing book %s finished. Created book %s in mongo DB".formatted(book, bookNr));
                    }

                    public void onProcessError(@NonNull Book book, @NonNull Exception e) {
                        logger.info("An error occurred while processing book %s".formatted(book));
                    }
                })
                .build();
    }

    @StepScope
    @Bean
    public JpaPagingItemReader<Book> booksReader(EntityManagerFactory entityManagerFactory) {
        return new JpaPagingItemReaderBuilder<Book>()
                .name("booksReader")
                .queryString("SELECT book FROM Book book")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(5)
                .build();
    }

    @StepScope
    @Bean
    public ItemProcessor<Book, ru.otus.spring.bookstore.models.nonrelational.Book> bookProcessor(MongoTemplate mongoTemplate) {
        return book -> {
            var bookMongo = entityMapper.bookToBookNonRelational(book);
            Author author = book.getAuthor();

            var authorMongo = Optional.ofNullable(authorRelations.get(author.getId())).map(authorId -> {
                Query query = new Query(Criteria.where("id").is(authorId));
                return mongoTemplate.findOne(query,
                        ru.otus.spring.bookstore.models.nonrelational.Author.class, AUTHORS_COLLECTION_NAME);
            }).orElseThrow();

            bookMongo.setAuthor(authorMongo);
            bookMongo.setId(ObjectId.get().toString());
            bookRelations.put(book.getId(), bookMongo.getId());

            return bookMongo;

        };
    }

    @StepScope
    @Bean
    public MongoItemWriter<ru.otus.spring.bookstore.models.nonrelational.Book> booksWriter(MongoTemplate mongoTemplate) {
        return new MongoItemWriterBuilder<ru.otus.spring.bookstore.models.nonrelational.Book>()
                .template(mongoTemplate)
                .collection(BOOKS_COLLECTION_NAME)
                .build();
    }
}
