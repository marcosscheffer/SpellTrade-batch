package com.marcos.cards_batch.batch.processor;

import com.marcos.cards_batch.dto.CardFacesDto;
import java.util.ArrayList;
import java.util.List;
import org.jspecify.annotations.Nullable;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.stereotype.Component;
import com.marcos.cards_batch.domain.entity.Card;
import com.marcos.cards_batch.domain.entity.CardFace;
import com.marcos.cards_batch.domain.entity.Image;
import com.marcos.cards_batch.dto.ScryfallCardDto;
import com.marcos.cards_batch.mapper.ImageMapper;
import com.marcos.cards_batch.repository.CardFaceRepository;
import com.marcos.cards_batch.repository.CardRepository;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ImageProcessor implements ItemProcessor<ScryfallCardDto, List<Image>>{
    private CardRepository cardRepository;
    private CardFaceRepository cardFaceRepository;
    private ImageMapper imageMapper;

    public ImageProcessor(CardRepository cardRepository, CardFaceRepository cardFaceRepository,
            ImageMapper imageMapper) {
        this.cardRepository = cardRepository;
        this.cardFaceRepository = cardFaceRepository;
        this.imageMapper = imageMapper;
    }

    @Override
    public @Nullable List<Image> process(ScryfallCardDto item) throws Exception {
        List<Image> images = new ArrayList<>();

        log.info("Processing image {}", item.name());
        
        if (item.imageUris() == null && item.cardFaces() == null) {
            log.info("No image for card {}", item.name());
            return null;
        } else if (item.cardFaces() == null) {
            Image image = imageMapper.toEntity(item.imageUris());
            Card card = cardRepository.findById(item.id()).orElseThrow(() -> new RuntimeException("Card Not Found"));
            image.setCard(card);
            images.add(image);
        } else {
            short faceIndex = 0;
            for (CardFacesDto face : item.cardFaces()) {
                if (face.imageUris() == null) {
                    log.info("No image for face {} of card {}", faceIndex, item.name());
                    return null;
                }
                Image image = imageMapper.toEntity(face.imageUris());
                CardFace cardFace = cardFaceRepository.findByCardIdAndFaceIndex(item.id(), faceIndex)
                    .orElseThrow(() -> new RuntimeException("Card face Not found"));
                image.setCardFace(cardFace);
                images.add(image);
                faceIndex++;
            }
        }
        return images;
    }
}
