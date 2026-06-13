package com.marcos.cards_batch.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.marcos.cards_batch.domain.entity.CardFace;

public interface CardFaceRepository extends JpaRepository<CardFace, Long>{
    Optional<CardFace> findByCardIdAndFaceIndex(UUID cardId, Short faceIndex);
}
