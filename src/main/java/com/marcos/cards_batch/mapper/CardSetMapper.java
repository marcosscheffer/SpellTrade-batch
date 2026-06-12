package com.marcos.cards_batch.mapper;

import com.marcos.cards_batch.domain.entity.CardSet;
import com.marcos.cards_batch.domain.enums.SetType;
import com.marcos.cards_batch.dto.ScryfallCardDto;

public interface CardSetMapper {
    CardSet toEntity(ScryfallCardDto dto);

    default SetType mapType(String type) {
        try {
            return SetType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
