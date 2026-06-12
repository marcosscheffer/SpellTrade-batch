package com.marcos.cards_batch.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.marcos.cards_batch.domain.entity.CardSet;

public interface CardSetRepository extends JpaRepository<CardSet, UUID>{
    
}
