package com.marcos.cards_batch.config.step;

import java.sql.SQLException;
import java.util.List;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.dao.TransientDataAccessException;
import com.marcos.cards_batch.batch.flatten.LegalityFlatten;
import com.marcos.cards_batch.batch.listener.RetryBatchListener;
import com.marcos.cards_batch.batch.listener.SkipLegalityListener;
import com.marcos.cards_batch.batch.listener.StepSummaryListener;
import com.marcos.cards_batch.batch.processor.LegalityProcessor;
import com.marcos.cards_batch.batch.reader.ScryfallStreamReader;
import com.marcos.cards_batch.domain.entity.CardLegalityJdbc;
import com.marcos.cards_batch.dto.ScryfallCardDto;

@Configuration
public class LegalityStepConfig {
    private final ScryfallStreamReader scryfallStreamReader;
    private final LegalityProcessor legalitiesProcessor;
    private final LegalityFlatten legalitiesFlatten;
    private final StepSummaryListener stepSummaryListener;
    private final RetryBatchListener retryBatchListener;
    private final SkipLegalityListener skipLegalityListener;

    
    public LegalityStepConfig(ScryfallStreamReader scryfallStreamReader,
            LegalityProcessor legalitiesProcessor, LegalityFlatten legalitiesFlatten, 
            StepSummaryListener stepSummaryListener, RetryBatchListener retryBatchListener, 
            SkipLegalityListener skipLegalityListener) {
        this.scryfallStreamReader = scryfallStreamReader;
        this.legalitiesProcessor = legalitiesProcessor;
        this.legalitiesFlatten = legalitiesFlatten;
        this.stepSummaryListener = stepSummaryListener;
        this.retryBatchListener = retryBatchListener;
        this.skipLegalityListener = skipLegalityListener;
    }

    @Bean
    public Step legalityStep(JobRepository jobRepository) {
        return new StepBuilder("legalityStep", jobRepository)
            .<ScryfallCardDto, List<CardLegalityJdbc>>chunk(500)
            .reader(scryfallStreamReader)
            .processor(legalitiesProcessor)
            .writer(legalitiesFlatten)
            .faultTolerant()
            .retry(CannotAcquireLockException.class)
            .retry(TransientDataAccessException.class)
            .retry(SQLException.class)
            .retryLimit(3)
            .skip(Exception.class)
            .skipLimit(100)
            .listener(stepSummaryListener)
            .listener(retryBatchListener)
            .listener(skipLegalityListener)
            .build();
    }
}
