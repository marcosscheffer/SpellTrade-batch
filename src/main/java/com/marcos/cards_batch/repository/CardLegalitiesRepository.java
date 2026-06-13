package com.marcos.cards_batch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.marcos.cards_batch.domain.entity.CardLegality;
import com.marcos.cards_batch.domain.entity.CardLegalityId;

public interface CardLegalitiesRepository extends JpaRepository<CardLegality, CardLegalityId> {
    
}
