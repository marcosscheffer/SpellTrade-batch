package com.marcos.cards_batch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.marcos.cards_batch.domain.entity.Image;

public interface ImageRepository extends JpaRepository<Image, Long>{
    
}
