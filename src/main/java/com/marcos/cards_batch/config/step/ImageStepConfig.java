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
import com.marcos.cards_batch.batch.flatten.ImageFlatten;
import com.marcos.cards_batch.batch.listener.RetryBatchListener;
import com.marcos.cards_batch.batch.listener.SkipImageListener;
import com.marcos.cards_batch.batch.listener.StepSummaryListener;
import com.marcos.cards_batch.batch.processor.ImageProcessor;
import com.marcos.cards_batch.batch.reader.ScryfallStreamReader;
import com.marcos.cards_batch.domain.entity.ImageJdbc;
import com.marcos.cards_batch.dto.ScryfallCardDto;

@Configuration
public class ImageStepConfig {
    private final ScryfallStreamReader scryfallStreamReader;
    private final ImageProcessor imageProcessor;
    private final ImageFlatten imageFlatten;
    private final StepSummaryListener stepSummaryListener;
    private final RetryBatchListener retryBatchListener;
    private final SkipImageListener skipImageListener;


    public ImageStepConfig(ScryfallStreamReader scryfallStreamReader, ImageProcessor imageProcessor,
            ImageFlatten imageFlatten, StepSummaryListener stepSummaryListener, 
            RetryBatchListener retryBatchListener, SkipImageListener skipImageListener) {
        this.scryfallStreamReader = scryfallStreamReader;
        this.imageProcessor = imageProcessor;
        this.imageFlatten = imageFlatten;
        this.stepSummaryListener = stepSummaryListener;
        this.retryBatchListener = retryBatchListener;
        this.skipImageListener = skipImageListener;
    }

    @Bean
    public Step imageStep(JobRepository jobRepository) {
        return new StepBuilder("imageStep", jobRepository)
            .<ScryfallCardDto, List<ImageJdbc>>chunk(500)
            .reader(scryfallStreamReader)
            .processor(imageProcessor)
            .writer(imageFlatten)
            .faultTolerant()
            .retry(CannotAcquireLockException.class)
            .retry(TransientDataAccessException.class)
            .retry(SQLException.class)
            .retryLimit(3)
            .skip(Exception.class)
            .skipLimit(100)
            .listener(stepSummaryListener)
            .listener(retryBatchListener)
            .listener(skipImageListener)
            .build();
    }
}
