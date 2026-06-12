package com.marcos.cards_batch.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.marcos.cards_batch.domain.entity.CardSet;
import com.marcos.cards_batch.domain.enums.SetType;
import com.marcos.cards_batch.dto.ScryfallCardDto;

@Mapper(componentModel = "spring")
public interface CardSetMapper {
    @Mapping(source = "setId", target = "id")
    @Mapping(source = "setName", target = "name")
    @Mapping(source = "set", target = "code")
    @Mapping(source = "setType", target = "type")
    CardSet toEntity(ScryfallCardDto dto);

    default SetType mapType(String type) {
        try {
            return SetType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
