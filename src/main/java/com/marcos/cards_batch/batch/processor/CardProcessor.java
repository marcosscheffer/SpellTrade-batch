package com.marcos.cards_batch.batch.processor;

import org.jspecify.annotations.Nullable;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.stereotype.Component;
import com.marcos.cards_batch.domain.entity.CardJdbc;
import com.marcos.cards_batch.dto.ScryfallCardDto;
import com.marcos.cards_batch.mapper.ScryfallCardMapper;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CardProcessor implements ItemProcessor<ScryfallCardDto, CardJdbc> {
    private final ScryfallCardMapper scryfallCardMapper;

    public CardProcessor(ScryfallCardMapper scryfallCardMapper) {
        this.scryfallCardMapper = scryfallCardMapper;
    }

    @Override
    public @Nullable CardJdbc process(ScryfallCardDto item) throws Exception {
        if (item == null) {
            return null;
        }
        CardJdbc card = scryfallCardMapper.toEntity(item);
        log.info("Processing card - {}", item.name());
        return card;
    } 
}
