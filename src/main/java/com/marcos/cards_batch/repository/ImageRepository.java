package com.marcos.cards_batch.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.marcos.cards_batch.domain.entity.CardFace;
import com.marcos.cards_batch.domain.entity.Image;

public interface ImageRepository extends JpaRepository<Image, Long>{
    boolean existsByCardFaceAndFaceIndex(CardFace cardFace, short faceIndex);
    boolean existsByCardId(UUID cardId);
    
}
