package ru.otus.spring.bookstore.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.Objects;

import static ru.otus.spring.bookstore.config.batchconfig.MigrationJobConfig.MIGRATE_DATA_JOB_NAME;

@RequiredArgsConstructor
@ShellComponent
public class BatchCommands {

    private final Job migrateDataJob;

    private final JobLauncher jobLauncher;

    private final JobOperator jobOperator;

    private final JobExplorer jobExplorer;

    @ShellMethod(value = "startMigrationFromPostgresToMongoLauncher", key = "migrate")
    public void startMigrationFromPostgresToMongoLauncher() throws JobExecutionException {
        if (!jobOperator.getRunningExecutions(MIGRATE_DATA_JOB_NAME).isEmpty()) {
            System.out.printf("Job %s already running%n", MIGRATE_DATA_JOB_NAME);
        } else if (!Objects.isNull(jobExplorer.getLastJobInstance(MIGRATE_DATA_JOB_NAME))) {
            jobOperator.startNextInstance(MIGRATE_DATA_JOB_NAME);
        } else {
            JobExecution execution = jobLauncher.run(migrateDataJob, new JobParameters());
            System.out.println(execution);
        }
    }

    @ShellMethod(value = "showInfo", key = "i")
    public void showInfo() {
        var jobNames = jobExplorer.getJobNames();
        for (String jobName : jobNames) {
            System.out.println(jobName);
            for (JobInstance jobInstance : jobExplorer.getJobInstances(jobName,
                    Math.max(jobNames.size() - 10, 0), 10)) {
                System.out.println(jobInstance);
                for (JobExecution jobExecution : jobExplorer.getJobExecutions(jobInstance)) {
                    System.out.println(jobExecution);
                }
            }
        }

    }

    @ShellMethod(value = "restart (only if fail)", key = "r")
    public void restart(long id) throws JobExecutionException {
        JobExecution jobExecution = jobExplorer.getJobExecution(id);
        if (Objects.isNull(jobExecution)) {
            System.out.printf("JobExecution with ID %s not found.%n", id);
        }
        jobOperator.restart(id);
    }
}
