package com.marcos.cards_batch.batch.processor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jspecify.annotations.Nullable;
import org.springframework.batch.core.listener.StepExecutionListener;
import org.springframework.batch.core.step.StepExecution;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.stereotype.Component;
import com.marcos.cards_batch.domain.entity.CardFace;
import com.marcos.cards_batch.domain.entity.ImageJdbc;
import com.marcos.cards_batch.domain.key.CardFaceKey;
import com.marcos.cards_batch.dto.CardFacesDto;
import com.marcos.cards_batch.dto.ScryfallCardDto;
import com.marcos.cards_batch.mapper.ImageMapper;
import com.marcos.cards_batch.repository.CardFaceRepository;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ImageProcessor implements ItemProcessor<ScryfallCardDto, List<ImageJdbc>>, StepExecutionListener{
    private final CardFaceRepository cardFaceRepository;
    private final ImageMapper imageMapper;

    private Map<CardFaceKey, Long> cache = new HashMap<>();
    
    public ImageProcessor(CardFaceRepository cardFaceRepository, ImageMapper imageMapper) {
        this.cardFaceRepository = cardFaceRepository;
        this.imageMapper = imageMapper;
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        List<CardFace> cardFaces = cardFaceRepository.findAll();
        for(CardFace face : cardFaces) {
            CardFaceKey key = new CardFaceKey(face.getCard().getId(), face.getFaceIndex());
            cache.put(key, face.getId());
        }
    }

    @Override
    public @Nullable List<ImageJdbc> process(ScryfallCardDto item) throws Exception {
        if (item.cardFaces() == null && item.imageUris() == null) {
            log.debug("Image not found {}", item.name());
            return null;
        }
                
        List<ImageJdbc> images = new ArrayList<>();

        if (item.cardFaces() != null) {
            short index = 0;
            for (CardFacesDto face : item.cardFaces()) {
                if (face.imageUris() == null) {
                    log.debug("Images uris not found in face {}", face.name());
                    index++;
                    continue;
                }
                CardFaceKey key = new CardFaceKey(item.id(), index);
                Long faceId = cache.get(key);

                ImageJdbc image = imageMapper.toEntity(face.imageUris());
                image.setFaceIndex(index);
                image.setCardFace(faceId);
                image.setCardId(null);
                images.add(image);
                index++;
            }
        } else {
            ImageJdbc image = imageMapper.toEntity(item.imageUris());
            image.setFaceIndex((short) 0);
            image.setCardId(item.id());
            image.setCardFace(null);
            images.add(image);
        }

        log.info("Processing image {}", item.name());
        return images;
    }
}
