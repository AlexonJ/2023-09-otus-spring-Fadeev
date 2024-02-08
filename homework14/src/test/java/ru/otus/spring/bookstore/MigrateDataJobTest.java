package ru.otus.spring.bookstore;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.spring.bookstore.models.nonrelational.Author;
import ru.otus.spring.bookstore.models.nonrelational.Book;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.otus.spring.bookstore.config.batchconfig.MigrationJobConfig.MIGRATE_DATA_JOB_NAME;

@SpringBootTest
@SpringBatchTest
@ActiveProfiles("test")
class MigrateDataJobTest {


    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;

    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeEach
    void clearMetaData() {
        TestDataHolder.prepareTestData();
        jobRepositoryTestUtils.removeJobExecutions();
    }

    @Test
    @DisplayName("Testing job data migration")
    void testJob() throws Exception {

        var expectedBooks = TestDataHolder.getBooks();
        var expectedAuthors = TestDataHolder.getAuthors();
        Job job = jobLauncherTestUtils.getJob();

        assertThat(job).isNotNull()
                .extracting(Job::getName)
                .isEqualTo(MIGRATE_DATA_JOB_NAME);

        JobExecution jobExecution = jobLauncherTestUtils.launchJob();
        assertThat(jobExecution.getExitStatus().getExitCode()).isEqualTo("COMPLETED");

        var authors = mongoTemplate.findAll(Author.class);
        assertThat(authors.size()).isEqualTo(expectedAuthors.size());
        for (int i = 0; i < authors.size(); i++) {
            Assertions.assertNotNull(authors.get(i));
            Assertions.assertEquals(authors.get(i).getFullName(), expectedAuthors.get(i).getFullName());
        }

        var books = mongoTemplate.findAll(Book.class);
        assertThat(books.size()).isEqualTo(expectedBooks.size());
        for (int i = 0; i < books.size(); i++) {
            Assertions.assertNotNull(books.get(i));
            Assertions.assertEquals(books.get(i).getTitle(), expectedBooks.get(i).getTitle());
            Assertions.assertEquals(books.get(i).getAuthor().getFullName(), expectedBooks.get(i).getAuthor().getFullName());
            Assertions.assertEquals(books.get(i).getGenres().size(), expectedBooks.get(i).getGenres().size());
        }
    }
}
