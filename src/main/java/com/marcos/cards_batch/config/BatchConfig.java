package com.marcos.cards_batch.config;

import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.marcos.cards_batch.batch.listener.JobSummaryListener;


@Configuration
public class BatchConfig {
    @Bean
    public Job importScryfallCardsJob(
        JobRepository jobRepository,
        JobSummaryListener jobSummaryListener,
        @Qualifier("downloadCardStep") Step downloadCardStep,
        @Qualifier("setStep") Step setStep,
        @Qualifier("cardStep") Step cardStep,
        @Qualifier("colorIdentityStep") Step colorIdentityStep,
        @Qualifier("cardFaceStep") Step cardFaceStep,
        @Qualifier("imageStep") Step imageStep,
        @Qualifier("legalityStep") Step legalityStep
    ) {
        return new JobBuilder("importScryfallCardsJob", jobRepository)
            .listener(jobSummaryListener)
            .start(downloadCardStep)
            .next(setStep)
            .next(cardStep)
            .next(colorIdentityStep)
            .next(cardFaceStep)
            .next(imageStep)
            .next(legalityStep)
            .build();
    }
}
