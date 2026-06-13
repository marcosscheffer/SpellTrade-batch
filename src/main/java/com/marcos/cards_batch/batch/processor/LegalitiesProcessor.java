package com.marcos.cards_batch.batch.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.jspecify.annotations.Nullable;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.stereotype.Component;
import com.marcos.cards_batch.domain.entity.Card;
import com.marcos.cards_batch.domain.entity.CardLegality;
import com.marcos.cards_batch.domain.entity.CardLegalityId;
import com.marcos.cards_batch.domain.enums.Format;
import com.marcos.cards_batch.domain.enums.LegalityStatus;
import com.marcos.cards_batch.dto.ScryfallCardDto;
import com.marcos.cards_batch.repository.CardRepository;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LegalitiesProcessor implements ItemProcessor<ScryfallCardDto, List<CardLegality>>{

    private final CardRepository cardRepository;

    public LegalitiesProcessor(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    public @Nullable List<CardLegality> process(ScryfallCardDto item) throws Exception {
        List<CardLegality> cardLegalities = new ArrayList<>();
        log.debug("Processing formats of card {}", item.name());

        for (Map.Entry<String, String> entry : item.legalities().entrySet()) {
            CardLegalityId cardLegalityId = new CardLegalityId();
            cardLegalityId.setCardId(item.id());
            cardLegalityId.setFormat(Format.valueOf(entry.getKey().toUpperCase()));
            
            CardLegality cardLegality = new CardLegality();
            cardLegality.setId(cardLegalityId);
            cardLegality.setStatus(LegalityStatus.valueOf(entry.getValue().toUpperCase()));
            
            Card card = cardRepository.findById(item.id()).orElseThrow(() -> new RuntimeException("Card not found"));
            cardLegality.setCard(card);

            cardLegalities.add(cardLegality);
        }

        return cardLegalities;
    }
    
}
