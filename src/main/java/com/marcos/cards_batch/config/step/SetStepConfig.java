package com.marcos.cards_batch.config.step;

import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
    
    public SetStepConfig(ScryfallStreamReader scryfallStreamReader, SetProcessor setProcessor,
            SetWriter setWriter) {
        this.scryfallStreamReader = scryfallStreamReader;
        this.setProcessor = setProcessor;
        this.setWriter = setWriter;
    }

    @Bean 
    public Step setStep(JobRepository jobRepository) {
        return new StepBuilder("setStep", jobRepository)
            .<ScryfallCardDto, CardSetJdbc>chunk(500)
            .reader(scryfallStreamReader)
            .processor(setProcessor)
            .writer(setWriter)
            .build();
    }
}
