package com.marcos.cards_batch.config;

import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.marcos.cards_batch.batch.processor.CardProcessor;
import com.marcos.cards_batch.batch.reader.ScryfallStreamReader;
import com.marcos.cards_batch.batch.tasklet.ScryfallDownloadTasklet;
import com.marcos.cards_batch.batch.writer.CardWriter;
import com.marcos.cards_batch.domain.entity.Card;
import com.marcos.cards_batch.dto.ScryfallCardDto;

@Configuration
public class BatchConfig {
    private final JobRepository jobRepository;
    private final ScryfallStreamReader scryfallStreamReader;
    private final CardProcessor cardProcessor;
    private final CardWriter cardWriter;
    private final ScryfallDownloadTasklet scryfallDownloadTasklet;

    public BatchConfig(JobRepository jobRepository, ScryfallStreamReader scryfallStreamReader, 
        CardProcessor cardProcessor, CardWriter cardWriter, ScryfallDownloadTasklet scryfallDownloadTasklet) {
        this.jobRepository = jobRepository;
        this.scryfallStreamReader = scryfallStreamReader;
        this.cardProcessor = cardProcessor;
        this.cardWriter = cardWriter;
        this.scryfallDownloadTasklet = scryfallDownloadTasklet;
    }

    @Bean
    public Job importScryfallCardsJob() {
        return new JobBuilder("importScryfallCardsJob", jobRepository)
            .start(downloadCardsStep())
            .next(cardsStep())
            .build();
    }    

    @Bean
    public Step cardsStep() {
        return new StepBuilder("processCardsStep", jobRepository)
            .<ScryfallCardDto, Card>chunk(100)
            .reader(scryfallStreamReader)
            .processor(cardProcessor)
            .writer(cardWriter)
            .build();
    }

    @Bean
    public Step downloadCardsStep() {
        return new StepBuilder("downloadCardsStep", jobRepository)
            .tasklet(scryfallDownloadTasklet)
            .build();
    }
}
