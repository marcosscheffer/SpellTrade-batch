package com.marcos.cards_batch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.marcos.cards_batch.domain.entity.ColorIdentity;
import com.marcos.cards_batch.domain.entity.ColorIdentityId;

public interface ColorIdentityRepository extends JpaRepository<ColorIdentity, ColorIdentityId> {
    
}
