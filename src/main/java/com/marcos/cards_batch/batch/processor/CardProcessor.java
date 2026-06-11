package com.marcos.cards_batch.batch.processor;

import org.jspecify.annotations.Nullable;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.stereotype.Component;
import com.marcos.cards_batch.domain.entity.Card;
import com.marcos.cards_batch.dto.ScryfallCardDto;

@Component
public class CardProcessor implements ItemProcessor<ScryfallCardDto, Card> {

    @Override
    public @Nullable Card process(ScryfallCardDto item) throws Exception {
        return null;
    } 
}
