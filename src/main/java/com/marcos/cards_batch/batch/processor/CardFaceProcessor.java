package com.marcos.cards_batch.batch.processor;

import java.util.ArrayList;
import java.util.List;
import org.jspecify.annotations.Nullable;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.stereotype.Component;
import com.marcos.cards_batch.domain.entity.CardFaceJdbc;
import com.marcos.cards_batch.dto.CardFacesDto;
import com.marcos.cards_batch.dto.ScryfallCardDto;
import com.marcos.cards_batch.mapper.CardFacesMapper;

@Component
public class CardFaceProcessor implements ItemProcessor<ScryfallCardDto, List<CardFaceJdbc>> {
    private final CardFacesMapper cardFacesMapper;

    public CardFaceProcessor(CardFacesMapper cardFacesMapper) {
        this.cardFacesMapper = cardFacesMapper;
    }

    @Override
    public @Nullable List<CardFaceJdbc> process(ScryfallCardDto item) throws Exception {
        List<CardFaceJdbc> cardFaces = new ArrayList<>();
        short faceIndex = 0;

        if (item.cardFaces() == null) {
            faceIndex = 0;
            return null;
        }

        for (CardFacesDto face : item.cardFaces()) {
            CardFaceJdbc cardFace = cardFacesMapper.toEntity(face);
            cardFace.setCardId(item.id());
            cardFace.setFaceIndex(faceIndex);
            cardFaces.add(cardFace);
            faceIndex++;
        }

        return cardFaces;
    }
    
}
