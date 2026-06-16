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
import com.marcos.cards_batch.repository.CardFaceRepository;
import com.marcos.cards_batch.repository.CardRepository;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CardFaceProcessor implements ItemProcessor<ScryfallCardDto, List<CardFace>> {
    private final CardFacesMapper cardFacesMapper;
    private final CardRepository cardRepository;
    private final CardFaceRepository cardFaceRepository;

    public CardFaceProcessor(CardFacesMapper cardFacesMapper, CardRepository cardRepository, CardFaceRepository cardFaceRepository) {
        this.cardFacesMapper = cardFacesMapper;
        this.cardRepository = cardRepository;
        this.cardFaceRepository = cardFaceRepository;
    }

    @Override
    public @Nullable List<CardFace> process(ScryfallCardDto item) throws Exception {
        List<CardFace> cardFaces = new ArrayList<>();
        short faceIndex = 0;
        CardFace cardFace = null;

        if (item.cardFaces() == null) {
            log.debug("No faces found");
            faceIndex = 0;
            return null;
        }

        log.debug("Faces found");
        Card card = cardRepository.getReferenceById(item.id());
        for (CardFacesDto face : item.cardFaces()) {
            log.debug("Processing face {}", face.name());
            boolean exists = cardFaceRepository.existsByCardIdAndFaceIndex(item.id(), faceIndex);

            if (!exists) {
                cardFace = cardFacesMapper.toEntity(face);
                cardFace.setCard(card);
                cardFace.setFaceIndex(faceIndex);
                cardFaces.add(cardFace);
            }
            
            faceIndex++;
        }
        
        return cardFaces;
    }
    
}
