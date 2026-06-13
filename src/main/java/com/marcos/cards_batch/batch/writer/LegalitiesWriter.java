package com.marcos.cards_batch.batch.writer;

import java.util.List;
import org.springframework.batch.infrastructure.item.Chunk;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.stereotype.Component;
import com.marcos.cards_batch.domain.entity.CardLegality;
import com.marcos.cards_batch.repository.CardLegalitiesRepository;
import lombok.extern.slf4j.Slf4j;


@Component
@Slf4j
public class LegalitiesWriter implements ItemWriter<List<CardLegality>> {
    private final CardLegalitiesRepository cardLegalitiesRepository;
    
    public LegalitiesWriter(CardLegalitiesRepository cardLegalitiesRepository) {
        this.cardLegalitiesRepository = cardLegalitiesRepository;
    }

    @Override
    public void write(Chunk<? extends List<CardLegality>> chunk) throws Exception {
        List<? extends List<CardLegality>> items = chunk.getItems();
        List<CardLegality> cardLegalities = items.stream()
            .flatMap(list -> list.stream())
            .toList();
        log.info("Saving legalities - {} B", cardLegalities.size());
        cardLegalitiesRepository.saveAll(cardLegalities);
    } 
}
