package com.marcos.cards_batch.batch.processor;

import org.jspecify.annotations.Nullable;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.stereotype.Component;
import com.marcos.cards_batch.domain.entity.CardSet;
import com.marcos.cards_batch.dto.ScryfallCardDto;
import com.marcos.cards_batch.mapper.CardSetMapper;

@Component
public class SetProcessor implements ItemProcessor<ScryfallCardDto, CardSet>{

    private final CardSetMapper cardSetMapper;

    public SetProcessor(CardSetMapper cardSetMapper) {
        this.cardSetMapper = cardSetMapper;
    }

    @Override
    public @Nullable CardSet process(ScryfallCardDto item) throws Exception {
        if (item == null) {
            return null;
        }

        return cardSetMapper.toEntity(item);
    }
}
