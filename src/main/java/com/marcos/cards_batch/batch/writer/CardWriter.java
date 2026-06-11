package com.marcos.cards_batch.batch.writer;

import org.springframework.batch.infrastructure.item.Chunk;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.stereotype.Component;
import com.marcos.cards_batch.domain.entity.Card;


@Component
public class CardWriter implements ItemWriter<Card> {

    @Override
    public void write(Chunk<? extends Card> chunk) throws Exception {
        
    }
}
