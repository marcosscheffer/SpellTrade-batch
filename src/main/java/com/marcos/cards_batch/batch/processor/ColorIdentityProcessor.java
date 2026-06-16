package com.marcos.cards_batch.batch.processor;

import java.util.ArrayList;
import java.util.List;
import org.jspecify.annotations.Nullable;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.stereotype.Component;
import com.marcos.cards_batch.domain.entity.Card;
import com.marcos.cards_batch.domain.entity.ColorIdentity;
import com.marcos.cards_batch.domain.entity.ColorIdentityId;
import com.marcos.cards_batch.domain.enums.Color;
import com.marcos.cards_batch.dto.ScryfallCardDto;
import com.marcos.cards_batch.repository.CardRepository;

@Component
public class ColorIdentityProcessor implements ItemProcessor<ScryfallCardDto, List<ColorIdentity>> {
    private final CardRepository cardRepository;

    public ColorIdentityProcessor(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    public @Nullable List<ColorIdentity> process(ScryfallCardDto item) throws Exception {
        List<ColorIdentity> colorIdentities = new ArrayList<>();

        Card card = cardRepository.getReferenceById(item.id());

        for (String color : item.colorIdentity()) {
            ColorIdentityId id = new ColorIdentityId();
            id.setCardId(item.id());
            id.setColor(Color.valueOf(color.toUpperCase()));

            ColorIdentity colorIdentity = new ColorIdentity();
            colorIdentity.setId(id);
            colorIdentity.setCard(card);

            colorIdentities.add(colorIdentity);
        }

        return colorIdentities;
    }
}
