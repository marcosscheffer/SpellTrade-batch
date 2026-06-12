package com.marcos.cards_batch.batch.writer;

import java.util.List;
import org.springframework.batch.infrastructure.item.Chunk;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.stereotype.Component;
import com.marcos.cards_batch.domain.entity.Card;
import com.marcos.cards_batch.repository.CardRepository;


@Component
public class CardWriter implements ItemWriter<Card> {
    private final CardRepository cardRepository;

    public CardWriter(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    public void write(Chunk<? extends Card> chunk) throws Exception {
        List<? extends Card> items = chunk.getItems();
        cardRepository.saveAll(items);
    }
}
