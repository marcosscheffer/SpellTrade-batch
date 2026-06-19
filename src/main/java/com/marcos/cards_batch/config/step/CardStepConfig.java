package com.marcos.cards_batch.config.step;

import java.sql.SQLException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.dao.TransientDataAccessException;
import com.marcos.cards_batch.batch.listener.RetryBatchListener;
import com.marcos.cards_batch.batch.listener.SkipCardListener;
import com.marcos.cards_batch.batch.listener.StepSummaryListener;
import com.marcos.cards_batch.batch.processor.CardProcessor;
import com.marcos.cards_batch.batch.reader.ScryfallStreamReader;
import com.marcos.cards_batch.batch.writer.CardWriter;
import com.marcos.cards_batch.domain.entity.CardJdbc;
import com.marcos.cards_batch.dto.ScryfallCardDto;

@Configuration
public class CardStepConfig {
    private final ScryfallStreamReader scryfallStreamReader;
    private final CardProcessor cardProcessor;
    private final CardWriter cardWriter;
    private final StepSummaryListener stepSummaryListener;
    private final RetryBatchListener retryBatchListener;
    private final SkipCardListener skipCardListener;

    
    public CardStepConfig(ScryfallStreamReader scryfallStreamReader, CardProcessor cardProcessor,
            CardWriter cardWriter, StepSummaryListener stepSummaryListener, 
            RetryBatchListener retryBatchListener, SkipCardListener skipCardListener) {
        this.scryfallStreamReader = scryfallStreamReader;
        this.cardProcessor = cardProcessor;
        this.cardWriter = cardWriter;
        this.stepSummaryListener = stepSummaryListener;
        this.retryBatchListener = retryBatchListener;
        this.skipCardListener = skipCardListener;
    }

    @Bean
    public Step cardStep(JobRepository jobRepository) {
        return new StepBuilder("cardStep", jobRepository)
            .<ScryfallCardDto, CardJdbc>chunk(500)
            .reader(scryfallStreamReader)
            .processor(cardProcessor)
            .writer(cardWriter)
            .faultTolerant()
            .retry(CannotAcquireLockException.class)
            .retry(TransientDataAccessException.class)
            .retry(SQLException.class)
            .retryLimit(3)
            .skip(Exception.class)
            .skipLimit(100)
            .listener(stepSummaryListener)
            .listener(retryBatchListener)
            .listener(skipCardListener)
            .build();
    }
}
