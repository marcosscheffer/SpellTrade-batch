package com.marcos.cards_batch.config.step;

import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.marcos.cards_batch.batch.tasklet.ScryfallDownloadTasklet;

@Configuration
public class DownloadCardStepConfig {
    private final ScryfallDownloadTasklet scryfallDownloadTasklet;


    public DownloadCardStepConfig(ScryfallDownloadTasklet scryfallDownloadTasklet) {
        this.scryfallDownloadTasklet = scryfallDownloadTasklet;
    }

    @Bean
    public Step downloadCardStep(JobRepository jobRepository) {
        return new StepBuilder("downloadCardStep", jobRepository)
            .tasklet(scryfallDownloadTasklet)
            .build();
    }
}
