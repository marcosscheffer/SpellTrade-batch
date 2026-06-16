package com.marcos.cards_batch.batch.processor;

import org.jspecify.annotations.Nullable;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.stereotype.Component;
import com.marcos.cards_batch.domain.entity.Card;
import com.marcos.cards_batch.domain.entity.CardSet;
import com.marcos.cards_batch.dto.ScryfallCardDto;
import com.marcos.cards_batch.mapper.ScryfallCardMapper;
import com.marcos.cards_batch.repository.CardSetRepository;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CardProcessor implements ItemProcessor<ScryfallCardDto, Card> {

    private final ScryfallCardMapper scryfallCardMapper;
    private final CardSetRepository cardSetRepository;

    public CardProcessor(ScryfallCardMapper scryfallCardMapper, CardSetRepository cardSetRepository) {
        this.scryfallCardMapper = scryfallCardMapper;
        this.cardSetRepository = cardSetRepository;
    }

    @Override
    public @Nullable Card process(ScryfallCardDto item) throws Exception {
        if (item == null) {
            return null;
        }
        log.debug("Processing card {}", item.name());
        CardSet cardSet = cardSetRepository.getReferenceById(item.setId());
        Card card = scryfallCardMapper.toEntity(item);
        card.setSet(cardSet);
        return card;
    } 
}
