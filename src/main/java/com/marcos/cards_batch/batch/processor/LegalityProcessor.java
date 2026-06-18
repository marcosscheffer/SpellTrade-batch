package com.marcos.cards_batch.batch.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.jspecify.annotations.Nullable;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.stereotype.Component;
import com.marcos.cards_batch.domain.entity.CardLegalityJdbc;
import com.marcos.cards_batch.domain.enums.Format;
import com.marcos.cards_batch.domain.enums.LegalityStatus;
import com.marcos.cards_batch.dto.ScryfallCardDto;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LegalityProcessor implements ItemProcessor<ScryfallCardDto, List<CardLegalityJdbc>>{
    @Override
    public @Nullable List<CardLegalityJdbc> process(ScryfallCardDto item) throws Exception {
        List<CardLegalityJdbc> cardLegalities = new ArrayList<>();
        log.debug("Processing formats of card {}", item.name());

        for (Map.Entry<String, String> entry : item.legalities().entrySet()) {
            CardLegalityJdbc cardLegality = new CardLegalityJdbc();
            cardLegality.setCardId(item.id());
            cardLegality.setFormat(Format.valueOf(entry.getKey().toUpperCase()));
            cardLegality.setStatus(LegalityStatus.valueOf(entry.getValue().toUpperCase()));

            cardLegalities.add(cardLegality);
        }

        log.info("Processing formats of card - {}", item.id());
        return cardLegalities;
    }
    
}
