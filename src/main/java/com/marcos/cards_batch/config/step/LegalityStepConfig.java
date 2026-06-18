package com.marcos.cards_batch.config.step;

import java.util.List;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.marcos.cards_batch.batch.flatten.LegalityFlatten;
import com.marcos.cards_batch.batch.processor.LegalityProcessor;
import com.marcos.cards_batch.batch.reader.ScryfallStreamReader;
import com.marcos.cards_batch.domain.entity.CardLegalityJdbc;
import com.marcos.cards_batch.dto.ScryfallCardDto;

@Configuration
public class LegalityStepConfig {
    private final ScryfallStreamReader scryfallStreamReader;
    private final LegalityProcessor legalitiesProcessor;
    private final LegalityFlatten legalitiesFlatten;
    
    public LegalityStepConfig(ScryfallStreamReader scryfallStreamReader,
            LegalityProcessor legalitiesProcessor, LegalityFlatten legalitiesFlatten) {
        this.scryfallStreamReader = scryfallStreamReader;
        this.legalitiesProcessor = legalitiesProcessor;
        this.legalitiesFlatten = legalitiesFlatten;
    }

    @Bean
    public Step legalityStep(JobRepository jobRepository) {
        return new StepBuilder("legalityStep", jobRepository)
            .<ScryfallCardDto, List<CardLegalityJdbc>>chunk(500)
            .reader(scryfallStreamReader)
            .processor(legalitiesProcessor)
            .writer(legalitiesFlatten)
            .build();
    }
}
