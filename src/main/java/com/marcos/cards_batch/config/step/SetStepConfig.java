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
import com.marcos.cards_batch.batch.listener.SkipSetListener;
import com.marcos.cards_batch.batch.listener.StepSummaryListener;
import com.marcos.cards_batch.batch.processor.SetProcessor;
import com.marcos.cards_batch.batch.reader.ScryfallStreamReader;
import com.marcos.cards_batch.batch.writer.SetWriter;
import com.marcos.cards_batch.domain.entity.CardSetJdbc;
import com.marcos.cards_batch.dto.ScryfallCardDto;

@Configuration
public class SetStepConfig {
    private final ScryfallStreamReader scryfallStreamReader;
    private final SetProcessor setProcessor;
    private final SetWriter setWriter;
    private final StepSummaryListener stepSummaryListener;
    private final RetryBatchListener retryBatchListener;
    private final SkipSetListener skipSetListener;

    
    public SetStepConfig(ScryfallStreamReader scryfallStreamReader, SetProcessor setProcessor,
            SetWriter setWriter, StepSummaryListener stepSummaryListener, 
            RetryBatchListener retryBatchListener, SkipSetListener skipSetListener) {
        this.scryfallStreamReader = scryfallStreamReader;
        this.setProcessor = setProcessor;
        this.setWriter = setWriter;
        this.stepSummaryListener = stepSummaryListener;
        this.retryBatchListener = retryBatchListener;
        this.skipSetListener = skipSetListener;
    }

    @Bean 
    public Step setStep(JobRepository jobRepository) {
        return new StepBuilder("setStep", jobRepository)
            .<ScryfallCardDto, CardSetJdbc>chunk(500)
            .reader(scryfallStreamReader)
            .processor(setProcessor)
            .writer(setWriter)
            .faultTolerant()
            .retry(CannotAcquireLockException.class)
            .retry(TransientDataAccessException.class)
            .retry(SQLException.class)
            .retryLimit(3)
            .skip(Exception.class)
            .skipLimit(100)
            .listener(stepSummaryListener)
            .listener(retryBatchListener)
            .listener(skipSetListener)
            .build();
    }
}
