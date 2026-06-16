package com.marcos.cards_batch.batch.processor;

import java.util.ArrayList;
import java.util.List;
import org.jspecify.annotations.Nullable;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.stereotype.Component;
import com.marcos.cards_batch.domain.entity.Card;
import com.marcos.cards_batch.domain.entity.CardFace;
import com.marcos.cards_batch.domain.entity.Image;
import com.marcos.cards_batch.dto.CardFacesDto;
import com.marcos.cards_batch.dto.ScryfallCardDto;
import com.marcos.cards_batch.mapper.ImageMapper;
import com.marcos.cards_batch.repository.CardFaceRepository;
import com.marcos.cards_batch.repository.CardRepository;
import com.marcos.cards_batch.repository.ImageRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ImageProcessor implements ItemProcessor<ScryfallCardDto, List<Image>>{
    private CardRepository cardRepository;
    private CardFaceRepository cardFaceRepository;
    private ImageRepository imageRepository;
    private ImageMapper imageMapper;

    public ImageProcessor(CardRepository cardRepository, CardFaceRepository cardFaceRepository,
            ImageMapper imageMapper, ImageRepository imageRepository) {
        this.cardRepository = cardRepository;
        this.cardFaceRepository = cardFaceRepository;
        this.imageMapper = imageMapper;
        this.imageRepository = imageRepository;
    }

    @Override
    public @Nullable List<Image> process(ScryfallCardDto item) throws Exception {
        if (item.cardFaces() == null && item.imageUris() == null) {
            log.debug("Image not found {}", item.name());
            return null;
        }
                
        List<Image> images = new ArrayList<>();

        if (item.cardFaces() != null) {
            short index = 0;
            for (CardFacesDto face : item.cardFaces()) {                
                if (face.imageUris() == null) {
                    log.warn("Skipping card without image {}", item.id());
                    index++;
                    continue;
                }
                
                CardFace cardFace = cardFaceRepository.findByCardIdAndFaceIndex(item.id(), index)
                    .orElseThrow(() -> new EntityNotFoundException("CardFace not found"));                    
                boolean exists = imageRepository.existsByCardFaceAndFaceIndex(cardFace, index);
                if (!exists){
                    Image image = imageMapper.toEntity(face.imageUris());
                
                    image.setCardFace(cardFace);
                    image.setFaceIndex(index);
                    images.add(image);
                } 
                index++;
            }
        } else {
            boolean exists = imageRepository.existsByCardId(item.id());
            
            if (!exists) {
                Card card = cardRepository.getReferenceById(item.id());
                Image image = imageMapper.toEntity(item.imageUris());
                image.setCard(card);
                images.add(image);
            }
        }

        log.debug("Processing image {}", item.name());
        
        return images;
    }
}
