package com.marcos.cards_batch.mapper;

import org.mapstruct.Mapper;
import com.marcos.cards_batch.domain.entity.Card;
import com.marcos.cards_batch.domain.enums.Color;
import com.marcos.cards_batch.domain.enums.RarityType;
import com.marcos.cards_batch.dto.ScryfallCardDto;

@Mapper(componentModel = "spring")
public interface ScryfallCardMapper {
    Card toEntity(ScryfallCardDto dto);

    default Color mapColor(String color) {
        try {
            return Color.valueOf(color.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    default RarityType mapRarity(String rarity) {
        try {
            return RarityType.valueOf(rarity.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
