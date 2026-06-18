package com.marcos.cards_batch.config.step;

import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
    
    public CardStepConfig(ScryfallStreamReader scryfallStreamReader, CardProcessor cardProcessor,
            CardWriter cardWriter) {
        this.scryfallStreamReader = scryfallStreamReader;
        this.cardProcessor = cardProcessor;
        this.cardWriter = cardWriter;
    }

    @Bean
    public Step cardStep(JobRepository jobRepository) {
        return new StepBuilder("cardStep", jobRepository)
            .<ScryfallCardDto, CardJdbc>chunk(500)
            .reader(scryfallStreamReader)
            .processor(cardProcessor)
            .writer(cardWriter)
            .build();
    }
}
