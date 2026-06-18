package com.marcos.cards_batch.config.step;

import java.util.List;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.marcos.cards_batch.batch.flatten.CardFaceFlatten;
import com.marcos.cards_batch.batch.processor.CardFaceProcessor;
import com.marcos.cards_batch.batch.reader.ScryfallStreamReader;
import com.marcos.cards_batch.domain.entity.CardFaceJdbc;
import com.marcos.cards_batch.dto.ScryfallCardDto;

@Configuration
public class CardFaceStepConfig {
    private final ScryfallStreamReader scryfallStreamReader;
    private final CardFaceProcessor cardFaceProcessor;
    private final CardFaceFlatten cardFaceFlatten;

    public CardFaceStepConfig(ScryfallStreamReader scryfallStreamReader, CardFaceProcessor cardFaceProcessor,
            CardFaceFlatten cardFaceFlatten) {
        this.scryfallStreamReader = scryfallStreamReader;
        this.cardFaceProcessor = cardFaceProcessor;
        this.cardFaceFlatten = cardFaceFlatten;
    }

    @Bean
    public Step cardFaceStep(JobRepository jobRepository) {
        return new StepBuilder("cardFaceStep", jobRepository)
            .<ScryfallCardDto, List<CardFaceJdbc>>chunk(500)
            .reader(scryfallStreamReader)
            .processor(cardFaceProcessor)
            .writer(cardFaceFlatten)
            .build();
    }
}
