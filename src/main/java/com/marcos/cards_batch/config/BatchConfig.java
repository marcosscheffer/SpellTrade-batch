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
import com.marcos.cards_batch.batch.processor.ColorIdentityProcessor;
import com.marcos.cards_batch.batch.processor.ImageProcessor;
import com.marcos.cards_batch.batch.processor.LegalitiesProcessor;
import com.marcos.cards_batch.batch.processor.SetProcessor;
import com.marcos.cards_batch.batch.reader.ScryfallStreamReader;
import com.marcos.cards_batch.batch.tasklet.ScryfallDownloadTasklet;
import com.marcos.cards_batch.batch.writer.CardFaceWriter;
import com.marcos.cards_batch.batch.writer.CardWriter;
import com.marcos.cards_batch.batch.writer.ColorIdentityWriter;
import com.marcos.cards_batch.batch.writer.ImageWriter;
import com.marcos.cards_batch.batch.writer.LegalitiesWriter;
import com.marcos.cards_batch.batch.writer.SetWriter;
import com.marcos.cards_batch.domain.entity.Card;
import com.marcos.cards_batch.domain.entity.CardFace;
import com.marcos.cards_batch.domain.entity.CardLegality;
import com.marcos.cards_batch.domain.entity.CardSet;
import com.marcos.cards_batch.domain.entity.ColorIdentity;
import com.marcos.cards_batch.domain.entity.Image;
import com.marcos.cards_batch.dto.ScryfallCardDto;

@Configuration
public class BatchConfig {
    private final JobRepository jobRepository;

    private final ScryfallDownloadTasklet scryfallDownloadTasklet;
    private final ScryfallStreamReader scryfallStreamReader;

    private final CardProcessor cardProcessor;
    private final SetProcessor setProcessor;
    private final CardFaceProcessor cardFaceProcessor;
    private final ImageProcessor imageProcessor;
    private final LegalitiesProcessor legalitiesProcessor;
    private final ColorIdentityProcessor colorIdentityProcessor;

    private final CardWriter cardWriter;
    private final SetWriter setWriter;
    private final CardFaceWriter cardFaceWriter;
    private final ImageWriter imageWriter;
    private final LegalitiesWriter legalitiesWriter;
    private final ColorIdentityWriter colorIdentityWriter;
    
    public BatchConfig(JobRepository jobRepository, ScryfallDownloadTasklet scryfallDownloadTasklet,
            ScryfallStreamReader scryfallStreamReader, CardProcessor cardProcessor,
            SetProcessor setProcessor, CardFaceProcessor cardFaceProcessor,
            ImageProcessor imageProcessor, LegalitiesProcessor legalitiesProcessor,
            com.marcos.cards_batch.batch.processor.ColorIdentityProcessor colorIdentityProcessor,
            CardWriter cardWriter, SetWriter setWriter, CardFaceWriter cardFaceWriter,
            ImageWriter imageWriter, LegalitiesWriter legalitiesWriter,
            com.marcos.cards_batch.batch.writer.ColorIdentityWriter colorIdentityWriter) {
        this.jobRepository = jobRepository;
        this.scryfallDownloadTasklet = scryfallDownloadTasklet;
        this.scryfallStreamReader = scryfallStreamReader;
        this.cardProcessor = cardProcessor;
        this.setProcessor = setProcessor;
        this.cardFaceProcessor = cardFaceProcessor;
        this.imageProcessor = imageProcessor;
        this.legalitiesProcessor = legalitiesProcessor;
        this.colorIdentityProcessor = colorIdentityProcessor;
        this.cardWriter = cardWriter;
        this.setWriter = setWriter;
        this.cardFaceWriter = cardFaceWriter;
        this.imageWriter = imageWriter;
        this.legalitiesWriter = legalitiesWriter;
        this.colorIdentityWriter = colorIdentityWriter;
    }


    @Bean
    public Job importScryfallCardsJob() {
        return new JobBuilder("importScryfallCardsJob", jobRepository)
            .start(downloadCardsStep())
            .next(setsStep())
            .next(cardsStep())
            .next(colorIdentityStep())
            .next(cardFacesStep())
            .next(imagesStep())
            .next(legalitiesStep())
            .build();
    }
    
    @Bean
    public Step colorIdentityStep() {
        return new StepBuilder("colorIdentityStep", jobRepository)
            .<ScryfallCardDto, List<ColorIdentity>>chunk(500)
            .reader(scryfallStreamReader)
            .processor(colorIdentityProcessor)
            .writer(colorIdentityWriter)
            .build();
    }

    @Bean
    public Step cardsStep() {
        return new StepBuilder("cardsStep", jobRepository)
            .<ScryfallCardDto, Card>chunk(500)
            .reader(scryfallStreamReader)
            .processor(cardProcessor)
            .writer(cardWriter)
            .build();
    }

    @Bean Step setsStep() {
        return new StepBuilder("setsStep", jobRepository)
            .<ScryfallCardDto, CardSet>chunk(500)
            .reader(scryfallStreamReader)
            .processor(setProcessor)
            .writer(setWriter)
            .build();
    }

    @Bean
    public Step cardFacesStep() {
        return new StepBuilder("cardFacesStep", jobRepository)
            .<ScryfallCardDto, List<CardFace>>chunk(500)
            .reader(scryfallStreamReader)
            .processor(cardFaceProcessor)
            .writer(cardFaceWriter)
            .build();
    }

    @Bean
    public Step imagesStep() {
        return new StepBuilder("imagesStep", jobRepository)
            .<ScryfallCardDto, List<Image>>chunk(500)
            .reader(scryfallStreamReader)
            .processor(imageProcessor)
            .writer(imageWriter)
            .build();
    }

    @Bean
    public Step legalitiesStep() {
        return new StepBuilder("legalitiesStep", jobRepository)
            .<ScryfallCardDto, List<CardLegality>>chunk(500)
            .reader(scryfallStreamReader)
            .processor(legalitiesProcessor)
            .writer(legalitiesWriter)
            .build();
    }

    @Bean
    public Step downloadCardsStep() {
        return new StepBuilder("downloadCardsStep", jobRepository)
            .tasklet(scryfallDownloadTasklet)
            .build();
    }
}
