package com.marcos.cards_batch.config.step;

import java.util.List;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.marcos.cards_batch.batch.flatten.ImageFlatten;
import com.marcos.cards_batch.batch.processor.ImageProcessor;
import com.marcos.cards_batch.batch.reader.ScryfallStreamReader;
import com.marcos.cards_batch.domain.entity.ImageJdbc;
import com.marcos.cards_batch.dto.ScryfallCardDto;

@Configuration
public class ImageStepConfig {
    private final ScryfallStreamReader scryfallStreamReader;
    private final ImageProcessor imageProcessor;
    private final ImageFlatten imageFlatten;

    public ImageStepConfig(ScryfallStreamReader scryfallStreamReader, ImageProcessor imageProcessor,
            ImageFlatten imageFlatten) {
        this.scryfallStreamReader = scryfallStreamReader;
        this.imageProcessor = imageProcessor;
        this.imageFlatten = imageFlatten;
    }

    @Bean
    public Step imageStep(JobRepository jobRepository) {
        return new StepBuilder("imageStep", jobRepository)
            .<ScryfallCardDto, List<ImageJdbc>>chunk(500)
            .reader(scryfallStreamReader)
            .processor(imageProcessor)
            .writer(imageFlatten)
            .build();
    }
}
