package com.marcos.cards_batch.batch.processor;

import org.jspecify.annotations.Nullable;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.stereotype.Component;
import com.marcos.cards_batch.domain.entity.CardSetJdbc;
import com.marcos.cards_batch.dto.ScryfallCardDto;
import com.marcos.cards_batch.mapper.CardSetMapper;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SetProcessor implements ItemProcessor<ScryfallCardDto, CardSetJdbc>{

    private final CardSetMapper cardSetMapper;

    public SetProcessor(CardSetMapper cardSetMapper) {
        this.cardSetMapper = cardSetMapper;
    }

    @Override
    public @Nullable CardSetJdbc process(ScryfallCardDto item) throws Exception {
        if (item == null) {
            log.info("Sets importing completed");
            return null;
        }

        log.info("Processing set {}", item.setName());
        return cardSetMapper.toEntity(item);
    }
}
