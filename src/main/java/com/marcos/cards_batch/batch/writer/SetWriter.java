package com.marcos.cards_batch.batch.writer;

import java.util.List;
import org.springframework.batch.infrastructure.item.Chunk;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.stereotype.Component;
import com.marcos.cards_batch.domain.entity.CardSet;
import com.marcos.cards_batch.repository.CardSetRepository;

@Component
public class SetWriter implements ItemWriter<CardSet>{
    private final CardSetRepository cardSetRepository;


    public SetWriter(CardSetRepository cardSetRepository) {
        this.cardSetRepository = cardSetRepository;
    }

    @Override
    public void write(Chunk<? extends CardSet> chunk) throws Exception {
        List<? extends CardSet> items = chunk.getItems();
        cardSetRepository.saveAll(items);
    }
    
}
