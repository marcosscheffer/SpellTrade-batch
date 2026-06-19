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
import com.marcos.cards_batch.batch.flatten.ColorIdentityFlatten;
import com.marcos.cards_batch.batch.listener.RetryBatchListener;
import com.marcos.cards_batch.batch.listener.SkipColorIdentityListener;
import com.marcos.cards_batch.batch.listener.StepSummaryListener;
import com.marcos.cards_batch.batch.processor.ColorIdentityProcessor;
import com.marcos.cards_batch.batch.reader.ScryfallStreamReader;
import com.marcos.cards_batch.domain.entity.ColorIdentityJdbc;
import com.marcos.cards_batch.dto.ScryfallCardDto;

@Configuration
public class ColorIdentityStepConfig {
    private final ScryfallStreamReader scryfallStreamReader;
    private final ColorIdentityProcessor colorIdentityProcessor;
    private final ColorIdentityFlatten colorIdentityFlatten;
    private final StepSummaryListener stepSummaryListener;
    private final RetryBatchListener retryBatchListener;
    private final SkipColorIdentityListener skipColorIdentityListener;

    
    public ColorIdentityStepConfig(ScryfallStreamReader scryfallStreamReader,
            ColorIdentityProcessor colorIdentityProcessor,
            ColorIdentityFlatten colorIdentityFlatten, StepSummaryListener stepSummaryListener, 
            RetryBatchListener batchListener, SkipColorIdentityListener colorIdentityListener) {
        this.scryfallStreamReader = scryfallStreamReader;
        this.colorIdentityProcessor = colorIdentityProcessor;
        this.colorIdentityFlatten = colorIdentityFlatten;
        this.stepSummaryListener = stepSummaryListener;
        this.retryBatchListener = batchListener;
        this.skipColorIdentityListener = colorIdentityListener;
    }

    @Bean
    public Step colorIdentityStep(JobRepository jobRepository) {
        return new StepBuilder("colorIdentityStep", jobRepository)
            .<ScryfallCardDto, List<ColorIdentityJdbc>>chunk(500)
            .reader(scryfallStreamReader)
            .processor(colorIdentityProcessor)
            .writer(colorIdentityFlatten)
            .faultTolerant()
            .retry(CannotAcquireLockException.class)
            .retry(TransientDataAccessException.class)
            .retry(SQLException.class)
            .retryLimit(3)
            .skip(Exception.class)
            .skipLimit(100)
            .listener(stepSummaryListener)
            .listener(retryBatchListener)
            .listener(skipColorIdentityListener)
            .build();
    }
}
