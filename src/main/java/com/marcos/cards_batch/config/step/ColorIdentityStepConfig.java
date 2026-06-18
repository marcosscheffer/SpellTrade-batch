package com.marcos.cards_batch.config.step;

import java.util.List;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.marcos.cards_batch.batch.flatten.ColorIdentityFlatten;
import com.marcos.cards_batch.batch.processor.ColorIdentityProcessor;
import com.marcos.cards_batch.batch.reader.ScryfallStreamReader;
import com.marcos.cards_batch.domain.entity.ColorIdentityJdbc;
import com.marcos.cards_batch.dto.ScryfallCardDto;

@Configuration
public class ColorIdentityStepConfig {
    private final ScryfallStreamReader scryfallStreamReader;
    private final ColorIdentityProcessor colorIdentityProcessor;
    private final ColorIdentityFlatten colorIdentityFlatten;
    
    public ColorIdentityStepConfig(ScryfallStreamReader scryfallStreamReader,
            ColorIdentityProcessor colorIdentityProcessor,
            ColorIdentityFlatten colorIdentityFlatten) {
        this.scryfallStreamReader = scryfallStreamReader;
        this.colorIdentityProcessor = colorIdentityProcessor;
        this.colorIdentityFlatten = colorIdentityFlatten;
    }

    @Bean
    public Step colorIdentityStep(JobRepository jobRepository) {
        return new StepBuilder("colorIdentityStep", jobRepository)
            .<ScryfallCardDto, List<ColorIdentityJdbc>>chunk(500)
            .reader(scryfallStreamReader)
            .processor(colorIdentityProcessor)
            .writer(colorIdentityFlatten)
            .build();
    }
}
