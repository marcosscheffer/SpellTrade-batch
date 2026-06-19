package com.marcos.cards_batch.config.step;

import java.sql.SQLException;
import java.util.List;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.TransientDataAccessException;
import com.marcos.cards_batch.batch.flatten.CardFaceFlatten;
import com.marcos.cards_batch.batch.listener.RetryBatchListener;
import com.marcos.cards_batch.batch.listener.SkipCardFaceListener;
import com.marcos.cards_batch.batch.listener.StepSummaryListener;
import com.marcos.cards_batch.batch.processor.CardFaceProcessor;
import com.marcos.cards_batch.batch.reader.ScryfallStreamReader;
import com.marcos.cards_batch.domain.entity.CardFaceJdbc;
import com.marcos.cards_batch.dto.ScryfallCardDto;

@Configuration
public class CardFaceStepConfig {
    private final ScryfallStreamReader scryfallStreamReader;
    private final CardFaceProcessor cardFaceProcessor;
    private final CardFaceFlatten cardFaceFlatten;
    private final StepSummaryListener stepSummaryListener;
    private final SkipCardFaceListener skipCardFaceListener;
    private final RetryBatchListener retryBatchListener;

    public CardFaceStepConfig(ScryfallStreamReader scryfallStreamReader, CardFaceProcessor cardFaceProcessor,
            CardFaceFlatten cardFaceFlatten, StepSummaryListener stepSummaryListener, 
            SkipCardFaceListener skipCardFaceListener, RetryBatchListener retryBatchListener) {
        this.scryfallStreamReader = scryfallStreamReader;
        this.cardFaceProcessor = cardFaceProcessor;
        this.cardFaceFlatten = cardFaceFlatten;
        this.stepSummaryListener = stepSummaryListener;
        this.skipCardFaceListener = skipCardFaceListener;
        this.retryBatchListener = retryBatchListener;
    }

    @Bean
    public Step cardFaceStep(JobRepository jobRepository) {
        return new StepBuilder("cardFaceStep", jobRepository)
            .<ScryfallCardDto, List<CardFaceJdbc>>chunk(500)
            .reader(scryfallStreamReader)
            .processor(cardFaceProcessor)
            .writer(cardFaceFlatten)
            .faultTolerant()
            .retry(CannotAcquireLockException.class)
            .retry(TransientDataAccessException.class)
            .retry(SQLException.class)
            .retryLimit(3)
            .skip(DataIntegrityViolationException.class)
            .skip(DuplicateKeyException.class)
            .skipLimit(100)
            .listener(stepSummaryListener)
            .listener(skipCardFaceListener)
            .listener(retryBatchListener)
            .build();
    }
}
