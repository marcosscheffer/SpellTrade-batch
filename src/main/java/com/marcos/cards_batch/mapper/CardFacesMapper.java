package com.marcos.cards_batch.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.marcos.cards_batch.domain.entity.CardFace;
import com.marcos.cards_batch.domain.enums.Color;
import com.marcos.cards_batch.dto.CardFacesDto;

@Mapper(componentModel = "spring")
public interface CardFacesMapper {
    @Mapping(target = "card", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "faceIndex", ignore = true)
    CardFace toEntity(CardFacesDto dto);

    default Color mapColor(String color) {
        try {
            return Color.valueOf(color.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
