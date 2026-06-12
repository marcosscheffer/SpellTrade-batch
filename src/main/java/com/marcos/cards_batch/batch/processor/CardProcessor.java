package com.marcos.cards_batch.batch.processor;

import org.jspecify.annotations.Nullable;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.stereotype.Component;
import com.marcos.cards_batch.domain.entity.Card;
import com.marcos.cards_batch.dto.ScryfallCardDto;

import com.marcos.cards_batch.mapper.ScryfallCardMapper;

@Component
public class CardProcessor implements ItemProcessor<ScryfallCardDto, Card> {

    private final ScryfallCardMapper scryfallCardMapper;

    public CardProcessor(ScryfallCardMapper scryfallCardMapper) {
        this.scryfallCardMapper = scryfallCardMapper;
    }

    @Override
    public @Nullable Card process(ScryfallCardDto item) throws Exception {
        if (item == null) {
            return null;
        }

        return scryfallCardMapper.toEntity(item);
    } 
}
