package com.marcos.cards_batch.config;

import java.util.List;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.marcos.cards_batch.batch.processor.CardFaceProcessor;
import com.marcos.cards_batch.batch.processor.CardProcessor;
import com.marcos.cards_batch.batch.processor.SetProcessor;
import com.marcos.cards_batch.batch.reader.ScryfallStreamReader;
import com.marcos.cards_batch.batch.tasklet.ScryfallDownloadTasklet;
import com.marcos.cards_batch.batch.writer.CardFaceWriter;
import com.marcos.cards_batch.batch.writer.CardWriter;
import com.marcos.cards_batch.batch.writer.SetWriter;
import com.marcos.cards_batch.domain.entity.Card;
import com.marcos.cards_batch.domain.entity.CardFace;
import com.marcos.cards_batch.domain.entity.CardSet;
import com.marcos.cards_batch.dto.ScryfallCardDto;

@Configuration
public class BatchConfig {
    private final JobRepository jobRepository;

    private final ScryfallDownloadTasklet scryfallDownloadTasklet;
    private final ScryfallStreamReader scryfallStreamReader;

    private final CardProcessor cardProcessor;
    private final SetProcessor setProcessor;
    private final CardFaceProcessor cardFaceProcessor;

    private final CardWriter cardWriter;
    private final SetWriter setWriter;
    private final CardFaceWriter cardFaceWriter;


    

    public BatchConfig(JobRepository jobRepository, ScryfallDownloadTasklet scryfallDownloadTasklet,
            ScryfallStreamReader scryfallStreamReader, CardProcessor cardProcessor,
            SetProcessor setProcessor, CardFaceProcessor cardFaceProcessor, CardWriter cardWriter,
            SetWriter setWriter, CardFaceWriter cardFaceWriter) {
        this.jobRepository = jobRepository;
        this.scryfallDownloadTasklet = scryfallDownloadTasklet;
        this.scryfallStreamReader = scryfallStreamReader;
        this.cardProcessor = cardProcessor;
        this.setProcessor = setProcessor;
        this.cardFaceProcessor = cardFaceProcessor;
        this.cardWriter = cardWriter;
        this.setWriter = setWriter;
        this.cardFaceWriter = cardFaceWriter;
    }

    @Bean
    public Job importScryfallCardsJob() {
        return new JobBuilder("importScryfallCardsJob", jobRepository)
            .start(downloadCardsStep())
            //.next(setsStep())
            //.next(cardsStep())
            .next(cardFacesStep())
            .build();
    }    

    @Bean
    public Step cardsStep() {
        return new StepBuilder("cardsStep", jobRepository)
            .<ScryfallCardDto, Card>chunk(100)
            .reader(scryfallStreamReader)
            .processor(cardProcessor)
            .writer(cardWriter)
            .build();
    }

    @Bean Step setsStep() {
        return new StepBuilder("setsStep", jobRepository)
            .<ScryfallCardDto, CardSet>chunk(100)
            .reader(scryfallStreamReader)
            .processor(setProcessor)
            .writer(setWriter)
            .build();
    }

    @Bean
    public Step cardFacesStep() {
        return new StepBuilder("cardFacesStep", jobRepository)
            .<ScryfallCardDto, List<CardFace>>chunk(100)
            .reader(scryfallStreamReader)
            .processor(cardFaceProcessor)
            .writer(cardFaceWriter)
            .build();
    }

    @Bean
    public Step downloadCardsStep() {
        return new StepBuilder("downloadCardsStep", jobRepository)
            .tasklet(scryfallDownloadTasklet)
            .build();
    }
}
