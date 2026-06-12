package com.marcos.cards_batch.config;

import java.time.LocalDateTime;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.batch.core.job.parameters.JobParametersBuilder;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JobScheduler {
    private final JobOperator jobOperator;
    private final Job importScryfallCardsJob;

    public JobScheduler(Job importScryfallCardsJob, JobOperator jobOperator) {
        this.jobOperator = jobOperator;
        this.importScryfallCardsJob = importScryfallCardsJob;
    }

    @Scheduled(fixedRate = 60000)
    public void runImportJob() {
        try {
            JobParameters params = new JobParametersBuilder()
                .addLocalDateTime("runAt", LocalDateTime.now())
                .toJobParameters();

            log.info("Running scryfall import at {}", LocalDateTime.now());
            jobOperator.start(importScryfallCardsJob, params);

        } catch (Exception e) {
            log.error("Error running import job", e);
        }
    }
}
