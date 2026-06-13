package com.marcos.cards_batch.batch.processor;

import java.util.ArrayList;
import java.util.List;
import org.jspecify.annotations.Nullable;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.stereotype.Component;
import com.marcos.cards_batch.domain.entity.Card;
import com.marcos.cards_batch.domain.entity.CardFace;
import com.marcos.cards_batch.dto.CardFacesDto;
import com.marcos.cards_batch.dto.ScryfallCardDto;
import com.marcos.cards_batch.mapper.CardFacesMapper;
import com.marcos.cards_batch.repository.CardRepository;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CardFaceProcessor implements ItemProcessor<ScryfallCardDto, List<CardFace>> {
    private final CardFacesMapper cardFacesMapper;
    private final CardRepository cardRepository;

    public CardFaceProcessor(CardFacesMapper cardFacesMapper, CardRepository cardRepository) {
        this.cardFacesMapper = cardFacesMapper;
        this.cardRepository = cardRepository;
    }

    @Override
    public @Nullable List<CardFace> process(ScryfallCardDto item) throws Exception {
        short faceIndex = 0;
        CardFace cardFace = null;
        List<CardFace> cardFaces = new ArrayList<>();

        if (item.cardFaces() == null) {
            log.info("No faces found");
            faceIndex = 0;
            return null;
        }

        log.info("Faces found");
        for (CardFacesDto face : item.cardFaces()) {
            log.info("Processing face {}", face.name());
            cardFace = cardFacesMapper.toEntity(face);
            Card card = cardRepository.findById(item.id()).orElseThrow(() -> new RuntimeException("Card Not Found"));
            cardFace.setCard(card);
            cardFace.setFaceIndex(faceIndex);
            cardFaces.add(cardFace);
            faceIndex++;
        }
        
        return cardFaces;
    }
    
}
