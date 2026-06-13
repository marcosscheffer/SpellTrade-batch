package com.marcos.cards_batch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.marcos.cards_batch.domain.entity.CardFace;

public interface CardFaceRepository extends JpaRepository<CardFace, Long>{
    
}
