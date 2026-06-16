package com.marcos.cards_batch.batch.writer;

import org.springframework.batch.infrastructure.item.Chunk;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.stereotype.Component;
import com.marcos.cards_batch.domain.entity.CardFace;
import com.marcos.cards_batch.repository.CardFaceRepository;
import lombok.extern.slf4j.Slf4j;
import java.util.List;

@Component
@Slf4j
public class CardFaceWriter implements ItemWriter<List<CardFace>> {
    private CardFaceRepository cardFaceRepository;

    public CardFaceWriter(CardFaceRepository cardFaceRepository) {
        this.cardFaceRepository = cardFaceRepository;
    }

    @Override
    public void write(Chunk<? extends List<CardFace>> chunk) throws Exception {
        List<? extends List<CardFace>> items = chunk.getItems();
        List<CardFace> cardFaces = items.stream()
            .flatMap(list -> list.stream())
            .toList();
        log.info("Saving cards faces - {} B", cardFaces.size());
        cardFaceRepository.saveAll(cardFaces);
    }
}
